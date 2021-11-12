package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.manager.ConfigurationManager;
import by.goncharov.epamsound.service.OrderService;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MyOrdersCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_MY_ORDERS = "is_my_orders";
    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        String logined = (String) servletSessionRequestContent
                .getSessionAttribute(IS_LOGIN);
        if (Boolean.parseBoolean(logined)) {
            List<Track> myTracks;
            User user = (User) servletSessionRequestContent
                    .getSessionAttribute(USER_ATTRIBUTE);
            OrderService orderService = new OrderService();
            try {
                myTracks = orderService.findMyTracks(user.getId());
                servletSessionRequestContent.setSessionAttribute(
                        TRACK_LIST_ATTR, myTracks);
                servletSessionRequestContent.setRequestAttribute(
                        IS_MY_ORDERS, true);
                page = ConfigurationManager.getProperty(
                        ConfigurationManager.MY_ORDERS_PATH);
            } catch (ServiceException e) {
                LOGGER.error("Exception during my tracks search", e);
                page = redirectToErrorPage(servletSessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager
                    .HOME_PATH);
        }
        return page;
    }
}
