package by.goncharov.epamsound.controller.command.admin;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.manager.ConfigurationManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SearchUsersCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_PARAMETER = "find";
    private static final String USER_LIST_ATTR = "users";
    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        User user = (User) servletSessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            UserService userService = new UserService();
            try {
                String str = servletSessionRequestContent
                        .getRequestParameter(FIND_PARAMETER);
                List<User> userList = userService.findSuitableUsers(str);
                servletSessionRequestContent.setSessionAttribute(
                        USER_LIST_ATTR, userList);
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.SET_BONUS_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during users search", e);
                page = redirectToErrorPage(servletSessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
