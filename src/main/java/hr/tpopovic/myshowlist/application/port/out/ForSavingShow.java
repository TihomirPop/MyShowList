package hr.tpopovic.myshowlist.application.port.out;

public interface ForSavingShow {

    SaveShowResult save(SaveShowCommand command);

}
