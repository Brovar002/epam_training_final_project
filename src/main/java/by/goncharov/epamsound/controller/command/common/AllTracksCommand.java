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

public class AllTracksCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger(AllTracksCommand.class);
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_DELETED = "is_deleted";
    private static final String ALL_ATTR = "all";
    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        TrackService trackService = new TrackService();
        try {
            List<Track> trackList = trackService.findAllTracks();
            servletSessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR,
                    trackList);
            servletSessionRequestContent.setSessionAttribute(IS_DELETED, false);
            servletSessionRequestContent.setRequestAttribute(ALL_ATTR, true);
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .MAIN_PATH);
        } catch (ServiceException e) {
            LOGGER.error("Exception during all tracks search", e);
            page = redirectToErrorPage(servletSessionRequestContent, e);
        }
        return page;
    }
}
