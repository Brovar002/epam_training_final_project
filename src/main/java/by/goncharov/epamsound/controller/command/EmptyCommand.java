package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.manager.ConfigurationManager;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;

public class EmptyCommand extends AbstractCommand {
    private static final String DEFAULT_LOCALE = "ru_RU";
    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        String page = ConfigurationManager.getProperty(
                ConfigurationManager.HOME_PATH);
        servletSessionRequestContent.setSessionAttribute(
                CUR_PAGE_ATTR, ConfigurationManager.HOME_PATH);
        servletSessionRequestContent.setSessionAttribute(
                LOCALE_ATTRIBUTE, DEFAULT_LOCALE);
        return page;
    }
}
