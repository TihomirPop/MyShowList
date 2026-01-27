package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.adapter.out.show.ShowEntityMapper;
import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingUserShows;
import hr.tpopovic.myshowlist.application.port.out.LoadUserShowsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.Objects.isNull;

public class UserShowLoader implements ForLoadingUserShows {

    private static final Logger log = LoggerFactory.getLogger(UserShowLoader.class);

    private final UserShowRepository userShowRepository;

    public UserShowLoader(UserShowRepository userShowRepository) {
        this.userShowRepository = userShowRepository;
    }

    @Override
    public LoadUserShowsResult load(UserId userId) {
        try {
            List<UserShow> userShows = userShowRepository.findByUserId(userId.id())
                    .stream()
                    .map(entity -> new UserShow(
                            ShowEntityMapper.toDomain(entity.getShow()),
                            new Progress(entity.getProgress()),
                            mapStatus(entity.getStatus()),
                            mapScore(entity.getScore())
                    ))
                    .toList();
            return new LoadUserShowsResult.Success(userShows);
        } catch (Exception e) {
            log.error("Error loading shows for user ID: {}", userId.id(), e);
            return new LoadUserShowsResult.Failure();
        }
    }

    private Status mapStatus(Short status) {
        return switch (status) {
            case 1 -> Status.WATCHING;
            case 2 -> Status.COMPLETED;
            case 3 -> Status.ON_HOLD;
            case 4 -> Status.DROPPED;
            case 5 -> Status.PLAN_TO_WATCH;
            default -> throw new IllegalArgumentException("Unknown status: " + status);
        };
    }

    private Score mapScore(Short score) {
        return isNull(score) || score == 0
                ? new Score.NotRated()
                : new Score.Rated(score);
    }

}
