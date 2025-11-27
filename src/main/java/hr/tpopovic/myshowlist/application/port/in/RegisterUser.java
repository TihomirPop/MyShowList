package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.RegisteringUser;

public interface RegisterUser {

    RegisterUserResult register(RegisteringUser user);

}
