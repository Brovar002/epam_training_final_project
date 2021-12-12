package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.service.UserService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CommentCommand implements Command {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_ID_PARAM = "track_id";
    private static final String COMMENT_PARAM = "comment_area";
    private static final String COMMENTS_ATTRIBUTE = "comments";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        String logined = (String) sessionRequestContent
                .getSessionAttribute(IS_LOGIN);
        if (Boolean.parseBoolean(logined)) {
            User user = (User) sessionRequestContent
                    .getSessionAttribute(USER_ATTRIBUTE);
            int trackId = Integer.parseInt(sessionRequestContent
                    .getRequestParameter(TRACK_ID_PARAM));
            String commentText = sessionRequestContent
                    .getRequestParameter(COMMENT_PARAM);
            UserService userService = new UserService();
            TrackService trackService = new TrackService();
            if (!commentText.isEmpty()) {
                try {
                    String res = userService.addComment(user, commentText,
                            trackId);
                    if (SUCCESS.equals(res)) {
                        List<Comment> comments = trackService
                                .findTrackComments(trackId);
                        sessionRequestContent.setSessionAttribute(
                                COMMENTS_ATTRIBUTE, comments);
                        page = ConfigurationManager.getProperty(
                                ConfigurationManager.TRACK_INFO_PATH);
                    } else {
                        sessionRequestContent.setRequestAttribute(
                                ERROR, res);
                        return ConfigurationManager.getProperty(
                                ConfigurationManager.TRACK_INFO_PATH);
                    }
                } catch (ServiceException e) {
                    LOGGER.error("Exception during comment addition command",
                            e);
                    page = redirectToErrorPage(sessionRequestContent, e);
                }
            } else {
                sessionRequestContent.setRequestAttribute(ERROR,
                        messageManager.getProperty(MessageManager
                                .ADD_COMMENT_EMPTY));
                return ConfigurationManager.getProperty(
                        ConfigurationManager.TRACK_INFO_PATH);
            }
        } else {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
