package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.controller.SessionRequestContent;

public class DownloadErrorCommand extends AbstractCommand {
    @Override
    public String execute(final SessionRequestContent
                                      servletSessionRequestContent) {
        servletSessionRequestContent.setRequestAttribute(ERROR,
                messageManager.getProperty(MessageManager.DOWNLOAD_ERROR));
        return ConfigurationManager.getProperty(ConfigurationManager
                .ERROR_PATH);
    }
}

