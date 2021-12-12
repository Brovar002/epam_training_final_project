package by.goncharov.epamsound.controller.command.common;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class SearchCommand implements Command {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_PARAMETER = "find";
    private static final String SEARCH_ATTR = "search";
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_DELETED = "is_deleted";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        TrackService trackService = new TrackService();
        try {
            String str = sessionRequestContent.getRequestParameter(
                    FIND_PARAMETER);
            List<Track> trackList = trackService.findSuitableTracks(str);
            sessionRequestContent.setSessionAttribute(IS_DELETED, false);
            sessionRequestContent.setRequestAttribute(SEARCH_ATTR, true);
            sessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR,
                    trackList);
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .MAIN_PATH);
        } catch (ServiceException e) {
            LOGGER.error("Exception during tracks search", e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}
