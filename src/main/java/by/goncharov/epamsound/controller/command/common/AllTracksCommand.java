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

/**
 * Class for execute command of showung all tracks.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see TrackService
 * @see Track
 * @see SessionRequestContent
 */
public class AllTracksCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger(AllTracksCommand.class);
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_DELETED = "is_deleted";
    private static final String ALL_ATTR = "all";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        TrackService trackService = new TrackService();
        try {
            List<Track> trackList = trackService.findAllTracks();
            sessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR,
                    trackList);
            sessionRequestContent.setSessionAttribute(IS_DELETED, false);
            sessionRequestContent.setRequestAttribute(ALL_ATTR, true);
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .MAIN_PATH);
        } catch (ServiceException e) {
            LOGGER.error("Exception during all tracks search", e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}
