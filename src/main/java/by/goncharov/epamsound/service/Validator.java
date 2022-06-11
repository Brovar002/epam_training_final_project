package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Genre;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.UserDaoImpl;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.manager.Messenger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Validator.
 * @author Goncharov Daniil
 */
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
    private final int MAX_BONUS = 100;
    private final int MAX_CASH_LENGTH = 5;
    private final int MAX_COMMENT_LENGTH = 65_535;

    /**
     * Is data valid string.
     *
     * @param login       the login
     * @param password    the password
     * @param confirmPass the confirm pass
     * @param email       the email
     * @return the string
     * @throws ServiceException the service exception
     */
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

    /**
     * Is email unique boolean.
     *
     * @param email the email
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isEmailUnique(final String email) throws ServiceException {
        UserDaoImpl userDAOImpl = new UserDaoImpl();
        try {
            return (userDAOImpl.findByEmail(email) == null);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Is email valid boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean isEmailValid(final String email) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Is login unique boolean.
     *
     * @param login the login
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isLoginUnique(final String login) throws ServiceException {
        UserDaoImpl userDAOImpl = new UserDaoImpl();
        try {
            return (userDAOImpl.findByLogin(login) == null);
        } catch (DaoException e) {
            throw new ServiceException("Error during checking login"
                    + " uniqueness", e);
        }
    }

    /**
     * Is login valid boolean.
     *
     * @param login the login
     * @return the boolean
     */
    boolean isLoginValid(final String login) {
        Pattern pattern = Pattern.compile(REGEX_LOGIN);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    /**
     * Is password valid boolean.
     *
     * @param password the password
     * @return the boolean
     */
    boolean isPasswordValid(final String password) {
        return (password.length() >= MIN_PASS_LENGTH
                && password.length() <= MAX_PASS_LENGTH);
    }

    /**
     * Is track valid boolean.
     *
     * @param name   the name
     * @param artist the artist
     * @param price  the price
     * @param genre  the genre
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean isTrackValid(final String name, final String artist,
                                final String price, final Genre genre)
            throws ServiceException {
        return  (isTrackNameValid(name) && isTrackArtistValid(artist)
                && isGenreValid(genre) && isPriceValid(price));
    }

    /**
     * Is track name valid boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean isTrackNameValid(final String name) {
        return name.length() > ZERO && name.length() < MAX_TRACK_NAME_LENGTH;
    }

    /**
     * Is track artist valid boolean.
     *
     * @param artist the artist
     * @return the boolean
     */
    boolean isTrackArtistValid(final String artist) {
        return artist.length() > ZERO && artist.length() < MAX_ARTIST_LENGTH;
    }

    /**
     * Is genre valid boolean.
     *
     * @param genre the genre
     * @return the boolean
     */
    boolean isGenreValid(final Genre genre) {
        return genre.getName().length() > ZERO && genre.getName().length() < MAX_GENRE_LENGTH;
    }

    /**
     * Is price valid boolean.
     *
     * @param price the price
     * @return the boolean
     */
    boolean isPriceValid(final String price) {
        return  (price.length() != ZERO && price.length() <= MAX_PRICE_LENGTH)
                && canConvertToUnsignedDouble(price);
    }

    /**
     * Validate confirm pass boolean.
     *
     * @param confirmPass the confirm pass
     * @param pass        the pass
     * @return the boolean
     */
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

    /**
     * Is bonus valid boolean.
     *
     * @param bonus the bonus
     * @return the boolean
     */
    public boolean isBonusValid(final String bonus) {
        return  (bonus.length() != ZERO && bonus.length()
                <= MAX_PRICE_LENGTH) && canConvertToUnsignedInt(bonus);
    }

    /**
     * Is cash valid boolean.
     *
     * @param cash the cash
     * @return the boolean
     */
    public boolean isCashValid(final String cash) {
        return  !(cash.length() == ZERO && cash.length()
                > MAX_CASH_LENGTH) && canConvertToUnsignedDouble(cash);
    }

    /**
     * Is comment valid boolean.
     *
     * @param comment the comment
     * @return the boolean
     */
    public boolean isCommentValid(final String comment) {
        return (comment.length() > ZERO && comment.length()
                < MAX_COMMENT_LENGTH);
    }
}
