package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.UserId;

public interface ForLoadingUserShows {

    LoadUserShowsResult load(UserId userId);

}
