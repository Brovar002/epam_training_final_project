package by.goncharov.epamsound.service.impl;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.UserRepository;
import by.goncharov.epamsound.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type User service.
 * @author Goncharov Daniil
 * @see User
 * @see UserRepository
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    private static final String SUCCESS = "Success";
    /**
     * The User dao.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Find clients list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */

    public List<User> findClients() throws ServiceException {
        return userRepository.findAll();
    }


    /**
     * Find user user.
     *
     * @param login the login
     * @return the user
     * @throws ServiceException the service exception
     */
    public List<User> findUserByLogin(final String login) throws ServiceException {
        return userRepository.findByLogin(login);
    }

    /**
     * Sing up string.
     *
     * @return the string
     * @throws ServiceException the service exception
     */
    public void save(User user)
                throws ServiceException {
        userRepository.save(user);
    }
    public User findUserById(final int id) throws ServiceException {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ServiceException("User not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = (User) findUserByLogin(username);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        if (user == null){
            throw new UsernameNotFoundException("Данный пользователь не зарегестрирован!");
        }

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
