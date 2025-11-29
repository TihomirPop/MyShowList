package hr.tpopovic.myshowlist.application.port.out;

public interface ForSavingUserShow {

    SaveUserShowResult save(SaveUserShowCommand command);

}
