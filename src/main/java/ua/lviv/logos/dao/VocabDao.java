package ua.lviv.logos.dao;

import java.sql.Timestamp;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.logos.dto.User;
import ua.lviv.logos.dto.Vocab;

public interface VocabDao extends CrudRepository<Vocab, String> {
    public Vocab findByUserAndNumber(User user, Integer number);
    @Transactional
    public Stream<Vocab> findByUser(User user);
    @Transactional
    public Stream<Vocab> findByNextDateLessThanAndLearnedAndUser(Timestamp timestamp, boolean learned, User user);
}
