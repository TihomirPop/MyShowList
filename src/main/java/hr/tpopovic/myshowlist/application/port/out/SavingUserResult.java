package hr.tpopovic.myshowlist.application.port.out;

public sealed interface SavingUserResult {

    record Success() implements SavingUserResult {

    }

}
