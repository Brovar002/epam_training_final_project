package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeDataCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_ATTR = "user";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_EMAIL = "email";
    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        String logined = (String) servletSessionRequestContent
                .getSessionAttribute(IS_LOGIN);
        if (Boolean.parseBoolean(logined)) {
            User user = (User) servletSessionRequestContent
                    .getSessionAttribute(USER_ATTR);
            String login = servletSessionRequestContent
                    .getRequestParameter(PARAM_LOGIN);
            String email = servletSessionRequestContent
                    .getRequestParameter(PARAM_EMAIL);
            UserService userService = new UserService();
            String res;
            try {
                if (!login.equals(user.getLogin())) {
                    res = userService.changeLogin(user.getId(), login);
                    if (SUCCESS.equals(res)) {
                        user.setLogin(login);
                    } else {
                        servletSessionRequestContent.setRequestAttribute(
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
                        servletSessionRequestContent.setRequestAttribute(
                                ERROR, res);
                        return ConfigurationManager.getProperty(
                                ConfigurationManager.CHANGE_PATH);
                    }
                }
                servletSessionRequestContent.setSessionAttribute(USER_ATTR,
                        user);
                servletSessionRequestContent.setRequestAttribute(SUCCESS,
                        messageManager.getProperty(
                                MessageManager.CHANGE_SUCCESS));
                page = ConfigurationManager.getProperty(ConfigurationManager
                        .PROFILE_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during change command", e);
                page = redirectToErrorPage(servletSessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .HOME_PATH);
        }
        return page;
    }
}
