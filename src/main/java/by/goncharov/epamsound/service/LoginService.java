package by.goncharov.epamsound.service;

import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.UserDaoImpl;
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
