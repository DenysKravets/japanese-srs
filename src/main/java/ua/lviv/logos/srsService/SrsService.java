package ua.lviv.logos.srsService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.logos.dto.Level;
import ua.lviv.logos.dto.User;
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

    public final int MAXSTREAK = 2; 
    public final int DIFTIME1 = 1;
    public final int DIFTIME2 = 2;
    public final int DIFTIME3 = 3; 

    public boolean vocabIsReady(Vocab vocab) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        Timestamp nextTime = vocab.getNextDate();
        if(currentTime.getTime() > nextTime.getTime()) {
            return true;
        }
        return false;
    }

    public void addTimeBasedOnLevel(Vocab vocab, Level level) {
        LocalDateTime currentTime = LocalDateTime.now();
        switch(level) {
            case NEWBIE:
                currentTime = currentTime.plusMinutes(DIFTIME1);
                break;
            case INTERMEDIATE:
                currentTime = currentTime.plusMinutes(DIFTIME2);
                break;
            case ADVANCED:
                currentTime = currentTime.plusMinutes(DIFTIME3);
                break;
        }
        vocab.setNextDate(Timestamp.valueOf(currentTime));
    }

    public boolean progressVocab(Vocab vocab, boolean progress) {
        if(!vocabIsReady(vocab)) {
            return false;
        }
        Level level = vocab.getLevel();
        int streak = vocab.getStreak();
        if(progress) {
            switch(level) {
                case NEWBIE: 
                    if(streak == MAXSTREAK) {
                        vocab.setLevel(Level.INTERMEDIATE);
                        vocab.setStreak(1);
                    } else {
                        vocab.setStreak(streak + 1);
                    }
                    break;

                case INTERMEDIATE:
                    if(streak == MAXSTREAK) {
                        vocab.setLevel(Level.ADVANCED);
                        vocab.setStreak(1);
                    } else {
                        vocab.setStreak(streak + 1);
                    }
                    break;

                case ADVANCED:
                    if(streak == MAXSTREAK) {
                        vocab.setStreak(10);
                    } else {
                        vocab.setStreak(streak + 1);
                    }
                    break;
            }
        } else {
            streak--;
            switch(level) {
                case NEWBIE: 
                    if(streak == 0) {
                        vocab.setStreak(1);
                    } else {
                        vocab.setStreak(streak);
                    }
                    break;

                case INTERMEDIATE:
                    if(streak == 0) {
                        vocab.setLevel(Level.NEWBIE);
                        vocab.setStreak(MAXSTREAK);
                    } else {
                        vocab.setStreak(streak);
                    }
                    break;

                case ADVANCED:
                    if(streak == 0) {
                        vocab.setLevel(Level.INTERMEDIATE);
                        vocab.setStreak(MAXSTREAK);
                    } else {
                        vocab.setStreak(streak);
                    }
                    break;
            }
        }
        addTimeBasedOnLevel(vocab, vocab.getLevel());
        vocabService.update(vocab);
        return true;
    }

    @Transactional
    public ArrayList<VocabDto> getReviews(User user) {
        
        ArrayList<VocabDto> vocabs = new ArrayList<>();

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        vocabService.findByNextDateLessThanAndUser(timestamp, user).forEach(vocab -> {
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
