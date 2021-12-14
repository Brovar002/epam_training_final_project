package by.goncharov.epamsound.controller.command.common;

import by.goncharov.epamsound.beans.Comment;
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
 * Class for execute command of showing track information.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see TrackService
 * @see Track
 * @see Comment
 * @see SessionRequestContent
 */
public class TrackInfoCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_ID = "track_id";
    private static final String TRACK_ATTRIBUTE = "track";
    private static final String COMMENTS_ATTRIBUTE = "comments";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        int trackId = Integer.parseInt(sessionRequestContent
                .getRequestParameter(TRACK_ID));
        TrackService trackService = new TrackService();
        try {
            List<Comment> comments = trackService.findTrackComments(trackId);
            Track track = trackService.findTrackById(trackId);
            sessionRequestContent.setSessionAttribute(
                    TRACK_ATTRIBUTE, track);
            sessionRequestContent.setSessionAttribute(
                    COMMENTS_ATTRIBUTE, comments);
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .TRACK_INFO_PATH);
        } catch (ServiceException e) {
            LOGGER.error("Exception during command about track info", e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}
