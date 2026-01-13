package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.*;
import hr.tpopovic.myshowlist.application.port.out.*;

import java.util.List;

public class UserShowService implements UpsertUserShow, FetchUserShows {

    private final ForLoadingShows forLoadingShows;
    private final ForFetchingUser forFetchingUser;
    private final ForSavingUserShow forSavingUserShow;
    private final ForLoadingUserShows forLoadingUserShows;

    public UserShowService(
            ForLoadingShows forLoadingShows,
            ForFetchingUser forFetchingUser,
            ForSavingUserShow forSavingUserShow
    ) {
        this.forLoadingShows = forLoadingShows;
        this.forFetchingUser = forFetchingUser;
        this.forSavingUserShow = forSavingUserShow;
        this.forLoadingUserShows = null;
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

    @Override
    public FetchUserShowsResult fetch(Username username) {
        FetchUserIdResult userResult = forFetchingUser.fetch(username);

        return switch (userResult) {
            case FetchUserIdResult.Success(UserId userId) -> handleUserFound(userId);
            case FetchUserIdResult.UserNotFound _ -> new FetchUserShowsResult.UserNotFound();
            case FetchUserIdResult.Failure _ -> new FetchUserShowsResult.Failure();
        };
    }

    private FetchUserShowsResult handleUserFound(UserId userId) {
        LoadUserShowsResult result = forLoadingUserShows.load(userId);
        return switch (result) {
            case LoadUserShowsResult.Success(List<UserShow> userShows) -> new FetchUserShowsResult.Success(userShows);
            case LoadUserShowsResult.UserNotFound _ -> new FetchUserShowsResult.UserNotFound();
            case LoadUserShowsResult.Failure _ -> new FetchUserShowsResult.Failure();
        };
    }

}
