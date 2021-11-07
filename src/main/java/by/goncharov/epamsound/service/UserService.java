package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.DAOException;
import by.goncharov.epamsound.dao.UserDAO;
import by.goncharov.epamsound.manager.ConnectionPool;
import by.goncharov.epamsound.manager.Messenger;
import by.goncharov.epamsound.manager.ProxyConnection;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Messenger {
    private final String SUCCESS = "Success";
    public List<User> findClients() throws ServiceException {
        ProxyConnection connection = ConnectionPool.getInstance()
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
        ProxyConnection connection = ConnectionPool.getInstance()
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
        ProxyConnection connection = ConnectionPool.getInstance().
                getConnection();
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
            ProxyConnection connection = ConnectionPool.getInstance().
                    getConnection();
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

        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
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
}
