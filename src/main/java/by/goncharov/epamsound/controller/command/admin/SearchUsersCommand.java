package by.goncharov.epamsound.controller.command.admin;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Class for execute command of searching users.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see UserService
 * @see User
 * @see SessionRequestContent
 */
public class SearchUsersCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_PARAMETER = "find";
    private static final String USER_LIST_ATTR = "users";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        User user = (User) sessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            UserService userService = new UserService();
            try {
                String str = sessionRequestContent
                        .getRequestParameter(FIND_PARAMETER);
                List<User> userList = userService.findSuitableUsers(str);
                sessionRequestContent.setSessionAttribute(
                        USER_LIST_ATTR, userList);
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.SET_BONUS_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during users search", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
