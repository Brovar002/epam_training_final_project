package by.goncharov.epamsound.controller.command.admin;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowUsersCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private final String USER_LIST_ATTR = "users";
    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        User user = (User) servletSessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            UserService userService = new UserService();
            try {
                List<User> userList = userService.findClients();
                servletSessionRequestContent
                        .setSessionAttribute(USER_LIST_ATTR, userList);
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.SET_BONUS_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during clients search", e);
                page = redirectToErrorPage(servletSessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
