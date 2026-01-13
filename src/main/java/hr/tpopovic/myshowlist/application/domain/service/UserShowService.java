package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.*;
import hr.tpopovic.myshowlist.application.port.out.*;

import java.util.List;
import java.util.stream.Collectors;

public class UserShowService implements UpsertUserShow, FetchUserShows {

    private final ForLoadingShows forLoadingShows;
    private final ForFetchingUser forFetchingUser;
    private final ForSavingUserShow forSavingUserShow;
    private final ForLoadingUserShows forLoadingUserShows;
    private final ForFetchingScore forFetchingScore;

    public UserShowService(
            ForLoadingShows forLoadingShows,
            ForFetchingUser forFetchingUser,
            ForSavingUserShow forSavingUserShow,
            ForLoadingUserShows forLoadingUserShows, ForFetchingScore forFetchingScore
    ) {
        this.forLoadingShows = forLoadingShows;
        this.forFetchingUser = forFetchingUser;
        this.forSavingUserShow = forSavingUserShow;
        this.forLoadingUserShows = forLoadingUserShows;
        this.forFetchingScore = forFetchingScore;
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
        if (show.tooManyEpisodesWatched(command.progress())) {
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
            case LoadUserShowsResult.Success(List<UserShow> userShows) -> handleUserShowsLoaded(userShows);
            case LoadUserShowsResult.Failure _ -> new FetchUserShowsResult.Failure();
        };
    }

    private FetchUserShowsResult handleUserShowsLoaded(List<UserShow> userShows) {
        return userShows.stream()
                .map(userShow -> new UserShowDetails(userShow, forFetchingScore.fetch(userShow.show().id())))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        FetchUserShowsResult.Success::new
                ));
    }

}
