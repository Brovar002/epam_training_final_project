package by.goncharov.epamsound.controller.command.common;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for execute command of showing main menu.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see Track
 * @see TrackService
 * @see SessionRequestContent
 */
public class MainMenuCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_DELETED = "is_deleted";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        TrackService trackService = new TrackService();
        List<Track> trackList = new ArrayList<>();
        try {
            trackList = trackService.lastTracks();
        } catch (ServiceException e) {
            LOGGER.error("Exception during last ordered tracks search", e);
        }
        sessionRequestContent.setSessionAttribute(NUM_PAGE, 0);
        sessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR, trackList);
        sessionRequestContent.setSessionAttribute(IS_DELETED, false);
        return ConfigurationManager.getProperty(ConfigurationManager.MAIN_PATH);
    }
}
