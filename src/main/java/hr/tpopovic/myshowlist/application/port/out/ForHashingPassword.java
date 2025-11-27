package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.HashedPassword;
import hr.tpopovic.myshowlist.application.domain.model.Password;

public interface ForHashingPassword {

    HashedPassword hash(Password password);

}
