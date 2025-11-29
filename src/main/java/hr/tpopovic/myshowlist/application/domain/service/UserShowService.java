package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.port.in.AddUserShow;
import hr.tpopovic.myshowlist.application.port.in.AddUserShowCommand;
import hr.tpopovic.myshowlist.application.port.in.AddUserShowResult;
import hr.tpopovic.myshowlist.application.port.out.ForSavingUserShow;
import hr.tpopovic.myshowlist.application.port.out.SaveUserShowCommand;
import hr.tpopovic.myshowlist.application.port.out.SaveUserShowResult;

public class UserShowService implements AddUserShow {

    private final ForSavingUserShow forSavingUserShow;

    public UserShowService(ForSavingUserShow forSavingUserShow) {
        this.forSavingUserShow = forSavingUserShow;
    }

    @Override
    public AddUserShowResult add(AddUserShowCommand command) {
        var saveCommand = new SaveUserShowCommand(
                command.username(),
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
