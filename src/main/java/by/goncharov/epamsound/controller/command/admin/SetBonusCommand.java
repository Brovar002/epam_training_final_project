package by.goncharov.epamsound.controller.command.admin;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetBonusCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_ID_ATTR = "user_id";
    private static final String BONUS_ATTR = "bonus";
    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        User user = (User) servletSessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            String userIdStr = servletSessionRequestContent
                    .getRequestParameter(USER_ID_ATTR);
            String bonus = servletSessionRequestContent
                    .getRequestParameter(BONUS_ATTR + userIdStr);
            Integer userId = Integer.valueOf(userIdStr);
            UserService userService = new UserService();
            try {
                String res = userService.setBonus(userId, bonus);
                if (SUCCESS.equals(res)) {
                    servletSessionRequestContent.setRequestAttribute(
                            SUCCESS, messageManager.getProperty(
                                    MessageManager.SET_BONUS_SUCCESS));
                    page = ConfigurationManager.getProperty(
                            ConfigurationManager.SHOW_USERS_PATH);
                } else {
                    servletSessionRequestContent.setRequestAttribute(
                            ERROR, res);
                    page = ConfigurationManager.getProperty(
                            ConfigurationManager.SHOW_USERS_PATH);
                }
            } catch (ServiceException e) {
                LOGGER.error("Exception during bonus setting", e);
                page = redirectToErrorPage(servletSessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
