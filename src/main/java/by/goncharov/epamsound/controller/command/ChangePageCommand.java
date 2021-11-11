package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.manager.ConfigurationManager;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;

public class ChangePageCommand extends AbstractCommand {
    private static final String PAGE_PARAM = "page";
    private static final String NUM_OF_PAGES = "number_of_pages";

    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        Object pageObj = servletSessionRequestContent
                .getRequestParameter(PAGE_PARAM);
        Object allPagesObj = servletSessionRequestContent
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
        servletSessionRequestContent.setSessionAttribute(NUM_PAGE, newPageNum);
        return ConfigurationManager.getProperty(servletSessionRequestContent
                .getSessionAttribute(CUR_PAGE_ATTR).toString());
    }
}
