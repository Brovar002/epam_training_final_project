package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.manager.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.servlet.ServletSessionRequestContent;

public class DownloadErrorCommand extends AbstractCommand {
    @Override
    public String execute(final ServletSessionRequestContent
                                      servletSessionRequestContent) {
        servletSessionRequestContent.setRequestAttribute(ERROR,
                messageManager.getProperty(MessageManager.DOWNLOAD_ERROR));
        return ConfigurationManager.getProperty(ConfigurationManager
                .ERROR_PATH);
    }
}

