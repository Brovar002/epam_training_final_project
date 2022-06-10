package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.Messenger;
import by.goncharov.epamsound.controller.SessionRequestContent;

/**
 * The interface Command.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Messenger
 * @see SessionRequestContent
 */
public interface Command extends Messenger {
    /**
     * The constant LOCALE_ATTRIBUTE.
     */
    String LOCALE_ATTRIBUTE = "locale";
    /**
     * The constant CUR_PAGE_ATTR.
     */
    String CUR_PAGE_ATTR = "page";
    /**
     * The constant ERROR.
     */
    String ERROR = "error";
    /**
     * The constant SUCCESS.
     */
    String SUCCESS = "success";
    /**
     * The constant USER_ATTRIBUTE.
     */
    String USER_ATTRIBUTE = "user";
    /**
     * The constant TRACK_ATTRIBUTE.
     */
    String TRACK_ATTRIBUTE = "track";
    /**
     * The constant IS_LOGIN.
     */
    String IS_LOGIN = "is_login";
    /**
     * The constant NUM_PAGE.
     */
    String NUM_PAGE = "num_page";

    /**
     * Execute string.
     *
     * @param sessionRequestContent the session request content
     * @return the string
     */
    String execute(SessionRequestContent sessionRequestContent);

    /**
     * Redirect to error page string.
     *
     * @param sessionRequestContent the session request content
     * @param e                     the e
     * @return the string
     */
    default String redirectToErrorPage(final SessionRequestContent
                                               sessionRequestContent,
                                       final Exception e) {
        sessionRequestContent.setRequestAttribute(ERROR, e);
        return ConfigurationManager.getProperty(ConfigurationManager
                .ERROR_PATH);
    }
}
