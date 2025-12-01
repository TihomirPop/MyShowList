package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.UserShow;

import java.util.List;

public sealed interface FetchUserShowsResult {

    record Success(List<UserShow> shows) implements FetchUserShowsResult {

    }

    record UserNotFound() implements FetchUserShowsResult {

    }

    record Failure() implements FetchUserShowsResult {

    }

}
