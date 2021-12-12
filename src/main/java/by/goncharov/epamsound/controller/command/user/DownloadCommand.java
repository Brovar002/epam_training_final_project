package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DownloadCommand implements Command {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_ID_ATTR = "track_id";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String filePath;
        int trackId = Integer.parseInt(sessionRequestContent
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
