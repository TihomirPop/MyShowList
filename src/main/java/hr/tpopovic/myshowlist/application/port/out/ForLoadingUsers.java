package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.UserId;
import hr.tpopovic.myshowlist.application.domain.model.Username;

public interface ForLoadingUsers {

    Username loadUsername(UserId userId);
}
