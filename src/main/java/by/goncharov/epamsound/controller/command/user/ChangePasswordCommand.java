package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.manager.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangePasswordCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_CONF_PASS = "password2";
    private static final String PARAM_NEW_PASS = "new_pass";
    @Override
    public String execute(final ServletSessionRequestContent
                                      sessionRequestContent) {
        String page;
        String logined = (String) sessionRequestContent
                .getSessionAttribute(IS_LOGIN);
        if (Boolean.parseBoolean(logined)) {
            User user = (User) sessionRequestContent
                    .getSessionAttribute(USER_ATTRIBUTE);
            String password = sessionRequestContent
                    .getRequestParameter(PARAM_PASSWORD);
            String newPassword = sessionRequestContent
                    .getRequestParameter(PARAM_NEW_PASS);
            String confPassword = sessionRequestContent
                    .getRequestParameter(PARAM_CONF_PASS);
            UserService userService = new UserService();
            String res;
            try {
                res = userService.changePass(user.getId(), user.getPassword(),
                        password, newPassword, confPassword);
                if (SUCCESS.equals(res)) {
                    user.setPassword(newPassword);
                    sessionRequestContent.setRequestAttribute(SUCCESS,
                            messageManager.getProperty(MessageManager
                                    .CHANGE_SUCCESS));
                    sessionRequestContent.setSessionAttribute(
                            USER_ATTRIBUTE, user);
                    page = ConfigurationManager.getProperty(
                            ConfigurationManager.PROFILE_PATH);
                } else {
                    sessionRequestContent.setRequestAttribute(ERROR, res);
                    return ConfigurationManager.getProperty(
                            ConfigurationManager.CHANGE_PASS_PATH);
                }
            } catch (ServiceException e) {
                LOGGER.error("Exception during change password command", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .HOME_PATH);
        }
        return page;
    }
}
