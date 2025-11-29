package hr.tpopovic.myshowlist.application.port.in;

public sealed interface AddUserShowResult {

    record Success() implements AddUserShowResult {

    }

    record ShowNotFound() implements AddUserShowResult {

    }

    record DuplicateEntry() implements AddUserShowResult {

    }

    record Failure() implements AddUserShowResult {

    }

}
