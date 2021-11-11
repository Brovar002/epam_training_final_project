package by.goncharov.epamsound.controller.command.common;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.manager.ConfigurationManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class SearchCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_PARAMETER = "find";
    private static final String SEARCH_ATTR = "search";
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_DELETED = "is_deleted";
    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        TrackService trackService = new TrackService();
        try {
            String str = servletSessionRequestContent.getRequestParameter(
                    FIND_PARAMETER);
            List<Track> trackList = trackService.findSuitableTracks(str);
            servletSessionRequestContent.setSessionAttribute(IS_DELETED, false);
            servletSessionRequestContent.setRequestAttribute(SEARCH_ATTR, true);
            servletSessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR,
                    trackList);
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .MAIN_PATH);
        } catch (ServiceException e) {
            LOGGER.error("Exception during tracks search", e);
            page = redirectToErrorPage(servletSessionRequestContent, e);
        }
        return page;
    }
}
