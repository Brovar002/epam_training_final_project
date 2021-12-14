package by.goncharov.epamsound.controller.command.admin;

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
 * Class for execute command of setting bonus.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see UserService
 * @see User
 * @see SessionRequestContent
 */
public class SetBonusCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_ID_ATTR = "user_id";
    private static final String BONUS_ATTR = "bonus";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        User user = (User) sessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            String userIdStr = sessionRequestContent
                    .getRequestParameter(USER_ID_ATTR);
            String bonus = sessionRequestContent
                    .getRequestParameter(BONUS_ATTR + userIdStr);
            Integer userId = Integer.valueOf(userIdStr);
            UserService userService = new UserService();
            try {
                String res = userService.setBonus(userId, bonus);
                if (SUCCESS.equals(res)) {
                    sessionRequestContent.setRequestAttribute(
                            SUCCESS, messageManager.getProperty(
                                    MessageManager.SET_BONUS_SUCCESS));
                    page = ConfigurationManager.getProperty(
                            ConfigurationManager.SHOW_USERS_PATH);
                } else {
                    sessionRequestContent.setRequestAttribute(
                            ERROR, res);
                    page = ConfigurationManager.getProperty(
                            ConfigurationManager.SHOW_USERS_PATH);
                }
            } catch (ServiceException e) {
                LOGGER.error("Exception during bonus setting", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
