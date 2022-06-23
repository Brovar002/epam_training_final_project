package by.goncharov.epamsound.controller;

import by.goncharov.epamsound.beans.*;
import by.goncharov.epamsound.service.*;
import by.goncharov.epamsound.service.impl.CommentServiceImpl;
import by.goncharov.epamsound.service.impl.OrderServiceImpl;
import by.goncharov.epamsound.service.impl.TrackServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class TrackController {
    @Autowired
    private TrackServiceImpl trackService;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private CommentServiceImpl commentService;
    private FileDownloader fileDownloader;
    static final Logger LOGGER = LogManager.getLogger();

    @PostMapping("/admin/add_track")
    public String addTrack(@RequestBody Track track) throws ServiceException {
        trackService.save(track);
        return "main";
    }

    @PutMapping("/admin/recover")
    public String recoverTrack(@RequestBody Track track) throws ServiceException {
        track.setDeleted(false);
        trackService.save(track);
        return "main";
    }

    @PutMapping("/admin/edit")
    public String editTrack(@RequestBody Track track, @RequestParam String name,
                            @RequestParam String artist, @RequestParam Genre genre,
                            @RequestParam String price) throws ServiceException {
        track.setName(name);
        track.setArtist(artist);
        track.setGenre(genre);
        track.setPrice(Double.parseDouble(price));
        trackService.save(track);
        return "main";
    }
    @GetMapping("/tracks")
    public String allTracks() {
        try {
            trackService.findAllTracks();
        } catch (Exception e) {
            LOGGER.error("Exception during all tracks search", e);
        }
        return "tracks";
    }
    @GetMapping("/track_info")
    public String trackInfo(@RequestBody Track track) {
        try {
            trackService.findTrackById(track);
        } catch (Exception e) {
            LOGGER.error("Exception during track info search", e);
        }
        return "track_info";
    }
    @GetMapping("/user/my_orders")
    public String downloadTrack(@RequestBody Track track) {
        fileDownloader.downloadTrack(track.getPath());
        return "my_orders";
    }
    @PutMapping("/tracks")
    public String deleteTrack(@RequestBody Track track) throws ServiceException {
        track.setDeleted(true);
        trackService.save(track);
        return "tracks";
    }
    @GetMapping("main/search")
    public String searchTrack(@RequestParam String search, Model model) {
        try {
            List<Track> trackList = trackService.findTracksByName(search);
            model.addAttribute("track_list", trackList);
        } catch (Exception e) {
            LOGGER.error("Exception during all tracks search", e);
        }
        return "main";
    }
    @GetMapping("/main/search_genre")
    public String searchGenre(@RequestParam Genre genre, Model model) {
        try {
            List<Track> trackList = trackService.findTracksByGenre(genre);
            model.addAttribute("track_list", trackList);
        } catch (Exception e) {
            LOGGER.error("Exception during all tracks search", e);
        }
        return "main";
    }
    @GetMapping("tracks/buy")
    public String buyTrack(@RequestBody Track track, @RequestBody User user,
                           @RequestBody Order order, @RequestParam int price) throws ServiceException {
        order.setTrack(track);
        order.setCustomer(user);
        order.setPrice(price);
        orderService.save(order);

        return "main";
    }
    @PostMapping("/track/set_comment")
    public String setComment(@RequestBody Track track, @RequestBody User user,
                             @RequestBody Comment comment,
                             @RequestParam String text) {
        try {
            comment.setAudioTrackId(track);
            comment.setUserId(user.getId());
            comment.setText(text);
            commentService.save(comment);
        } catch (Exception e) {
            LOGGER.error("Exception during comment adding", e);
        }
        return "track_info";
    }
    @GetMapping("/track/get_comment")
    public String getComment(@RequestBody Track track, Model model) {
        try {
            List<Comment> commentList = commentService.findCommentsByTrack(track);
            model.addAttribute("comment_list", commentList);
        } catch (Exception e) {
            LOGGER.error("Exception during track comment", e);
        }
        return "track_info";
    }
   /* @GetMapping("/user/my_orders")
    public String UserOrders(@RequestBody User user, Model model) {
        try {
            List<Track> trackList = trackService.findMyTracks(user);
            model.addAttribute("order_list", trackList);
        } catch (Exception e) {
            LOGGER.error("Exception during user orders search", e);
        }
        return "my_orders";
    }*/
}
