package by.goncharov.epamsound.service;

import by.goncharov.epamsound.dao.DAOException;
import by.goncharov.epamsound.dao.UserDAO;
import by.goncharov.epamsound.manager.ConnectionPool;
import by.goncharov.epamsound.manager.ProxyConnection;
import org.apache.commons.codec.digest.DigestUtils;

public class LoginService {
    public boolean checkLogin(final String login,
                              final String password)
            throws ServiceException {
        Validator validator = new Validator();
        if (!validator.isLoginValid(login)
                || !validator.isPasswordValid(password)) {
            return false;
        }
        ProxyConnection connection = ConnectionPool.getInstance()
                .getConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Pass = DigestUtils.md5Hex(password);
        try {
            String dbPass = userDAO.findPassword(login);
            if (md5Pass.equals(dbPass)) {
                return true;
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception during login", e);
        } finally {
            userDAO.closeConnection(connection);
        }
        return false;
    }
}
