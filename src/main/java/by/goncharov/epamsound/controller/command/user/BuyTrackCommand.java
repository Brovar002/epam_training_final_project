package by.goncharov.epamsound.controller.command.user;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.controller.command.AbstractCommand;
import by.goncharov.epamsound.manager.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.service.OrderService;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuyTrackCommand extends AbstractCommand {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String TRACK_ID_ATTR = "track_id";
    private static final String PRICE_ATTR = "price";

    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        String page;
        String logined = (String) servletSessionRequestContent
                .getSessionAttribute(IS_LOGIN);
        if (Boolean.parseBoolean(logined)) {
            User user = (User) servletSessionRequestContent
                    .getSessionAttribute(USER_ATTRIBUTE);
            int trackId = Integer.parseInt(servletSessionRequestContent
                    .getRequestParameter(TRACK_ID_ATTR));
            double price = Double.parseDouble(servletSessionRequestContent
                    .getRequestParameter(PRICE_ATTR));
            price -= price * user.getDiscount() / 100;
            OrderService orderService = new OrderService();
            try {
                if (!orderService.isOrdered(user.getId(), trackId)) {
                    if (user.getCash() - price >= 0) {
                        orderService.addOrder(trackId, price, user);
                        user.setCash(user.getCash() - price);
                        servletSessionRequestContent.setSessionAttribute(
                                USER_ATTRIBUTE, user);
                        servletSessionRequestContent.setRequestAttribute(
                                SUCCESS, messageManager.getProperty(
                                        MessageManager.ORDER_SUCCESS));
                        page = ConfigurationManager.getProperty(
                                ConfigurationManager.SHOW_MY_ORDERS_PATH);

                    } else {
                        servletSessionRequestContent.setRequestAttribute(
                                ERROR, messageManager.getProperty(
                                        MessageManager.ORDER_ERROR));
                        page = ConfigurationManager
                                .getProperty(servletSessionRequestContent
                                        .getSessionAttribute(CUR_PAGE_ATTR)
                                        .toString());
                    }
                } else {
                    servletSessionRequestContent.setRequestAttribute(
                            SUCCESS, messageManager.getProperty(
                                    MessageManager.ODER_DOWNLOAD));
                    page = ConfigurationManager.getProperty(
                            ConfigurationManager.SHOW_MY_ORDERS_PATH);
                }
            } catch (ServiceException e) {
                LOGGER.error("Exception during track purchase");
                page = redirectToErrorPage(servletSessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(
                    ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}
