package hr.tpopovic.myshowlist.application.port.out;

import hr.tpopovic.myshowlist.application.domain.model.Genre;

import java.util.Set;

public interface ForLoadingGenres {

    ValidateGenresResult validate(Set<Genre> genres);

}
