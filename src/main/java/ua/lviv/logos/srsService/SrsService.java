package ua.lviv.logos.srsService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.logos.dto.Vocab;
import ua.lviv.logos.dto.VocabDto;
import ua.lviv.logos.serviceImpl.UserServiceimpl;
import ua.lviv.logos.serviceImpl.VocabServiceImpl;

//@SuppressWarnings("all")
@Service
public class SrsService {

    @Autowired
    UserServiceimpl userService;

    @Autowired
    VocabServiceImpl vocabService;

    @Transactional
    public ArrayList<VocabDto> getReviews() {
        
        ArrayList<VocabDto> vocabs = new ArrayList<>();

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        vocabService.findByNextDateLessThan(timestamp).forEach(vocab -> {
            try {
                vocabs.add(new VocabDto(vocab));
            } catch (IOException e) {
                System.out.println(e);
            }
        });
        ;

        return vocabs;
    }

    public void learnedVocabs(ArrayList<String> ids) {
        ArrayList<Vocab> vocabs = new ArrayList<>();
        ids.stream().forEach(id -> {
            vocabs.add(vocabService.findById(id));
        });
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusSeconds(1);
        Timestamp timestemp = Timestamp.valueOf(localDateTime);
        vocabs.stream().forEach(vocab -> {
            vocab.setLearned(true);
            vocab.setNextDate(timestemp);
            vocab.setStreak(1);
            vocabService.update(vocab);
        });

    }
}
