package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND = "command";
    public AbstractCommand defineCommand(final SessionRequestContent
                                                 servletSessionRequestContent) {
        AbstractCommand current = new EmptyCommand();

        String command = servletSessionRequestContent
                .getRequestParameter(COMMAND);
        if (command != null && !command.isEmpty()) {
            try {
                CommandType currentCommand = CommandType.valueOf(
                        command.toUpperCase());
                current = currentCommand.getCurrentCommand();
            } catch (IllegalArgumentException e) {
                LOGGER.error("Exception during command creator", e);
            }
        }
        return current;
    }
}
