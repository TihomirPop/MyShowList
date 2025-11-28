package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Username;

public interface ForFetchingPasswordHash {

    FetchPasswordHashResult fetch(Username username);

}
