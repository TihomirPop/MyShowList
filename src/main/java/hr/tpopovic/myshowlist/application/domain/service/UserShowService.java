package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.AddUserShow;
import hr.tpopovic.myshowlist.application.port.in.AddUserShowCommand;
import hr.tpopovic.myshowlist.application.port.in.AddUserShowResult;
import hr.tpopovic.myshowlist.application.port.out.*;

public class UserShowService implements AddUserShow {

    private final ForLoadingShows forLoadingShows;
    private final ForFetchingUser forFetchingUser;
    private final ForSavingUserShow forSavingUserShow;

    public UserShowService(
            ForLoadingShows forLoadingShows,
            ForFetchingUser forFetchingUser,
            ForSavingUserShow forSavingUserShow
    ) {
        this.forLoadingShows = forLoadingShows;
        this.forFetchingUser = forFetchingUser;
        this.forSavingUserShow = forSavingUserShow;
    }

    @Override
    public AddUserShowResult add(AddUserShowCommand command) {
        LoadShowResult showResult = forLoadingShows.load(command.showId());

        return switch (showResult) {
            case LoadShowResult.Success(Show show) -> handleShowLoaded(show, command);
            case LoadShowResult.NotFound _ -> new AddUserShowResult.ShowNotFound();
            case LoadShowResult.Failure _ -> new AddUserShowResult.Failure();
        };
    }

    private AddUserShowResult handleShowLoaded(Show show, AddUserShowCommand command) {
        if(show.tooManyEpisodesWatched(command.progress())) {
            return new AddUserShowResult.InvalidInput();
        }

        FetchUserIdResult userResult = forFetchingUser.fetch(command.username());

        return switch (userResult) {
            case FetchUserIdResult.Success(UserId userId) -> handleUserFound(userId, command);
            case FetchUserIdResult.UserNotFound _ -> new AddUserShowResult.UserNotFound();
            case FetchUserIdResult.Failure _ -> new AddUserShowResult.Failure();
        };
    }

    private AddUserShowResult handleUserFound(UserId userId, AddUserShowCommand command) {
        var saveCommand = new SaveUserShowCommand(
                userId,
                command.showId(),
                command.progress(),
                command.status(),
                command.score()
        );

        SaveUserShowResult saveResult = forSavingUserShow.save(saveCommand);

        return switch (saveResult) {
            case SaveUserShowResult.Success _ -> new AddUserShowResult.Success();
            case SaveUserShowResult.ShowNotFound _ -> new AddUserShowResult.ShowNotFound();
            case SaveUserShowResult.DuplicateEntry _ -> new AddUserShowResult.DuplicateEntry();
            case SaveUserShowResult.Failure _ -> new AddUserShowResult.Failure();
        };
    }

}
