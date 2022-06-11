package by.goncharov.epamsound.controller.command.admin;

import by.goncharov.epamsound.beans.Genre;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.GenreService;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for execute command of adding track.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see TrackService
 * @see User
 * @see GenreService
 * @see SessionRequestContent
 */
public class AddTrackCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String RESULT_ATTR = "result";
    private static final String REAL_PATH_ATTRIBUTE = "path";
    private static final String NAME_PARAM = "name";
    private static final String ARTIST_PARAM = "artist";
    private static final String GENRE_PARAM = "genre";
    private static final String GENRES_ATTR = "genres";
    private static final String PRICE_PARAM = "price";
    /**
     * Execute string.
     *
     * @param sessionRequestContent the session request content
     * @return the string
     */
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        User user = (User) sessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            boolean result = Boolean.parseBoolean(
                    sessionRequestContent
                            .getRequestAttribute(RESULT_ATTR).toString());
            if (result) {
                TrackService trackService = new TrackService();
                String name = sessionRequestContent
                        .getRequestParameter(NAME_PARAM);
                String artist = sessionRequestContent
                        .getRequestParameter(ARTIST_PARAM);
                Genre genre = (Genre) sessionRequestContent
                        .getRequestAttribute(GENRE_PARAM);
                String price = sessionRequestContent
                        .getRequestParameter(PRICE_PARAM);
                String realPath = sessionRequestContent
                        .getRequestAttribute(REAL_PATH_ATTRIBUTE).toString();
                try {
                    String res = trackService.addTrack(name, artist, price,
                            genre, realPath);
                    if (SUCCESS.equals(res)) {
                        GenreService genreService = new GenreService();
                        List<String> genreList;
                        genreList = genreService.findGenres();
                        sessionRequestContent.setSessionAttribute(
                                GENRES_ATTR, genreList);
                        sessionRequestContent.setRequestAttribute(
                                SUCCESS, messageManager.getProperty(
                                        MessageManager.ADD_TRACK_SUCCESS));
                        page = ConfigurationManager.getProperty(
                                ConfigurationManager.HOME_PATH);
                    } else {
                        sessionRequestContent.setRequestAttribute(
                                ERROR, res);
                        page = ConfigurationManager.getProperty(
                                ConfigurationManager.HOME_PATH);
                    }
                } catch (ServiceException e) {
                    LOGGER.error("Exception during track addition command", e);
                    sessionRequestContent.setRequestAttribute(
                            ERROR, messageManager.getProperty(
                                    MessageManager.ADD_TRACK_ERROR));
                    page = ConfigurationManager.getProperty(
                            ConfigurationManager.HOME_PATH);
                }
            } else {
                sessionRequestContent.setRequestAttribute(
                        ERROR, messageManager.getProperty(
                                MessageManager.ADD_TRACK_ERROR));
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.HOME_PATH);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .HOME_PATH);
        }
        return page;
    }
}
