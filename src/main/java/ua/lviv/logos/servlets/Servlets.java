package ua.lviv.logos.servlets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.qos.logback.core.net.SyslogOutputStream;
import ua.lviv.logos.AppSecurity.UserPrincipal;
import ua.lviv.logos.dao.UserDao;
import ua.lviv.logos.dto.Level;
import ua.lviv.logos.dto.User;
import ua.lviv.logos.dto.Vocab;
import ua.lviv.logos.dto.VocabDto;
import ua.lviv.logos.serviceImpl.UserServiceimpl;
import ua.lviv.logos.serviceImpl.VocabServiceImpl;

@SuppressWarnings("all")
@Controller
public class Servlets {

	@Autowired
	private UserServiceimpl userService;

	@Autowired
	private VocabServiceImpl vocabService;

	public String oneCharToHex(String arg) throws UnsupportedEncodingException {
		StringBuffer word = new StringBuffer(String.format("%040x", new BigInteger(1, arg.getBytes("UTF-8"))));

		word.delete(0, 40 - arg.length() * 6);
		int length = word.length();
		for (int i = 0; i < length / 2; i++) {
			word.insert(i * 3, "%");
		}

		return word.toString().toUpperCase();
	}

	public String toHex(String arg) throws UnsupportedEncodingException {

		int length = arg.length();
		StringBuffer chars = new StringBuffer("");
		for (int i = 0; i < length; i++) {
			chars.append(oneCharToHex(Character.toString(arg.charAt(i))));
		}

		return chars.toString();
	}

	@GetMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = userService.findByEmail(request.getUserPrincipal().getName());
		request.setAttribute("user", user);
		VocabDto vocab = new VocabDto(vocabService.findByUserAndNumber(user, 2));
		request.setAttribute("vocab", vocab);
		System.out.println(vocab);
		return "index";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}

	@GetMapping("/registration")
	public String registration(HttpServletRequest request, HttpServletResponse response) {
		return "registration";
	}

	@PostMapping("/registration")
	public String registrationPost(HttpServletRequest request, HttpServletResponse response)
		throws FileNotFoundException, IOException, CsvException {

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User(username, email, password);

		userService.save(user);

		Thread thread = new Thread() {
			public void run() {
				try {
					ArrayList<Vocab> vocabs = new ArrayList<>();
					User innerUser = userService.findByEmail(user.getEmail());
					AtomicInteger atomicInteger = new AtomicInteger();
					CSVReader reader = new CSVReader(new FileReader("src/main/vocab.csv"));
					List<String[]> r = reader.readAll();
					r.forEach(line -> {
						Vocab vocab = new Vocab(
							innerUser,
							atomicInteger.getAndAdd(1),
							line[7],
							Level.NEWBIE,
							false,
							null,
							0);
						vocabs.add(vocab);
					});
					vocabService.saveAll(vocabs);
					System.out.println("Done uploading vocab.");
				} catch (Exception e) {
					System.out.println("Oops!");
					System.out.println(e);
				}
				
			}
		  };
		
		thread.start();

		

		return "login";
	}

	//Testing
	@GetMapping("/")
	public String test(HttpServletRequest request, HttpServletResponse response)
			throws MalformedURLException, IOException, JsonProcessingException, JsonMappingException, CsvException {
		
		String search = request.getParameter("search");

		System.out.println(search.length());
		System.out.println(toHex(search));

		CSVReader reader = new CSVReader(new FileReader("src/main/vocab.csv"));
		List<String[]> r = reader.readAll();
		r.forEach(line -> System.out.println(line[7]));
		

		URL url = new URL("https://jisho.org/api/v1/search/words?keyword=" + toHex(search));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);

		int status = con.getResponseCode();
		System.out.println(status);

		BufferedReader in = new BufferedReader(
		new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}

		in.close();

		Map<String,Object> result = new ObjectMapper().readValue(content.toString(), HashMap.class);
		
		String word = (String) ((ArrayList<Map<String, ArrayList<Map<String, Object>>>>) result.get("data")).get(0).get("japanese").get(0).get("word");
		String reading = (String) ((ArrayList<Map<String, ArrayList<Map<String, Object>>>>) result.get("data")).get(0).get("japanese").get(0).get("reading");
		ArrayList<String> meanings = (ArrayList<String>) ((ArrayList<Map<String, ArrayList<Map<String, Object>>>>) result.get("data")).get(0).get("senses").get(0).get("english_definitions");

		System.out.println(result);
		System.out.println();
		System.out.println(word);
		System.out.println(reading);
		System.out.println(meanings);

		return "index";
	}

	@GetMapping("/saveUser")
	public String saveUser(HttpServletRequest request, HttpServletResponse response)
			throws CsvException {

		User user = new User("Denys", "denys@gmail.com", "123");
		userService.save(user);

		System.out.println(userService.findAll());

		return "index";
	}

}
