package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.OrderService;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for execute command of buying track.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see User
 * @see OrderService
 * @see SessionRequestContent
 */
public class BuyTrackCommand implements Command {
    /**
     * The Logger.
     */
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_ID_ATTR = "track_id";
    private static final String PRICE_ATTR = "price";

    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        String page;
        String logined = (String) sessionRequestContent
                .getSessionAttribute(IS_LOGIN);
        if (Boolean.parseBoolean(logined)) {
            User user = (User) sessionRequestContent
                    .getSessionAttribute(USER_ATTRIBUTE);
            Track track = (Track) sessionRequestContent
                    .getSessionAttribute(TRACK_ATTRIBUTE);
            double price = Double.parseDouble(sessionRequestContent
                    .getRequestParameter(PRICE_ATTR));
            price -= price * user.getDiscount() / 100;
            OrderService orderService = new OrderService();
            try {
                if (!orderService.isOrdered (user.getId(), track.getId())) {
                    if (user.getCash() - price >= 0) {
                        orderService.addOrder(track, price, user);
                        user.setCash(user.getCash() - price);
                        sessionRequestContent.setSessionAttribute(
                                USER_ATTRIBUTE, user);
                        sessionRequestContent.setSessionAttribute(
                                TRACK_ATTRIBUTE, track);
                        sessionRequestContent.setRequestAttribute(
                                SUCCESS, messageManager.getProperty(
                                        MessageManager.ORDER_SUCCESS));
                        page = ConfigurationManager.getProperty(
                                ConfigurationManager.SHOW_MY_ORDERS_PATH);

                    } else {
                        sessionRequestContent.setRequestAttribute(
                                ERROR, messageManager.getProperty(
                                        MessageManager.ORDER_ERROR));
                        page = ConfigurationManager
                                .getProperty(sessionRequestContent
                                        .getSessionAttribute(CUR_PAGE_ATTR)
                                        .toString());
                    }
                } else {
                    sessionRequestContent.setRequestAttribute(
                            SUCCESS, messageManager.getProperty(
                                    MessageManager.ODER_DOWNLOAD));
                    page = ConfigurationManager.getProperty(
                            ConfigurationManager.SHOW_MY_ORDERS_PATH);
                }
            } catch (ServiceException e) {
                LOGGER.error("Exception during track purchase");
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
