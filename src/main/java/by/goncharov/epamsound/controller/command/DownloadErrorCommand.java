package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.ConfigurationManager;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.controller.SessionRequestContent;

/**
 * Class for execute command of downloading error.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Command
 * @see ConfigurationManager
 * @see SessionRequestContent
 */
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

