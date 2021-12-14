package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for execute command of changing data about user.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see User
 * @see UserService
 * @see SessionRequestContent
 */
public class ChangeDataCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_ATTR = "user";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_EMAIL = "email";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        String logined = (String) sessionRequestContent
                .getSessionAttribute(IS_LOGIN);
        if (Boolean.parseBoolean(logined)) {
            User user = (User) sessionRequestContent
                    .getSessionAttribute(USER_ATTR);
            String login = sessionRequestContent
                    .getRequestParameter(PARAM_LOGIN);
            String email = sessionRequestContent
                    .getRequestParameter(PARAM_EMAIL);
            UserService userService = new UserService();
            String res;
            try {
                if (!login.equals(user.getLogin())) {
                    res = userService.changeLogin(user.getId(), login);
                    if (SUCCESS.equals(res)) {
                        user.setLogin(login);
                    } else {
                        sessionRequestContent.setRequestAttribute(
                                ERROR, res);
                        return ConfigurationManager
                                .getProperty(ConfigurationManager.CHANGE_PATH);
                    }
                }
                if (!email.equals(user.getEmail())) {
                    res = userService.changeEmail(user.getId(), email);
                    if (SUCCESS.equals(res)) {
                        user.setEmail(email);
                    } else {
                        sessionRequestContent.setRequestAttribute(
                                ERROR, res);
                        return ConfigurationManager.getProperty(
                                ConfigurationManager.CHANGE_PATH);
                    }
                }
                sessionRequestContent.setSessionAttribute(USER_ATTR,
                        user);
                sessionRequestContent.setRequestAttribute(SUCCESS,
                        messageManager.getProperty(
                                MessageManager.CHANGE_SUCCESS));
                page = ConfigurationManager.getProperty(ConfigurationManager
                        .PROFILE_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during change command", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .HOME_PATH);
        }
        return page;
    }
}
