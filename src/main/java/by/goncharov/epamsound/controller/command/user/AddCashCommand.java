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
 * Class for execute command of adding cash.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see User
 * @see UserService
 * @see SessionRequestContent
 */
public class AddCashCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_CASH = "cash";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        String logined = (String) sessionRequestContent
                .getSessionAttribute(IS_LOGIN);
        if (Boolean.parseBoolean(logined)) {
            User user = (User) sessionRequestContent
                    .getSessionAttribute(USER_ATTRIBUTE);
            UserService userService = new UserService();
            String cash = sessionRequestContent
                    .getRequestParameter(PARAM_CASH);
            try {
                userService.addCash(user, cash);
                sessionRequestContent.setRequestAttribute(
                        SUCCESS, messageManager.getProperty(
                                MessageManager.CHANGE_SUCCESS));
                sessionRequestContent.setSessionAttribute(
                        USER_ATTRIBUTE, user);
                page = ConfigurationManager.getProperty(ConfigurationManager
                        .PROFILE_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception funds addition command", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .HOME_PATH);
        }
        return page;
    }
}
