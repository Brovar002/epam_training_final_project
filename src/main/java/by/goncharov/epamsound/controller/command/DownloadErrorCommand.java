package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.controller.SessionRequestContent;

public class DownloadErrorCommand implements Command {
    @Override
    public String execute(final SessionRequestContent
                                      sessionRequestContent) {
        sessionRequestContent.setRequestAttribute(ERROR,
                messageManager.getProperty(MessageManager.DOWNLOAD_ERROR));
        return ConfigurationManager.getProperty(ConfigurationManager
                .ERROR_PATH);
    }
}

