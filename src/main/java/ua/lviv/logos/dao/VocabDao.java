package ua.lviv.logos.dao;

import org.springframework.data.repository.CrudRepository;

import ua.lviv.logos.dto.User;
import ua.lviv.logos.dto.Vocab;

public interface VocabDao extends CrudRepository<Vocab, String> {
    public Vocab findByUserAndNumber(User user, Integer number);
}
