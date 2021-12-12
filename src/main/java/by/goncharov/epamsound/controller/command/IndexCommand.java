package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.service.GenreService;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class IndexCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_LOCALE = "ru_RU";
    private static final String GENRES_ATTR = "genres";

    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        GenreService genreService = new GenreService();
        List<String> genreList = new ArrayList<>();
        try {
            genreList = genreService.findGenres();
        } catch (ServiceException e) {
            LOGGER.error("Exception during genres search", e);
        }
        servletSessionRequestContent.setSessionAttribute(GENRES_ATTR,
                genreList);
        servletSessionRequestContent.setSessionAttribute(LOCALE_ATTRIBUTE,
                DEFAULT_LOCALE);
        return ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
    }
}
