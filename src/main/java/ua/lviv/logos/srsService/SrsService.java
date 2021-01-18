package ua.lviv.logos.srsService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.lviv.logos.dto.Vocab;
import ua.lviv.logos.serviceImpl.UserServiceimpl;
import ua.lviv.logos.serviceImpl.VocabServiceImpl;

//@SuppressWarnings("all")
@Service
public class SrsService {

    @Autowired
    UserServiceimpl userService;

    @Autowired
    VocabServiceImpl vocabService;

    public void learnedVocabs(ArrayList<String> ids) {
        ArrayList<Vocab> vocabs = new ArrayList<>();
        ids.stream().forEach(id -> {
            vocabs.add(vocabService.findById(id));
        });
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.plusHours(1);
        Timestamp timestemp = Timestamp.valueOf(localDateTime);
        vocabs.stream().forEach(vocab -> {
            vocab.setLearned(true);
            vocab.setNextDate(timestemp);
            vocab.setStreak(1);
            vocabService.update(vocab);
        });

    }
}
