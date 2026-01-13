package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.UserShow;

import java.util.List;

public sealed interface LoadUserShowsResult {

    record Success(List<UserShow> userShows) implements LoadUserShowsResult {

    }

    record UserNotFound() implements LoadUserShowsResult {

    }

    record Failure() implements LoadUserShowsResult {

    }
}
