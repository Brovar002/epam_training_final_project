package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.Messenger;
import by.goncharov.epamsound.controller.SessionRequestContent;

public abstract class AbstractCommand implements Messenger {
    protected static final String LOCALE_ATTRIBUTE = "locale";
    protected static final String CUR_PAGE_ATTR = "page";
    protected static final String ERROR = "error";
    protected static final String SUCCESS = "success";
    protected static final String USER_ATTRIBUTE = "user";
    protected static final String IS_LOGIN = "is_login";
    protected static final String NUM_PAGE = "num_page";
    public abstract String execute(SessionRequestContent
                                           servletSessionRequestContent);
    public String redirectToErrorPage(final SessionRequestContent
                                              servletSessionRequestContent,
                                      final Exception e) {
        servletSessionRequestContent.setRequestAttribute(ERROR, e);
        return ConfigurationManager.getProperty(ConfigurationManager
                .ERROR_PATH);
    }
}
