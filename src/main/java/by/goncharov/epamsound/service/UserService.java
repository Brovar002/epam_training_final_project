package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.DAOException;
import by.goncharov.epamsound.dao.UserDAO;
import by.goncharov.epamsound.manager.ConnectionPool;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.manager.Messenger;
import by.goncharov.epamsound.manager.Transaction;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Messenger {
    private final String SUCCESS = "Success";
    public List<User> findClients() throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return userDAO.findClients();
        } catch (DAOException e) {
            throw new ServiceException("Exception during clients search", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    public List<User> findSuitableUsers(final String str)
            throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        UserDAO trackDAO = new UserDAO(connection);
        try {
            List<User> allClients = trackDAO.findClients();
            List<User> res = new ArrayList<>();
            for (User temp : allClients) {
                if (temp.getLogin().toLowerCase().contains(str.toLowerCase())) {
                    res.add(temp);
                }
            }
            return res;
        } catch (DAOException e) {
            throw new ServiceException("Exception during users search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public User findUser(final String login) throws ServiceException {
        User user;
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            user = userDAO.findUser(login);
        } catch (DAOException e) {
            throw new ServiceException("Error during user search", e);
        } finally {
            userDAO.closeConnection(connection);
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
            Transaction connection = ConnectionPool.getInstance()
                    .getConnection();
            UserDAO userDAO = new UserDAO(connection);
            String md5Pass = DigestUtils.md5Hex(password);
            try {
                userDAO.addUser(login, md5Pass, email);
                return SUCCESS;
            } catch (DAOException e) {
                throw new ServiceException("Error during signup", e);
            } finally {
                userDAO.closeConnection(connection);
            }
        } else {
            return res;
        }
    }

    private User findUserById(final int id) throws ServiceException {
        User user;

        Transaction connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            user = userDAO.findUserById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during user search", e);
        } finally {
            userDAO.closeConnection(connection);
        }
        return user;
    }
    public String addComment(final User user, final String text,
                             final int trackId) throws ServiceException {
        Validator validator = new Validator();
        if (validator.isCommentValid(text)) {
            Transaction connection = ConnectionPool.getInstance()
                    .getConnection();
            UserDAO userDAO = new UserDAO(connection);
            try {
                userDAO.addComment(user.getId(), text, trackId);
                return SUCCESS;
            } catch (DAOException e) {
                throw new ServiceException("Error during comment"
                        + " addition", e);
            } finally {
                userDAO.closeConnection(connection);
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
            Transaction connection = ConnectionPool.getInstance()
                    .getConnection();
            UserDAO userDAO = new UserDAO(connection);
            try {
                Double cash = Double.valueOf(newCash);
                Double finalCash = user.getCash() + cash;
                userDAO.changeCash(user.getId(), finalCash);
                double newUserCash = userDAO.findCash(user.getId());
                if (newUserCash > 0) {
                    user.setCash(newUserCash);
                }
                return SUCCESS;
            } catch (DAOException e) {
                throw new ServiceException("Exception during"
                        + " money addition", e);
            } finally {
                userDAO.closeConnection(connection);
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
                Transaction connection = ConnectionPool.getInstance()
                        .getConnection();
                UserDAO userDAO = new UserDAO(connection);
                try {
                    userDAO.changeEmail(userId, newEmail);
                    return SUCCESS;
                } catch (DAOException e) {
                    throw new ServiceException("Error during changing"
                            + " email", e);
                } finally {
                    userDAO.closeConnection(connection);
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
                Transaction connection = ConnectionPool.getInstance()
                        .getConnection();
                UserDAO userDAO = new UserDAO(connection);
                try {
                    userDAO.changeLogin(userId, newLogin);
                    return SUCCESS;
                } catch (DAOException e) {
                    throw new ServiceException("Error during changing"
                            + " login", e);
                } finally {
                    userDAO.closeConnection(connection);
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
                    Transaction connection = ConnectionPool.getInstance()
                            .getConnection();
                    UserDAO userDAO = new UserDAO(connection);
                    String md5NewPass = DigestUtils.md5Hex(newPassword);
                    try {
                        userDAO.changePassword(userId, md5NewPass);
                        return SUCCESS;
                    } catch (DAOException e) {
                        throw new ServiceException("Error during changing"
                                + " password", e);
                    } finally {
                        userDAO.closeConnection(connection);
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
                Transaction connection = ConnectionPool.getInstance()
                        .getConnection();
                UserDAO userDAO = new UserDAO(connection);
                try {
                    userDAO.setBonus(userId, discount);
                    return SUCCESS;
                } catch (DAOException e) {
                    throw new ServiceException("Exception during bonus setting",
                            e);
                } finally {
                    userDAO.closeConnection(connection);
                }
            } else {
                return SUCCESS;
            }
        } else {
            return messageManager.getProperty(MessageManager.SET_BONUS_ERROR);
        }
    }
}
