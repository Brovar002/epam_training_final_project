package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.controller.SessionRequestContent;

public class LogOutCommand extends AbstractCommand {
    private static final String IS_LOGIN = "is_login";
    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        servletSessionRequestContent.setSessionAttribute(IS_LOGIN, null);
        servletSessionRequestContent.setSessionAttribute(USER_ATTRIBUTE, null);
        return ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
    }
}
