package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.service.OrderService;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MyOrdersCommand implements Command {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_MY_ORDERS = "is_my_orders";
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        String logined = (String) sessionRequestContent
                .getSessionAttribute(IS_LOGIN);
        if (Boolean.parseBoolean(logined)) {
            List<Track> myTracks;
            User user = (User) sessionRequestContent
                    .getSessionAttribute(USER_ATTRIBUTE);
            OrderService orderService = new OrderService();
            try {
                myTracks = orderService.findMyTracks(user.getId());
                sessionRequestContent.setSessionAttribute(
                        TRACK_LIST_ATTR, myTracks);
                sessionRequestContent.setRequestAttribute(
                        IS_MY_ORDERS, true);
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.MY_ORDERS_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during my tracks search", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .HOME_PATH);
        }
        return page;
    }
}
