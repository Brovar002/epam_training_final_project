package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.controller.SessionRequestContent;

public class ChangePageCommand implements Command {
    private static final String PAGE_PARAM = "page";
    private static final String NUM_OF_PAGES = "number_of_pages";

    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        Object pageObj = sessionRequestContent
                .getRequestParameter(PAGE_PARAM);
        Object allPagesObj = sessionRequestContent
                .getRequestParameter(NUM_OF_PAGES);
        int newPageNum;
        if (pageObj != null) {
            newPageNum = Integer.valueOf(pageObj.toString());
            if (allPagesObj == null || newPageNum > Integer.valueOf(
                    allPagesObj.toString())) {
                newPageNum = 0;
            }
        } else {
            newPageNum = 0;
        }
        sessionRequestContent.setSessionAttribute(NUM_PAGE, newPageNum);
        return ConfigurationManager.getProperty(sessionRequestContent
                .getSessionAttribute(CUR_PAGE_ATTR).toString());
    }
}
