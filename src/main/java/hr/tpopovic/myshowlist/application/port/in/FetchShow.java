package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.ShowId;

public interface FetchShow {
    FetchShowResult fetchShow(ShowId showId);
}
