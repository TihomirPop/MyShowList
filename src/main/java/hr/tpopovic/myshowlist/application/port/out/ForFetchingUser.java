package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Username;

public interface ForFetchingUser {

    FetchUserIdResult fetch(Username username);

}
