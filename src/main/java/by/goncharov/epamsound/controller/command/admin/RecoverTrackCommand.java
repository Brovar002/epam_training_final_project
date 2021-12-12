package by.goncharov.epamsound.controller.command.admin;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecoverTrackCommand implements Command {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_ID = "track_id";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        User user = (User) sessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            int trackId = Integer.parseInt(sessionRequestContent
                    .getRequestParameter(TRACK_ID));
            TrackService trackService = new TrackService();
            try {
                trackService.recoverTrackById(trackId);
                sessionRequestContent.setRequestAttribute(
                        SUCCESS, messageManager.getProperty(
                                MessageManager.TRACK_RECOVER_SUCCESS));
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.TRACK_DELETED_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during track recover", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
