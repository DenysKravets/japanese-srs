package ua.lviv.logos.dto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VocabDto {

    public String id;
    public User user;
    public Integer number;
    public String word;
    public Level level;
    public Boolean learned;
    public Timestamp nextDate;
    public Integer streak;

    public String reading;
    public String[] meanings;

    public VocabDto() {
    }

    @SuppressWarnings("all")
    public VocabDto(Vocab vocab) throws IOException {
        this.id = vocab.getId();
        this.user = vocab.getUser();
        this.number = vocab.getNumber();
        this.word = vocab.getWord();
        this.level = vocab.getLevel();
        this.learned = vocab.getLearned();
        this.nextDate = vocab.getNextDate();
        this.streak = vocab.getStreak();
        
        URL url = new URL("https://jisho.org/api/v1/search/words?keyword=" + toHex(vocab.getWord()));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);

		int status = con.getResponseCode();

		BufferedReader in = new BufferedReader(
		new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}

        in.close();

		Map<String,Object> result = new ObjectMapper().readValue(content.toString(), HashMap.class);
		
		String reading = (String) ((ArrayList<Map<String, ArrayList<Map<String, Object>>>>) result.get("data")).get(0).get("japanese").get(0).get("reading");
        ArrayList<String> meanings = (ArrayList<String>) ((ArrayList<Map<String, ArrayList<Map<String, Object>>>>) result.get("data")).get(0).get("senses").get(0).get("english_definitions");
        this.reading = reading;
        Iterator<String> iterator = meanings.stream().iterator();
        this.meanings = new String[meanings.size()];
        int i = 0;
        while(iterator.hasNext())
        {
            this.meanings[i] = iterator.next();
            i++;
        }
    }

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

    public VocabDto(String id, User user, Integer number, String word, Level level, Boolean learned, Timestamp nextDate, Integer streak, String reading, String[] meanings) {
        this.id = id;
        this.user = user;
        this.number = number;
        this.word = word;
        this.level = level;
        this.learned = learned;
        this.nextDate = nextDate;
        this.streak = streak;
        this.reading = reading;
        this.meanings = meanings;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Boolean isLearned() {
        return this.learned;
    }

    public Boolean getLearned() {
        return this.learned;
    }

    public void setLearned(Boolean learned) {
        this.learned = learned;
    }

    public Timestamp getNextDate() {
        return this.nextDate;
    }

    public void setNextDate(Timestamp nextDate) {
        this.nextDate = nextDate;
    }

    public Integer getStreak() {
        return this.streak;
    }

    public void setStreak(Integer streak) {
        this.streak = streak;
    }

    public String getReading() {
        return this.reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String[] getMeanings() {
        return this.meanings;
    }

    public void setMeanings(String[] meanings) {
        this.meanings = meanings;
    }

    public VocabDto id(String id) {
        setId(id);
        return this;
    }

    public VocabDto user(User user) {
        setUser(user);
        return this;
    }

    public VocabDto number(Integer number) {
        setNumber(number);
        return this;
    }

    public VocabDto word(String word) {
        setWord(word);
        return this;
    }

    public VocabDto level(Level level) {
        setLevel(level);
        return this;
    }

    public VocabDto learned(Boolean learned) {
        setLearned(learned);
        return this;
    }

    public VocabDto nextDate(Timestamp nextDate) {
        setNextDate(nextDate);
        return this;
    }

    public VocabDto streak(Integer streak) {
        setStreak(streak);
        return this;
    }

    public VocabDto reading(String reading) {
        setReading(reading);
        return this;
    }

    public VocabDto meanings(String[] meanings) {
        setMeanings(meanings);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof VocabDto)) {
            return false;
        }
        VocabDto vocabDto = (VocabDto) o;
        return Objects.equals(id, vocabDto.id) && Objects.equals(user, vocabDto.user) && Objects.equals(number, vocabDto.number) && Objects.equals(word, vocabDto.word) && Objects.equals(level, vocabDto.level) && Objects.equals(learned, vocabDto.learned) && Objects.equals(nextDate, vocabDto.nextDate) && Objects.equals(streak, vocabDto.streak) && Objects.equals(reading, vocabDto.reading) && Objects.equals(meanings, vocabDto.meanings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, number, word, level, learned, nextDate, streak, reading, meanings);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", user='" + getUser() + "'" +
            ", number='" + getNumber() + "'" +
            ", word='" + getWord() + "'" +
            ", level='" + getLevel() + "'" +
            ", learned='" + isLearned() + "'" +
            ", nextDate='" + getNextDate() + "'" +
            ", streak='" + getStreak() + "'" +
            ", reading='" + getReading() + "'" +
            ", meanings='" + getMeanings() + "'" +
            "}";
    }

}
