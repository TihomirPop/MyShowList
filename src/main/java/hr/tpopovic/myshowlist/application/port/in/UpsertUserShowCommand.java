package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.*;

public record UpsertUserShowCommand(
        Username username,
        ShowId showId,
        Progress progress,
        Status status,
        Score score
) {

}
