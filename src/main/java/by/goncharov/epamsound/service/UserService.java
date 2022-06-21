package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.UserDaoImpl;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.manager.Messenger;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 * @author Goncharov Daniil
 * @see User
 * @see UserDaoImpl
 */
@Service
public class UserService implements Messenger {
    private static final String SUCCESS = "Success";
    /**
     * The User dao.
     */
    @Autowired
    private UserDaoImpl userDao;

    /**
     * Find clients list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Transactional
    public List<User> findClients() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find suitable users list.
     *
     * @param str the str
     * @return the list
     * @throws ServiceException the service exception
     */
    @Transactional
    public List<User> findSuitableUsers(final String str)
            throws ServiceException {
        UserDaoImpl trackDAO = new UserDaoImpl();
        try {
            List<User> allClients = trackDAO.findAll();
            List<User> res = new ArrayList<>();
            for (User temp : allClients) {
                if (temp.getLogin().toLowerCase().contains(str.toLowerCase())) {
                    res.add(temp);
                }
            }
            return res;
        } catch (DaoException e) {
            throw new ServiceException("Exception during users search", e);
        }
    }

    /**
     * Find user user.
     *
     * @param login the login
     * @return the user
     * @throws ServiceException the service exception
     */
    @Transactional
    public User findUser(final String login) throws ServiceException {
        User user;
        try {
            user = userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("Error during user search", e);
        }
        return user;
    }

    /**
     * Sing up string.
     *
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public void singUp(User user)
                throws ServiceException {
        try {
            userDao.add(user);
        } catch (DaoException e) {
            throw new ServiceException("Error during signup", e);
        }
    }
    @Transactional
    public User findUserById(final int id) throws ServiceException {

        try {
            Optional<User> user = userDao.findById(id);
            if (user.isPresent()) {
                return user.get();
            }
            throw new ServiceException();
        } catch (DaoException e) {
            throw new ServiceException("Exception during user search", e);
        }
    }

    /**
     * Add comment string.
     *
     * @param user    the user
     * @param text    the text
     * @param track the track
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String addComment(final User user, final String text,
                             final Track track) throws ServiceException {
        Validator validator = new Validator();
        if (validator.isCommentValid(text)) {
            try {
                userDao.addComment(user.getId(), text, track.getId());
                return SUCCESS;
            } catch (DaoException e) {
                throw new ServiceException("Error during comment"
                        + " addition", e);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .ADD_COMMENT_ERROR);
        }
    }

    /**
     * Add cash string.
     *
     * @param user    the user
     * @param newCash the new cash
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String addCash(final User user, final String newCash)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isCashValid(newCash)) {
            try {
                Double cash = Double.valueOf(newCash);
                Double finalCash = user.getCash() + cash;
                userDao.changeCash(user.getId(), finalCash);
                double newUserCash = userDao.findCash(user.getId());
                if (newUserCash > 0) {
                    user.setCash(newUserCash);
                }
                return SUCCESS;
            } catch (DaoException e) {
                throw new ServiceException("Exception during"
                        + " money addition", e);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_CASH_ERROR);
        }
    }

    /**
     * Change email string.
     * @param newEmail the new email
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String changeEmail(final User user, final String newEmail)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isEmailValid(newEmail)) {
            if (validator.isEmailUnique(newEmail)) {
                try {
                    userDao.changeEmail(user.getId(), newEmail);
                    return SUCCESS;
                } catch (DaoException e) {
                    throw new ServiceException("Error during changing"
                            + " email", e);
                }
            } else {
                return messageManager.getProperty(MessageManager
                        .NOT_UNIQUE_EMAIL);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_EMAIL_ERROR);
        }
    }

    /**
     * Change login string.
     *
     * @param newLogin the new login
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String changeLogin(final User user, final String newLogin)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isLoginValid(newLogin)) {
            if (validator.isLoginUnique(newLogin)) {
                try {
                    userDao.changeLogin(user.getId(), newLogin);
                    return SUCCESS;
                } catch (DaoException e) {
                    throw new ServiceException("Error during changing"
                            + " login", e);
                }
            } else {
                return messageManager.getProperty(MessageManager
                        .NOT_UNIQUE_LOGIN);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_LOGIN_ERROR);
        }
    }

    /**
     * Change pass string.
     *
     * @param password     the password
     * @param newPassword  the new password
     * @param confPassword the conf password
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String changePass(final User user, final String password,
                             final String newPassword,
                             final String confPassword)
            throws ServiceException {
        Validator validator = new Validator();
        String md5Pass = DigestUtils.md5Hex(password);
        if (user.getPassword().equals(md5Pass)) {
            if (validator.isPasswordValid(newPassword)) {
                if (validator.validateConfirmPass(confPassword, newPassword)) {
                    String md5NewPass = DigestUtils.md5Hex(newPassword);
                    try {
                        userDao.changePassword(user.getId(), md5NewPass);
                        return SUCCESS;
                    } catch (DaoException e) {
                        throw new ServiceException("Error during changing"
                                + " password", e);
                    }
                } else {
                    return messageManager.getProperty(MessageManager
                            .CHANGE_PASS_EQUAL_NEW_ERROR);
                }
            } else {
                return messageManager.getProperty(MessageManager
                        .CHANGE_PASS_NEW_ERROR);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_PASS_EQUAL_ERROR);
        }
    }

    /**
     * Sets bonus.
     *
     * @param bonus  the bonus
     * @return the bonus
     * @throws ServiceException the service exception
     */
    @Transactional
    public String setBonus(final User user, final String bonus)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isBonusValid(bonus)) {
            int discount = Integer.valueOf(bonus);
            User client = findUserById(user.getId());
            if (discount != client.getDiscount()) {
                try {
                    userDao.setBonus(user.getId(), discount);
                    return SUCCESS;
                } catch (DaoException e) {
                    throw new ServiceException("Exception during bonus setting",
                            e);
                }
            } else {
                return SUCCESS;
            }
        } else {
            return messageManager.getProperty(MessageManager.SET_BONUS_ERROR);
        }
    }
    @Transactional
    public boolean checkLogin(final String login,
                              final String password)
            throws ServiceException {
        Validator validator = new Validator();
        if (!validator.isLoginValid(login)
                || !validator.isPasswordValid(password)) {
            return false;
        }
        String md5Pass = DigestUtils.md5Hex(password);
        try {
            String dbPass = userDao.findPassword(login);
            if (md5Pass.equals(dbPass)) {
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception during login", e);
        }
        return false;
    }
}
