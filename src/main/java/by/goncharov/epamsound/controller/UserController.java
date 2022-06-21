package by.goncharov.epamsound.controller;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.service.ServiceException;
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
public class UserController {
    /**
     * The Logger.
     */
    @Autowired
    private UserService userService;
    static final Logger LOGGER = LogManager.getLogger();

    @RequestMapping("/singup")
    public String SingUp(@RequestBody User user) {
        try {
            userService.singUp(user);
        } catch (ServiceException e) {
            LOGGER.error("Exception during sign up command", e);
        }
        return "login";
    }
    @RequestMapping("/login")
    public String LogIn(@RequestBody User user) {
        try {
            if (userService.checkLogin(user.getLogin(), user.getPassword())) {
                return "login";
            } else {
                return "singup";
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception during login command", e);
        }
        return "main";
    }
    @RequestMapping("/user/add_funds")
    public String AddCash(@RequestBody User user, @RequestParam int cash) {
        try {
            userService.addCash(user, String.valueOf(cash));
        } catch (ServiceException e) {
            LOGGER.error("Exception during add cash command", e);
        }
        return "main";
    }
    @RequestMapping("/user/change")
    public String ChangeData(@RequestBody User user, @RequestParam String login, @RequestParam String email) {
        try {
            userService.changeLogin(user, login);
            userService.changeEmail(user, email);
        } catch (ServiceException e) {
            LOGGER.error("Exception during change data command", e);
        }
        return "main";
    }
    @RequestMapping("/user/password")
    public String ChangePassword(@RequestBody User user, @RequestParam String password, @RequestParam String newPassword, @RequestParam String confPassword) {
        try {
            userService.changePass(user, password, newPassword, confPassword);
        } catch (ServiceException e) {
            LOGGER.error("Exception during change password command", e);
        }
        return "main";
    }
    //TODO: добавить проверку на права пользователя
    @RequestMapping("/admin/set_bonus")
    public String SetBonus(@RequestBody User user, @RequestParam int bonus) {
        try {
            userService.setBonus(user, String.valueOf(bonus));
        } catch (ServiceException e) {
            LOGGER.error("Exception during set bonus command", e);
        }
        return "main";
    }
    @RequestMapping("/admin/show_users")
    public String ShowUsers(Model model) {
        try {
            model.addAttribute("users", userService.findClients());
        } catch (ServiceException e) {
            LOGGER.error("Exception during show users command", e);
        }
        return "main";
    }
    @RequestMapping("/admin/search_user")
    public String SearchUser(@RequestBody User user, @RequestParam String login, Model model) {
        try {
            List<User> users = userService.findSuitableUsers(login);
            model.addAttribute("users", users);
        } catch (ServiceException e) {
            LOGGER.error("Exception during search user command", e);
        }
        return "main";
    }
    @RequestMapping("/logout")
    public String LogOut(Model model, @RequestParam String isLogin) {
            model.addAttribute(isLogin, null);
            return "main";
    }
}
