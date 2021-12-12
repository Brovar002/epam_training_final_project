package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.Messenger;
import by.goncharov.epamsound.controller.SessionRequestContent;

public interface Command extends Messenger {
    String LOCALE_ATTRIBUTE = "locale";
    String CUR_PAGE_ATTR = "page";
    String ERROR = "error";
    String SUCCESS = "success";
    String USER_ATTRIBUTE = "user";
    String IS_LOGIN = "is_login";
    String NUM_PAGE = "num_page";
    String execute(SessionRequestContent sessionRequestContent);
    default String redirectToErrorPage(final SessionRequestContent
                                               sessionRequestContent,
                                       final Exception e) {
        sessionRequestContent.setRequestAttribute(ERROR, e);
        return ConfigurationManager.getProperty(ConfigurationManager
                .ERROR_PATH);
    }
}
