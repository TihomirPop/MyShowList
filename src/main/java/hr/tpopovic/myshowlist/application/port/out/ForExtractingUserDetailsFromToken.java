package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Token;

public interface ForExtractingUserDetailsFromToken {

    UserDetailsFromTokenExtractionResult extract(Token token);

}
