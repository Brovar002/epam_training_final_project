package by.goncharov.epamsound.controller.command.common;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.LoginService;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for execute command of logging in.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see UserService
 * @see User
 * @see LoginService
 * @see SessionRequestContent
 */
public class LogInCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String TRUE = "true";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        String login = sessionRequestContent
                .getRequestParameter(PARAM_LOGIN);
        String password = sessionRequestContent
                .getRequestParameter(PARAM_PASSWORD);
        try {
            if (new LoginService().checkLogin(login, password)) {
                sessionRequestContent.setSessionAttribute(
                        IS_LOGIN, TRUE);
                UserService userService = new UserService();
                User user = userService.findUser(login);
                sessionRequestContent.setSessionAttribute(
                        USER_ATTRIBUTE, user);
                page = ConfigurationManager.getProperty(ConfigurationManager
                        .HOME_PATH);
            } else {
                sessionRequestContent.setRequestAttribute(PARAM_LOGIN,
                        login);
                sessionRequestContent.setRequestAttribute(PARAM_PASSWORD,
                        password);
                sessionRequestContent.setRequestAttribute(ERROR,
                        messageManager.getProperty(MessageManager.LOGIN_ERROR));
                page = ConfigurationManager.getProperty(ConfigurationManager
                        .LOGIN_PATH);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception during login command", e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}
