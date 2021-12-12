package by.goncharov.epamsound.controller.command.admin;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
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

public class AddTrackCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String RESULT_ATTR = "result";
    private static final String REAL_PATH_ATTRIBUTE = "path";
    private static final String NAME_PARAM = "name";
    private static final String ARTIST_PARAM = "artist";
    private static final String GENRE_PARAM = "genre";
    private static final String GENRES_ATTR = "genres";
    private static final String PRICE_PARAM = "price";
    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        User user = (User) servletSessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            boolean result = Boolean.parseBoolean(
                    servletSessionRequestContent
                            .getRequestAttribute(RESULT_ATTR).toString());
            if (result) {
                TrackService trackService = new TrackService();
                String name = servletSessionRequestContent
                        .getRequestParameter(NAME_PARAM);
                String artist = servletSessionRequestContent
                        .getRequestParameter(ARTIST_PARAM);
                String genre = servletSessionRequestContent
                        .getRequestParameter(GENRE_PARAM);
                String price = servletSessionRequestContent
                        .getRequestParameter(PRICE_PARAM);
                String realPath = servletSessionRequestContent
                        .getRequestAttribute(REAL_PATH_ATTRIBUTE).toString();
                try {
                    String res = trackService.addTrack(name, artist, price,
                            genre, realPath);
                    if (SUCCESS.equals(res)) {
                        GenreService genreService = new GenreService();
                        List<String> genreList = new ArrayList<>();
                        genreList = genreService.findGenres();
                        servletSessionRequestContent.setSessionAttribute(
                                GENRES_ATTR, genreList);
                        servletSessionRequestContent.setRequestAttribute(
                                SUCCESS, messageManager.getProperty(
                                        MessageManager.ADD_TRACK_SUCCESS));
                        page = ConfigurationManager.getProperty(
                                ConfigurationManager.HOME_PATH);
                    } else {
                        servletSessionRequestContent.setRequestAttribute(
                                ERROR, res);
                        page = ConfigurationManager.getProperty(
                                ConfigurationManager.HOME_PATH);
                    }
                } catch (ServiceException e) {
                    LOGGER.error("Exception during track addition command", e);
                    servletSessionRequestContent.setRequestAttribute(
                            ERROR, messageManager.getProperty(
                                    MessageManager.ADD_TRACK_ERROR));
                    page = ConfigurationManager.getProperty(
                            ConfigurationManager.HOME_PATH);
                }
            } else {
                servletSessionRequestContent.setRequestAttribute(
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
