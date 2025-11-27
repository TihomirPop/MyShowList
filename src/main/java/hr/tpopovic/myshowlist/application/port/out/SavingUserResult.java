package hr.tpopovic.myshowlist.application.port.out;

import static java.util.Objects.requireNonNull;

public sealed interface SavingUserResult {

    record Success() implements SavingUserResult {

    }

    record Failure() implements SavingUserResult {

    }

}
