package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.command.admin.*;
import by.goncharov.epamsound.controller.command.common.*;
import by.goncharov.epamsound.controller.command.user.*;

/**
 * The enum Command type.
 * @author Goncharov Daniil
 * @version 1.0
 */
public enum CommandType {
    /**
     * The Main.
     */
    MAIN {
        {
            this.command = new MainMenuCommand();
        }
    },
    /**
     * The Show users.
     */
    SHOW_USERS {
        {
            this.command = new ShowUsersCommand();
        }
    },
    /**
     * The Add funds.
     */
    ADD_FUNDS {
        {
            this.command = new AddCashCommand();
        }
    },
    /**
     * The All.
     */
    ALL {
        {
            this.command = new AllTracksCommand();
        }
    },
    /**
     * The Add track.
     */
    ADD_TRACK {
        {
            this.command = new AddTrackCommand();
        }
    },
    /**
     * The Buy.
     */
    BUY {
        {
            this.command= new BuyTrackCommand();
        }
    },
    /**
     * The Change.
     */
    CHANGE {
        {
            this.command = new ChangeDataCommand();
        }
    },
    /**
     * The Change page.
     */
    CHANGE_PAGE {
        {
            this.command = new ChangePageCommand();
        }
    },
    /**
     * The Change pass.
     */
    CHANGE_PASS{
        {
            this.command = new ChangePasswordCommand();
        }
    },
    /**
     * The Download.
     */
    DOWNLOAD {
        {
            this.command= new DownloadCommand();
        }
    },
    /**
     * The Comment.
     */
    COMMENT {
        {
            this.command = new CommentCommand();
        }
    },
    /**
     * The Delete.
     */
    DELETE {
        {
            this.command = new DeleteTrackCommand();
        }
    },
    /**
     * The Deleted.
     */
    DELETED {
        {
            this.command = new ShowDeletedCommand();
        }
    },
    /**
     * The Genre.
     */
    GENRE {
        {
            this.command = new ShowGenreCommand();
        }
    },
    /**
     * The Edit.
     */
    EDIT {
        {
            this.command = new EditTrackCommand();
        }
    },
    /**
     * The Language.
     */
    LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },
    /**
     * The Login.
     */
    LOGIN {
        {
            this.command = new LogInCommand();
        }
    },
    /**
     * The Logout.
     */
    LOGOUT {
        {
            this.command = new LogOutCommand();
        }
    },
    /**
     * The My orders.
     */
    MY_ORDERS {
        {
            this.command = new MyOrdersCommand();
        }
    },
    /**
     * The Recover.
     */
    RECOVER {
        {
            this.command = new RecoverTrackCommand();
        }
    },
    /**
     * The Search.
     */
    SEARCH {
        {
            this.command = new SearchCommand();
        }
    },
    /**
     * The Search users.
     */
    SEARCH_USERS {
        {
            this.command = new SearchUsersCommand();
        }
    },
    /**
     * The Set bonus.
     */
    SET_BONUS {
        {
            this.command = new SetBonusCommand();
        }
    },
    /**
     * The Signup.
     */
    SIGNUP {
        {
            this.command = new SingUpCommand();
        }
    },
    /**
     * The Track info.
     */
    TRACK_INFO {
        {
            this.command = new TrackInfoCommand();
        }
    },
    /**
     * The Index.
     */
    INDEX{
        {
            this.command = new IndexCommand();
        }
    };

    /**
     * The Command.
     */
    Command command;

    /**
     * Gets current command.
     *
     * @return the current command
     */
    public Command getCurrentCommand() {
        return command;
    }
}
