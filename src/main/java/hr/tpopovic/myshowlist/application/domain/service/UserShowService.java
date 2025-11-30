package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.UpsertUserShow;
import hr.tpopovic.myshowlist.application.port.in.UpsertUserShowCommand;
import hr.tpopovic.myshowlist.application.port.in.UpsertUserShowResult;
import hr.tpopovic.myshowlist.application.port.out.*;

public class UserShowService implements UpsertUserShow {

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
    public UpsertUserShowResult upsert(UpsertUserShowCommand command) {
        LoadShowResult showResult = forLoadingShows.load(command.showId());

        return switch (showResult) {
            case LoadShowResult.Success(Show show) -> handleShowLoaded(show, command);
            case LoadShowResult.NotFound _ -> new UpsertUserShowResult.ShowNotFound();
            case LoadShowResult.Failure _ -> new UpsertUserShowResult.Failure();
        };
    }

    private UpsertUserShowResult handleShowLoaded(Show show, UpsertUserShowCommand command) {
        if(show.tooManyEpisodesWatched(command.progress())) {
            return new UpsertUserShowResult.InvalidInput();
        }

        FetchUserIdResult userResult = forFetchingUser.fetch(command.username());

        return switch (userResult) {
            case FetchUserIdResult.Success(UserId userId) -> handleUserFound(userId, command);
            case FetchUserIdResult.UserNotFound _ -> new UpsertUserShowResult.UserNotFound();
            case FetchUserIdResult.Failure _ -> new UpsertUserShowResult.Failure();
        };
    }

    private UpsertUserShowResult handleUserFound(UserId userId, UpsertUserShowCommand command) {
        var saveCommand = new SaveUserShowCommand(
                userId,
                command.showId(),
                command.progress(),
                command.status(),
                command.score()
        );

        SaveUserShowResult saveResult = forSavingUserShow.save(saveCommand);

        return switch (saveResult) {
            case SaveUserShowResult.Success _ -> new UpsertUserShowResult.Success();
            case SaveUserShowResult.ShowNotFound _ -> new UpsertUserShowResult.ShowNotFound();
            case SaveUserShowResult.Failure _ -> new UpsertUserShowResult.Failure();
        };
    }

}
