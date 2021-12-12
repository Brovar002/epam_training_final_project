package by.goncharov.epamsound.controller.command.common;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SingUpCommand implements Command {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_CONF_PASS = "password2";
    private static final String PARAM_EMAIL = "email";
    private static final String ROLE_ATTRIBUTE = "role";
    private static final String IS_LOGIN = "is_login";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        String login = sessionRequestContent.getRequestParameter(
                PARAM_LOGIN);
        String password = sessionRequestContent.getRequestParameter(
                PARAM_PASSWORD);
        String confPassword = sessionRequestContent.getRequestParameter(
                PARAM_CONF_PASS);
        String email = sessionRequestContent.getRequestParameter(
                PARAM_EMAIL);
        UserService userService = new UserService();
        try {
            String res = userService.singUp(login, password, confPassword,
                    email);
            if (SUCCESS.equals(res)) {
                User user = userService.findUser(login);
                sessionRequestContent.setSessionAttribute(
                        IS_LOGIN, "true");
                sessionRequestContent.setSessionAttribute(
                        USER_ATTRIBUTE, user);
                sessionRequestContent.setSessionAttribute(
                        ROLE_ATTRIBUTE, user.getRole());
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.HOME_PATH);
            } else {
                sessionRequestContent.setRequestAttribute(
                        PARAM_LOGIN, login);
                sessionRequestContent.setRequestAttribute(
                        PARAM_PASSWORD, password);
                sessionRequestContent.setRequestAttribute(
                        PARAM_CONF_PASS, confPassword);
                sessionRequestContent.setRequestAttribute(
                        PARAM_EMAIL, email);
                sessionRequestContent.setRequestAttribute(
                        ERROR, res);
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.SIGNUP_PATH);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception during sign up command", e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}
