package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.UserId;
import hr.tpopovic.myshowlist.application.port.in.AddUserShow;
import hr.tpopovic.myshowlist.application.port.in.AddUserShowCommand;
import hr.tpopovic.myshowlist.application.port.in.AddUserShowResult;
import hr.tpopovic.myshowlist.application.port.out.*;

public class UserShowService implements AddUserShow {

    private final ForFetchingUser forFetchingUser;
    private final ForSavingUserShow forSavingUserShow;

    public UserShowService(ForFetchingUser forFetchingUser, ForSavingUserShow forSavingUserShow) {
        this.forFetchingUser = forFetchingUser;
        this.forSavingUserShow = forSavingUserShow;
    }

    @Override
    public AddUserShowResult add(AddUserShowCommand command) {
        FetchUserIdResult fetchResult = forFetchingUser.fetch(command.username());

        return switch (fetchResult) {
            case FetchUserIdResult.Success(UserId userId) -> getAddUserShowResult(userId, command);
            case FetchUserIdResult.UserNotFound _ -> new AddUserShowResult.UserNotFound();
            case FetchUserIdResult.Failure _ -> new AddUserShowResult.Failure();
        };
    }

    private AddUserShowResult getAddUserShowResult(UserId userId, AddUserShowCommand command) {
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
