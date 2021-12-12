package by.goncharov.epamsound.service;

import by.goncharov.epamsound.dao.DAOException;
import by.goncharov.epamsound.dao.UserDAO;
import by.goncharov.epamsound.dao.ConnectionPool;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.manager.Messenger;
import by.goncharov.epamsound.dao.Transaction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator implements Messenger {
    private final int MAX_ARTIST_LENGTH = 255;
    private final int MAX_GENRE_LENGTH = 45;
    private final int MAX_PRICE_LENGTH = 4;
    private final int MAX_TRACK_NAME_LENGTH = 100;
    private final int MAX_PASS_LENGTH = 10;
    private final int MIN_PASS_LENGTH = 6;
    private final String REGEX_LOGIN = "(\\w){6,10}";
    private final String REGEX_EMAIL = "(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})";
    private final String SIGNUP_SUCCESS = "Success";
    private final int ZERO = 0;
    private final int MAX_BONUS=100;
    private final int MAX_CASH_LENGTH = 5;
    private final int MAX_COMMENT_LENGTH = 65_535;
    public String isDataValid(final String login, final String password,
                              final String confirmPass, final String email)
            throws ServiceException {
        if (isLoginValid(login) && isPasswordValid(password)
                && isEmailValid(email)
                && validateConfirmPass(confirmPass, password)) {
            if (!isLoginUnique(login)) {
                return messageManager.getProperty(MessageManager.
                        NOT_UNIQUE_LOGIN);
            }
            if (!isEmailUnique(email)) {
                return messageManager.getProperty(MessageManager.
                        NOT_UNIQUE_EMAIL);
            }
            return SIGNUP_SUCCESS;
        } else {
            return messageManager.getProperty(MessageManager.
                    SIGNUP_ERROR);
        }
    }
    boolean isEmailUnique(final String email) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Transaction connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return (userDAO.findUserByEmail(email) == null);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }
    boolean isEmailValid(final String email) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    boolean isLoginUnique(final String login) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Transaction connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return (userDAO.findUser(login) == null);
        } catch (DAOException e) {
            throw new ServiceException("Error during checking login"
                    + " uniqueness", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }
    boolean isLoginValid(final String login) {
        Pattern pattern = Pattern.compile(REGEX_LOGIN);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }
    boolean isPasswordValid(final String password) {
        return (password.length() >= MIN_PASS_LENGTH
                && password.length() <= MAX_PASS_LENGTH);
    }
    public boolean isTrackValid(final String name, final String artist,
                                final String price, final String genre)
            throws ServiceException {
        return  (isTrackNameValid(name) && isTrackArtistValid(artist)
                && isGenreValid(genre) && isPriceValid(price));
    }
    boolean isTrackNameValid(final String name) {
        return name.length() > ZERO && name.length() < MAX_TRACK_NAME_LENGTH;
    }
    boolean isTrackArtistValid(final String artist) {
        return artist.length() > ZERO && artist.length() < MAX_ARTIST_LENGTH;
    }
    boolean isGenreValid(final String genre) {
        return genre.length() > ZERO && genre.length() < MAX_GENRE_LENGTH;
    }
    boolean isPriceValid(final String price) {
        return  (price.length() != ZERO && price.length() <= MAX_PRICE_LENGTH)
                && canConvertToUnsignedDouble(price);
    }
    boolean validateConfirmPass(final String confirmPass, final String pass) {
        return pass.equals(confirmPass);
    }
    private boolean canConvertToUnsignedDouble(final String value) {
        try {
            Double doubleValue = Double.valueOf(value);
            return (doubleValue > ZERO);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean canConvertToUnsignedInt(final String value) {
        try {
            Integer intValue = Integer.valueOf(value);
            return (intValue >= ZERO && intValue <= MAX_BONUS);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean isBonusValid(final String bonus) {
        return  (bonus.length() != ZERO && bonus.length()
                <= MAX_PRICE_LENGTH) && canConvertToUnsignedInt(bonus);
    }
    public boolean isCashValid(final String cash) {
        return  !(cash.length() == ZERO && cash.length()
                > MAX_CASH_LENGTH) && canConvertToUnsignedDouble(cash);
    }
    public boolean isCommentValid(final String comment) {
        return (comment.length() > ZERO && comment.length()
                < MAX_COMMENT_LENGTH);
    }
}
