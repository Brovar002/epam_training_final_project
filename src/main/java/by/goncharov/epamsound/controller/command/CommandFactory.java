package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Command factory.
 * @author Goncharov Daniil
 * @version 1.0
 * @see SessionRequestContent
 */
public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND = "command";

    /**
     * Define command command.
     *
     * @param sessionRequestContent the session request content
     * @return the command
     */
    public Command defineCommand(final SessionRequestContent
                                                 sessionRequestContent) {
        Command current = new EmptyCommand();

        String command = sessionRequestContent
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
