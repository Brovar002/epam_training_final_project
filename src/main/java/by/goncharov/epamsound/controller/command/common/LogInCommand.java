package by.goncharov.epamsound.controller.command.common;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.LoginService;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogInCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String TRUE = "true";
    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        String login = servletSessionRequestContent
                .getRequestParameter(PARAM_LOGIN);
        String password = servletSessionRequestContent
                .getRequestParameter(PARAM_PASSWORD);
        try {
            if (new LoginService().checkLogin(login, password)) {
                servletSessionRequestContent.setSessionAttribute(
                        IS_LOGIN, TRUE);
                UserService userService = new UserService();
                User user = userService.findUser(login);
                servletSessionRequestContent.setSessionAttribute(
                        USER_ATTRIBUTE, user);
                page = ConfigurationManager.getProperty(ConfigurationManager
                        .HOME_PATH);
            } else {
                servletSessionRequestContent.setRequestAttribute(PARAM_LOGIN,
                        login);
                servletSessionRequestContent.setRequestAttribute(PARAM_PASSWORD,
                        password);
                servletSessionRequestContent.setRequestAttribute(ERROR,
                        messageManager.getProperty(MessageManager.LOGIN_ERROR));
                page = ConfigurationManager.getProperty(ConfigurationManager
                        .LOGIN_PATH);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception during login command", e);
            page = redirectToErrorPage(servletSessionRequestContent, e);
        }
        return page;
    }
}
