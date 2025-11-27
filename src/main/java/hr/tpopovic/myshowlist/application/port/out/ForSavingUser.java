package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.User;

public interface ForSavingUser {

    SavingUserResult save(User user);

}
