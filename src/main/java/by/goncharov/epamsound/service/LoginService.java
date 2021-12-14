package by.goncharov.epamsound.service;

import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.UserDaoImpl;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * The type Login service.
 * @author Goncharov Daniil
 * @see UserDaoImpl
 */
public class LoginService {
    /**
     * Check login boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean checkLogin(final String login,
                              final String password)
            throws ServiceException {
        Validator validator = new Validator();
        if (!validator.isLoginValid(login)
                || !validator.isPasswordValid(password)) {
            return false;
        }
        UserDaoImpl userDao = new UserDaoImpl();
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
