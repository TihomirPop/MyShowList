package hr.tpopovic.myshowlist.application.port.in;

import java.util.List;

public sealed interface FetchUserShowsResult {

    record Success(List<UserShowDetails> shows) implements FetchUserShowsResult {

    }

    record UserNotFound() implements FetchUserShowsResult {

    }

    record Failure() implements FetchUserShowsResult {

    }

}
