package hr.tpopovic.myshowlist.application.port.out;

public sealed interface SaveUserShowResult {

    record Success() implements SaveUserShowResult {

    }

    record ShowNotFound() implements SaveUserShowResult {

    }

    record Failure() implements SaveUserShowResult {

    }

}
