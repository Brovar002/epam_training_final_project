package by.goncharov.epamsound.controller;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.impl.UserServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    /**
     * The Logger.
     */
    @Autowired
    private UserServiceImpl userService;
    static final Logger LOGGER = LogManager.getLogger();

    @PostMapping("/singup")
    public String SingUp(@RequestBody User user) {
        try {
            userService.save(user);
        } catch (ServiceException e) {
            LOGGER.error("Exception during sign up command", e);
        }
        return "singup";
    }
    @PutMapping("/user/add_funds")
    public String AddCash(@RequestBody User user, @RequestParam int cash)
            throws ServiceException {
        user.setCash(user.getCash() + cash);
        userService.save(user);
        return "main";
    }
    @PutMapping("/user/change")
    public String ChangeData(@RequestBody User user, @RequestParam String login,
                             @RequestParam String email) throws ServiceException {
        user.setLogin(login);
        user.setEmail(email);
        userService.save(user);
        return "main";
    }
    @PutMapping("/user/password")
    public String ChangePassword(@RequestBody User user,
                                 @RequestParam String newPassword)
            throws ServiceException {
        user.setPassword(DigestUtils.md5Hex(newPassword));
        userService.save(user);
        return "main";
    }
    @PutMapping("/admin/set_bonus")
    public String SetBonus(@RequestBody User user, @RequestParam int bonus) throws ServiceException {
        user.setDiscount(bonus);
        userService.save(user);
        return "main";
    }
    @GetMapping("/admin/show_users")
    public String ShowUsers(Model model) {
        try {
            model.addAttribute("users", userService.findClients());
        } catch (ServiceException e) {
            LOGGER.error("Exception during show users command", e);
        }
        return "main";
    }
    @GetMapping("/admin/search_user")
    public String SearchUser(@PathVariable String login, Model model) {
        try {
            List<User> users = userService.findUserByLogin(login);
            model.addAttribute("users", users);
        } catch (ServiceException e) {
            LOGGER.error("Exception during search user command", e);
        }
        return "main";
    }
}
