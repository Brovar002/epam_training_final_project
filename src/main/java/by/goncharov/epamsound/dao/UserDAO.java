package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.User;

/**
 * The interface User dao.
 * @author Goncharov Daniil
 * @see BaseDao
 * @see User
 */
public interface UserDao extends BaseDao<Long, User> {

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     * @throws DaoException the dao exception
     */
    User findByEmail(String email) throws DaoException;

    /**
     * Find by login user.
     *
     * @param login the login
     * @return the user
     * @throws DaoException the dao exception
     */
    User findByLogin(String login) throws DaoException;

    /**
     * Find password string.
     *
     * @param login the login
     * @return the string
     * @throws DaoException the dao exception
     */
    String findPassword(String login) throws DaoException;

    /**
     * Change cash.
     *
     * @param userId the user id
     * @param cash   the cash
     * @throws DaoException the dao exception
     */
    void changeCash(int userId, Double cash)
            throws DaoException;

    /**
     * Add comment.
     *
     * @param userId  the user id
     * @param text    the text
     * @param trackId the track id
     * @throws DaoException the dao exception
     */
    void addComment(int userId, String text,
                    int trackId) throws DaoException;

    /**
     * Change email.
     *
     * @param userId   the user id
     * @param newEmail the new email
     * @throws DaoException the dao exception
     */
    void changeEmail(int userId, String newEmail)
            throws DaoException;

    /**
     * Change login.
     *
     * @param userId   the user id
     * @param newLogin the new login
     * @throws DaoException the dao exception
     */
    void changeLogin(int userId, String newLogin)
            throws DaoException;

    /**
     * Change password.
     *
     * @param userId  the user id
     * @param newPass the new pass
     * @throws DaoException the dao exception
     */
    void changePassword(int userId, String newPass)
            throws DaoException;

    /**
     * Sets bonus.
     *
     * @param userId the user id
     * @param bonus  the bonus
     * @throws DaoException the dao exception
     */
    void setBonus(int userId, int bonus)
            throws DaoException;

}
