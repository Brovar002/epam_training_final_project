package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DownloadCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_ID_ATTR = "track_id";
    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        String filePath;
        int trackId = Integer.parseInt(servletSessionRequestContent
                .getRequestParameter(TRACK_ID_ATTR));
        TrackService trackService = new TrackService();
        try {
            filePath = trackService.findTrackPath(trackId);
        } catch (ServiceException e) {
            LOGGER.error("Exception during track path search", e);
            filePath = "";
        }
        return filePath;
    }
}
