package by.goncharov.epamsound.controller.command;

import by.goncharov.epamsound.controller.command.admin.*;
import by.goncharov.epamsound.controller.command.common.*;
import by.goncharov.epamsound.controller.command.user.*;

public enum CommandType {
    MAIN {
        {
            this.command = new MainMenuCommand();
        }
    },
    SHOW_USERS {
        {
            this.command = new ShowUsersCommand();
        }
    },
    ADD_FUNDS {
        {
            this.command = new AddCashCommand();
        }
    },
    ALL {
        {
            this.command = new AllTracksCommand();
        }
    },
    ADD_TRACK {
        {
            this.command = new AddTrackCommand();
        }
    },
    BUY {
        {
            this.command= new BuyTrackCommand();
        }
    },
    CHANGE {
        {
            this.command = new ChangeDataCommand();
        }
    },
    CHANGE_PAGE {
        {
            this.command = new ChangePageCommand();
        }
    },
    CHANGE_PASS{
        {
            this.command = new ChangePasswordCommand();
        }
    },
    DOWNLOAD {
        {
            this.command= new DownloadCommand();
        }
    },
    COMMENT {
        {
            this.command = new CommentCommand();
        }
    },
    DELETE {
        {
            this.command = new DeleteTrackCommand();
        }
    },
    DELETED {
        {
            this.command = new ShowDeletedCommand();
        }
    },
    GENRE {
        {
            this.command = new ShowGenreCommand();
        }
    },
    EDIT {
        {
            this.command = new EditTrackCommand();
        }
    },
    LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },
    LOGIN {
        {
            this.command = new LogInCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogOutCommand();
        }
    },
    MY_ORDERS {
        {
            this.command = new MyOrdersCommand();
        }
    },
    RECOVER {
        {
            this.command = new RecoverTrackCommand();
        }
    },
    SEARCH {
        {
            this.command = new SearchCommand();
        }
    },
    SEARCH_USERS {
        {
            this.command = new SearchUsersCommand();
        }
    },
    SET_BONUS {
        {
            this.command = new SetBonusCommand();
        }
    },
    SIGNUP {
        {
            this.command = new SingUpCommand();
        }
    },
    TRACK_INFO {
        {
            this.command = new TrackInfoCommand();
        }
    },
    INDEX{
        {
            this.command = new IndexCommand();
        }
    };

    Command command;
    public Command getCurrentCommand() {
        return command;
    }
}
