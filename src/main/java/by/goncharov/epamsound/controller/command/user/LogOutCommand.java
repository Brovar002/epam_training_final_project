package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.controller.SessionRequestContent;

/**
 * Class for execute command of log out.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see SessionRequestContent
 */
public class LogOutCommand implements Command {
    private static final String IS_LOGIN = "is_login";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        sessionRequestContent.setSessionAttribute(IS_LOGIN, null);
        sessionRequestContent.setSessionAttribute(USER_ATTRIBUTE, null);
        return ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
    }
}
