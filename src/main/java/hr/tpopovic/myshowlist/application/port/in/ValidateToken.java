package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.Token;

public interface ValidateToken {

    ValidateTokenResult validate(Token token);

}
