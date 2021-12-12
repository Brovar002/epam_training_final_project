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

public class AddCashCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_CASH = "cash";
    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        String logined = (String) servletSessionRequestContent
                .getSessionAttribute(IS_LOGIN);
        if (Boolean.parseBoolean(logined)) {
            User user = (User) servletSessionRequestContent
                    .getSessionAttribute(USER_ATTRIBUTE);
            UserService userService = new UserService();
            String cash = servletSessionRequestContent
                    .getRequestParameter(PARAM_CASH);
            try {
                userService.addCash(user, cash);
                servletSessionRequestContent.setRequestAttribute(
                        SUCCESS, messageManager.getProperty(
                                MessageManager.CHANGE_SUCCESS));
                servletSessionRequestContent.setSessionAttribute(
                        USER_ATTRIBUTE, user);
                page = ConfigurationManager.getProperty(ConfigurationManager
                        .PROFILE_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception funds addition command", e);
                page = redirectToErrorPage(servletSessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .HOME_PATH);
        }
        return page;
    }
}
