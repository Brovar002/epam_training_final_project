package by.goncharov.epamsound.controller.command.admin;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class EditTrackCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_ATTR = "track";
    private static final String COMMENTS_ATTRIBUTE = "comments";
    private static final String NAME_PARAM = "name";
    private static final String ARTIST_PARAM = "artist";
    private static final String GENRE_PARAM = "genre";
    private static final String PRICE_PARAM = "price";
    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        User user = (User) servletSessionRequestContent
                .getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            Track track = (Track) servletSessionRequestContent
                    .getSessionAttribute(TRACK_ATTR);
            String name = servletSessionRequestContent
                    .getRequestParameter(NAME_PARAM);
            String artist = servletSessionRequestContent
                    .getRequestParameter(ARTIST_PARAM);
            String price = servletSessionRequestContent
                    .getRequestParameter(PRICE_PARAM);
            String genre = servletSessionRequestContent
                    .getRequestParameter(GENRE_PARAM);
            TrackService trackService = new TrackService();
            String res;
            try {
                if (!name.equals(track.getName())) {
                    res = trackService.changeName(track.getId(), name);
                    if (SUCCESS.equals(res)) {
                        track.setName(name);
                    } else {
                        servletSessionRequestContent.setRequestAttribute(
                                ERROR, res);
                        return ConfigurationManager.getProperty(
                                ConfigurationManager.TRACK_EDIT_PATH);
                    }
                }
                if (!artist.equals(track.getArtist())) {
                    res = trackService.changeArtist(track.getId(), artist);
                    if (SUCCESS.equals(res)) {
                        track.setArtist(artist);
                    } else {
                        servletSessionRequestContent.setRequestAttribute(
                                ERROR, res);
                        return ConfigurationManager.getProperty(
                                ConfigurationManager.TRACK_EDIT_PATH);
                    }
                }
                if (!genre.equals(track.getGenre())) {
                    res = trackService.changeGenre(track.getId(), genre);
                    if (SUCCESS.equals(res)) {
                        track.setGenre(genre.toUpperCase());
                    } else {
                        servletSessionRequestContent.setRequestAttribute(
                                ERROR, res);
                        return ConfigurationManager.getProperty(
                                ConfigurationManager.TRACK_EDIT_PATH);
                    }
                }
                res = trackService.changePrice(track.getId(),
                        track.getPrice(), price);
                if (SUCCESS.equals(res)) {
                    track.setPrice(Double.parseDouble(price));
                } else {
                    servletSessionRequestContent.setRequestAttribute(
                            ERROR, res);
                    return ConfigurationManager.getProperty(
                            ConfigurationManager.TRACK_EDIT_PATH);
                }
                List<Comment> comments = trackService.findTrackComments(
                        track.getId());
                servletSessionRequestContent.setSessionAttribute(
                        COMMENTS_ATTRIBUTE, comments);
                servletSessionRequestContent.setSessionAttribute(
                        TRACK_ATTR, track);
                servletSessionRequestContent.setRequestAttribute(
                        SUCCESS, messageManager.getProperty(
                                MessageManager.CHANGE_SUCCESS));
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.TRACK_INFO_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during change command", e);
                page = redirectToErrorPage(servletSessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        }

        return page;
    }
}
