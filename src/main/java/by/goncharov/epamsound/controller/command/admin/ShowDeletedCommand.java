package by.goncharov.epamsound.controller.command.admin;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 * Class for execute command of showing deleted track.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see TrackService
 * @see Track
 * @see User
 * @see SessionRequestContent
 */
public class ShowDeletedCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_DELETED = "is_deleted";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        User user = (User) sessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            List<Track> deletedTracks;
            TrackService trackService = new TrackService();
            try {
                deletedTracks = trackService.findDeletedTracks();
                sessionRequestContent.setSessionAttribute(
                        TRACK_LIST_ATTR, deletedTracks);
                sessionRequestContent.setSessionAttribute(
                        IS_DELETED, true);
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.TRACK_RECOVER_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during deleted tracks search", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
