package by.goncharov.epamsound.controller;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.GenreService;
import by.goncharov.epamsound.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private TrackService trackService;
    @Autowired
    private GenreService genreService;
    private MessageManager messageManager;

    @RequestMapping("/main")
    public String main(Model model) {
        try {
            List<Track> trackList = trackService.lastTracks();
            model.addAttribute("track_list", trackList);
        } catch (Exception e) {
            LOGGER.error("Exception during all tracks search", e);
        }
        return "main";
    }
    @RequestMapping("main/change_language")
    public String changeLanguage(@RequestParam String locale) {
        try {
            messageManager.setCurrentLocale(locale);
        } catch (Exception e) {
            LOGGER.error("Exception during change language command", e);
        }
        return "main";
    }
    //TODO: добавить смену страниц

    @RequestMapping("/")
    public String index() {
        try {
            new ArrayList<>();
            genreService.findGenres();
        } catch (Exception e) {
            LOGGER.error("Exception during all genres search", e);
        }
        return "main";
    }

}
