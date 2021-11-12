package by.goncharov.epamsound.controller.command.admin;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.manager.ConfigurationManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class ShowDeletedCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_DELETED = "is_deleted";
    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        User user = (User) servletSessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            List<Track> deletedTracks;
            TrackService trackService = new TrackService();
            try {
                deletedTracks = trackService.findDeletedTracks();
                servletSessionRequestContent.setSessionAttribute(
                        TRACK_LIST_ATTR, deletedTracks);
                servletSessionRequestContent.setSessionAttribute(
                        IS_DELETED, true);
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.TRACK_RECOVER_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during deleted tracks search", e);
                page = redirectToErrorPage(servletSessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
