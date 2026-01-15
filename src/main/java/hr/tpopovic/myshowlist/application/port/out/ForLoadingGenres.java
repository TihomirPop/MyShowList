package hr.tpopovic.myshowlist.application.port.out;

import java.util.Set;

public interface ForLoadingGenres {

    LoadGenresResult loadByNames(Set<String> genreNames);

}
