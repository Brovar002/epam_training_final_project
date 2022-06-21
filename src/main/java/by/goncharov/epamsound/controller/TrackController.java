package by.goncharov.epamsound.controller;

import by.goncharov.epamsound.beans.*;
import by.goncharov.epamsound.service.OrderService;
import by.goncharov.epamsound.service.TrackService;
import by.goncharov.epamsound.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TrackController {
    @Autowired
    private TrackService trackService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    static final Logger LOGGER = LogManager.getLogger();

    @RequestMapping("/admin/add_track")
    public String AddTrack(@RequestBody Track track) {
        try {
            trackService.addTrack(track);
        } catch (Exception e) {
            LOGGER.error("Exception during add track command", e);
        }
        return "main";
    }

    @RequestMapping("/admin/recover")
    public String RecoverTrack(@RequestBody Track track) {
        try {
            trackService.recoverTrackById(track);
        } catch (Exception e) {
            LOGGER.error("Exception during recover track command", e);
        }
        return "main";
    }

    @RequestMapping("/admin/edit")
    public String EditTrack(@RequestBody Track track, @RequestParam String name, @RequestParam String artist, @RequestParam Genre genre, @RequestParam String price) {
        try {
            trackService.changeArtist(track, artist);
            trackService.changeName(track, name);
            trackService.changeGenre(track, genre);
            trackService.changePrice(track, price);
        } catch (Exception e) {
            LOGGER.error("Exception during edit track command", e);
        }
        return "main";
    }
    @RequestMapping("/tracks")
    public String AllTracks() {
        try {
            trackService.findAllTracks();
        } catch (Exception e) {
            LOGGER.error("Exception during all tracks search", e);
        }
        return "tracks";
    }
    @RequestMapping("/track_info")
    public String TrackInfo(@RequestBody Track track) {
        try {
            trackService.findTrackById(track);
        } catch (Exception e) {
            LOGGER.error("Exception during track info search", e);
        }
        return "track_info";
    }
    @RequestMapping("/user/my_orders")
    public String DownloadTrack(@RequestBody Track track) {
        try {
            trackService.findTrackPath(track);
        } catch (Exception e) {
            LOGGER.error("Exception during track path search", e);
        }
        return "my_orders";
    }
    //TODO: добавить проверку на права пользователя
    @RequestMapping("/tracks")
    public String DeleteTrack(@RequestBody Track track) {
        try {
            trackService.deleteTrackById(track);
        } catch (Exception e) {
            LOGGER.error("Exception during track delete", e);
        }
        return "tracks";
    }
    @RequestMapping("/admin/recover")
    public String DeletedTracks() {
        try {
            trackService.findDeletedTracks();
        } catch (Exception e) {
            LOGGER.error("Exception during deleted tracks search", e);
        }
        return "recover";
    }
    @RequestMapping("main/search")
    public String SearchTrack(@RequestParam String search, Model model) {
        try {
            List<Track> trackList = trackService.findSuitableTracks(search);
            model.addAttribute("track_list", trackList);
        } catch (Exception e) {
            LOGGER.error("Exception during all tracks search", e);
        }
        return "main";
    }
    @RequestMapping("/main/search_genre")
    public String SearchGenre(@RequestParam Genre genre, Model model) {
        try {
            List<Track> trackList = trackService.findTracksByGenre(genre);
            model.addAttribute("track_list", trackList);
        } catch (Exception e) {
            LOGGER.error("Exception during all tracks search", e);
        }
        return "main";
    }
    @RequestMapping("tracks/buy")
    public String BuyTrack(@RequestBody Track track, @RequestBody User user, @RequestParam int price) {
        try {
            if (!orderService.isOrdered (user, track)) {
                if (user.getCash() - price >= 0) {
                    orderService.addOrder(track, price, user);
                    user.setCash(user.getCash() - price);
                } else {
                    return "main";
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception during track buy", e);
        }
        return "main";
    }
    @RequestMapping("/track/set_comment")
    public String SetComment(@RequestBody Track track, @RequestBody User user, @RequestParam String comment) {
        try {
            userService.addComment(user, comment, track);
        } catch (Exception e) {
            LOGGER.error("Exception during track comment", e);
        }
        return "track_info";
    }
    @RequestMapping("/track/get_comment")
    public String GetComment(@RequestBody Track track, Model model) {
        try {
            List<Comment> commentList = trackService.findTrackComments(track);
            model.addAttribute("comment_list", commentList);
        } catch (Exception e) {
            LOGGER.error("Exception during track comment", e);
        }
        return "track_info";
    }
    @RequestMapping("/user/my_orders")
    public String UserOrders(@RequestBody User user, Model model) {
        try {
            List<Track> orderList = orderService.findMyTracks(user);
            model.addAttribute("order_list", orderList);
        } catch (Exception e) {
            LOGGER.error("Exception during user orders search", e);
        }
        return "my_orders";
    }
}
