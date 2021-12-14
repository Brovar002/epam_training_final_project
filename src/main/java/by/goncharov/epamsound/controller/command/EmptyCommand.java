package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.controller.SessionRequestContent;

/**
 * Class of empty command.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see ConfigurationManager
 * @see SessionRequestContent
 */
public class EmptyCommand implements Command {
    private static final String DEFAULT_LOCALE = "ru_RU";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page = ConfigurationManager.getProperty(
                ConfigurationManager.HOME_PATH);
        sessionRequestContent.setSessionAttribute(
                CUR_PAGE_ATTR, ConfigurationManager.HOME_PATH);
        sessionRequestContent.setSessionAttribute(
                LOCALE_ATTRIBUTE, DEFAULT_LOCALE);
        return page;
    }
}
