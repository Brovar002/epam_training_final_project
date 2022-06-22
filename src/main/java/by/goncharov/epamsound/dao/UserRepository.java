package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface User dao.
 * @author Goncharov Daniil
 * @see User
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     */
    List<User> findByEmail(String email) ;

    /**
     * Find by login user.
     *
     * @param login the login
     * @return the user
     */
    List<User> findByLogin(String login);


}
