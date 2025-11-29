package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.*;

public record SaveUserShowCommand(
        Username username,
        ShowId showId,
        Progress progress,
        Status status,
        Score score
) {

}
