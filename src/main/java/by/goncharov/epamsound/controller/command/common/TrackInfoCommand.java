package by.goncharov.epamsound.controller.command.common;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.manager.ConfigurationManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TrackInfoCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_ID = "track_id";
    private static final String TRACK_ATTRIBUTE = "track";
    private static final String COMMENTS_ATTRIBUTE = "comments";
    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        int trackId = Integer.parseInt(servletSessionRequestContent
                .getRequestParameter(TRACK_ID));
        TrackService trackService = new TrackService();
        try {
            List<Comment> comments = trackService.findTrackComments(trackId);
            Track track = trackService.findTrackById(trackId);
            servletSessionRequestContent.setSessionAttribute(
                    TRACK_ATTRIBUTE, track);
            servletSessionRequestContent.setSessionAttribute(
                    COMMENTS_ATTRIBUTE, comments);
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .TRACK_INFO_PATH);
        } catch (ServiceException e) {
            LOGGER.error("Exception during command about track info", e);
            page = redirectToErrorPage(servletSessionRequestContent, e);
        }
        return page;
    }
}
