package ua.lviv.logos.dao;

import org.springframework.data.repository.CrudRepository;

import ua.lviv.logos.dto.User;

public interface UserDao extends CrudRepository<User, String> {
    
    public User findByName(String name);

    public User findByEmail(String email);

}
