package ua.lviv.logos.serviceImpl;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.lviv.logos.dao.VocabDao;
import ua.lviv.logos.dto.User;
import ua.lviv.logos.dto.Vocab;

@Service
public class VocabServiceImpl {

    @Autowired
    private VocabDao vocabDao;

    public void save(Vocab vocab) {
        vocab.setId(UUID.randomUUID().toString());
        vocabDao.save(vocab);
    }

    public Iterable<Vocab> findAll() {
        return vocabDao.findAll();
    }

    public Vocab findByUserAndNumber(User user, Integer number) {
        return vocabDao.findByUserAndNumber(user, number);
    }

    public void saveAll(ArrayList<Vocab> vocabs) {
        vocabs.stream().forEach(vocab -> vocab.setId(UUID.randomUUID().toString()));
        vocabDao.saveAll(vocabs);
    }

}
