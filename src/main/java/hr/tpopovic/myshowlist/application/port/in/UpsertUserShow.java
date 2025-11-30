package hr.tpopovic.myshowlist.application.port.in;

public interface UpsertUserShow {

    UpsertUserShowResult upsert(UpsertUserShowCommand command);

}
