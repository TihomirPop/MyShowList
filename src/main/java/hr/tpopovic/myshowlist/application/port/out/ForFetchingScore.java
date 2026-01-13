package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.AverageScore;
import hr.tpopovic.myshowlist.application.domain.model.ShowId;

public interface ForFetchingScore {

    AverageScore fetch(ShowId showId);

}
