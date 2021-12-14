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
 * Class for execute command of showing genre.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see Track
 * @see TrackService
 * @see SessionRequestContent
 */
public class ShowGenreCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_DELETED = "is_deleted";
    private static final String GENRE_PARAMETER = "genre";
    private static final String IS_GENRE = "is_genre";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        String genre = sessionRequestContent.getRequestParameter(
                GENRE_PARAMETER);
        if (genre == null) {
            genre = sessionRequestContent.getSessionAttribute(
                    GENRE_PARAMETER).toString();
        }
        TrackService trackService = new TrackService();
        try {
            List<Track> trackList = trackService.findTracksByGenre(genre);
            sessionRequestContent.setSessionAttribute(
                    GENRE_PARAMETER, genre);
            sessionRequestContent.setSessionAttribute(
                    TRACK_LIST_ATTR, trackList);
            sessionRequestContent.setSessionAttribute(
                    IS_DELETED, false);
            sessionRequestContent.setRequestAttribute(
                    IS_GENRE, true);
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.MAIN_PATH);
        } catch (ServiceException e) {
            LOGGER.error("Exception during tracks by genre search", e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}
