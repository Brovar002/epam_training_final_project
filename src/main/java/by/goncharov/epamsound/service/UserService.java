package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.UserDaoImpl;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.manager.Messenger;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("Duplicates")
public class UserService implements Messenger {
    private final String SUCCESS = "Success";
    UserDaoImpl userDao = new UserDaoImpl();

    public List<User> findClients() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

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

    public User findUser(final String login) throws ServiceException {
        User user;
        try {
            user = userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("Error during user search", e);
        }
        return user;
    }
    public String singUp(final String login, final String password,
                             final String confirmPassword, final String email)
                throws ServiceException {
        Validator validator = new Validator();
        String res = validator.isDataValid(login, password,
                confirmPassword, email);
        if (SUCCESS.equals(res)) {
            String md5Pass = DigestUtils.md5Hex(password);
            try {
                User user = new User();
                user.setLogin(login);
                user.setPassword(md5Pass);
                user.setEmail(email);
                userDao.add(user);
                return SUCCESS;
            } catch (DaoException e) {
                throw new ServiceException("Error during signup", e);
            }
        } else {
            return res;
        }
    }

    private User findUserById(final int id) throws ServiceException {

        try {
            Optional<User> user = userDao.findById(Long.valueOf(id));
            if (user.isPresent()) {
                return user.get();
            }
            throw new ServiceException();
        } catch (DaoException e) {
            throw new ServiceException("Exception during user search", e);
        }
    }
    public String addComment(final User user, final String text,
                             final int trackId) throws ServiceException {
        Validator validator = new Validator();
        if (validator.isCommentValid(text)) {
            try {
                userDao.addComment(user.getId(), text, trackId);
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
    public String changeEmail(final int userId, final String newEmail)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isEmailValid(newEmail)) {
            if (validator.isEmailUnique(newEmail)) {
                try {
                    userDao.changeEmail(userId, newEmail);
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
    public String changeLogin(final int userId, final String newLogin)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isLoginValid(newLogin)) {
            if (validator.isLoginUnique(newLogin)) {
                try {
                    userDao.changeLogin(userId, newLogin);
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
    public String changePass(final int userId, final String userPass,
                             final String password, final String newPassword,
                             final String confPassword)
            throws ServiceException {
        Validator validator = new Validator();
        String md5Pass = DigestUtils.md5Hex(password);
        if (userPass.equals(md5Pass)) {
            if (validator.isPasswordValid(newPassword)) {
                if (validator.validateConfirmPass(confPassword, newPassword)) {
                    String md5NewPass = DigestUtils.md5Hex(newPassword);
                    try {
                        userDao.changePassword(userId, md5NewPass);
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
    public String setBonus(final int userId, final String bonus)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isBonusValid(bonus)) {
            int discount = Integer.valueOf(bonus);
            User client = findUserById(userId);
            if (discount != client.getDiscount()) {
                try {
                    userDao.setBonus(userId, discount);
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
}
