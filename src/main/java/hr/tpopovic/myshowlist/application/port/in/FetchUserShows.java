package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.Username;

public interface FetchUserShows {

    FetchUserShowsResult fetch(Username username);

}
