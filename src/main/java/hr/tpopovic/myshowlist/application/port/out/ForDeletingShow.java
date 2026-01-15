package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.ShowId;

public interface ForDeletingShow {

    DeleteShowPortResult delete(ShowId showId);

}
