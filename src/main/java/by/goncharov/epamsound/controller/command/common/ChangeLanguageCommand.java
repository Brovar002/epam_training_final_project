package by.goncharov.epamsound.controller.command.common;

import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.controller.SessionRequestContent;

public class ChangeLanguageCommand implements Command {
    private static final String LANGUAGE_ATTRIBUTE = "lang";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        Object str = sessionRequestContent.getRequestParameter(
                LANGUAGE_ATTRIBUTE);
        if (str != null) {
            messageManager.setCurrentLocale(str.toString());
            sessionRequestContent.setSessionAttribute(
                    LOCALE_ATTRIBUTE, str.toString());
        }
        Object property = sessionRequestContent
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
