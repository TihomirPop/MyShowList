package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.Score;
import hr.tpopovic.myshowlist.application.domain.model.Status;
import hr.tpopovic.myshowlist.application.port.out.ForSavingUserShow;
import hr.tpopovic.myshowlist.application.port.out.SaveUserShowCommand;
import hr.tpopovic.myshowlist.application.port.out.SaveUserShowResult;
import org.springframework.dao.DataIntegrityViolationException;

public class UserShowSaver implements ForSavingUserShow {

    private final UserShowRepository userShowRepository;

    public UserShowSaver(UserShowRepository userShowRepository) {
        this.userShowRepository = userShowRepository;
    }

    @Override
    public SaveUserShowResult save(SaveUserShowCommand command) {
        UserShowId userShowId = new UserShowId();
        userShowId.setUserId(command.userId().id());
        userShowId.setShowId(command.showId().id());
        UserShowEntity userShowEntity = new UserShowEntity();
        userShowEntity.setId(userShowId);
        userShowEntity.setProgress(command.progress().value());
        userShowEntity.setStatus(mapStatus(command.status()));
        userShowEntity.setScore(mapScore(command.score()));

        try {
            userShowRepository.save(userShowEntity);
            return new SaveUserShowResult.Success();
        } catch (DataIntegrityViolationException e) {
            return parseException(e);
        } catch (Exception e) {
            return new SaveUserShowResult.Failure();
        }
    }

    private Short mapStatus(Status status) {
        return switch (status) {
            case WATCHING -> 1;
            case COMPLETED -> 2;
            case ON_HOLD -> 3;
            case DROPPED -> 4;
            case PLAN_TO_WATCH -> 5;
        };
    }

    private Short mapScore(Score score) {
        return switch (score) {
            case Score.Rated(Short value) -> value;
            case Score.NotRated _ -> 0;
        };
    }

    private static SaveUserShowResult parseException(DataIntegrityViolationException e) {
        if (e.getMessage().contains("foreign key constraint")) {
            return new SaveUserShowResult.ShowNotFound();
        }

        if (e.getMessage().contains("unique constraint")) {
            return new SaveUserShowResult.DuplicateEntry();
        }

        return new SaveUserShowResult.Failure();
    }

}
