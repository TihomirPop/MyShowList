package hr.tpopovic.myshowlist.application.port.in;

public sealed interface UpsertUserShowResult {

    record Success() implements UpsertUserShowResult {

    }

    record UserNotFound() implements UpsertUserShowResult {

    }

    record ShowNotFound() implements UpsertUserShowResult {

    }

    record InvalidInput() implements UpsertUserShowResult {

    }

    record Failure() implements UpsertUserShowResult {

    }

}
