package ua.lviv.logos.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ua.lviv.logos.AppSecurity.UserPrincipal;
import ua.lviv.logos.dao.UserDao;
import ua.lviv.logos.dto.User;

@Service
public class UserServiceimpl implements UserDetailsService {
    
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByEmail(username);
        if(user==null) {
            throw new UsernameNotFoundException("User not found in database.");
        }

        return new UserPrincipal(user);
    }

    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        userDao.save(user);
    }

    public Iterable<User> findAll() {
        return userDao.findAll();
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

}
