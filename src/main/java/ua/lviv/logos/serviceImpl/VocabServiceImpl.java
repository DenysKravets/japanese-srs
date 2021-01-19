package ua.lviv.logos.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void update(Vocab vocab) {
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

    @Transactional
    public Stream<Vocab> findByUser(User user) {
        return vocabDao.findByUser(user);
    }

    public Vocab findById(String id) {
        return vocabDao.findById(id).get();
    }

    @Transactional
    public Stream<Vocab> findByNextDateLessThan(Timestamp timestamp) {
        return vocabDao.findByNextDateLessThanAndLearned(timestamp, true);
    }


}
