package by.goncharov.epamsound.controller.command.common;

import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.controller.SessionRequestContent;

public class ChangeLanguageCommand extends AbstractCommand {
    private static final String LANGUAGE_ATTRIBUTE = "lang";
    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        Object str = servletSessionRequestContent.getRequestParameter(
                LANGUAGE_ATTRIBUTE);
        if (str != null) {
            messageManager.setCurrentLocale(str.toString());
            servletSessionRequestContent.setSessionAttribute(
                    LOCALE_ATTRIBUTE, str.toString());
        }
        Object property = servletSessionRequestContent
                .getSessionAttribute(CUR_PAGE_ATTR);
        if (property == null) {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        } else {
            page = ConfigurationManager.getProperty(property.toString());
        }
        return page;
    }
}
