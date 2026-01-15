package hr.tpopovic.myshowlist.adapter.out.show;

import hr.tpopovic.myshowlist.application.domain.model.Genre;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingGenres;
import hr.tpopovic.myshowlist.application.port.out.LoadGenresResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreLoader implements ForLoadingGenres {

    private final GenreRepository genreRepository;

    public GenreLoader(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public LoadGenresResult loadByNames(Set<String> genreNames) {
        try {
            List<GenreEntity> foundEntities = genreRepository.findByNameIn(genreNames);

            Set<Genre> foundGenres = foundEntities.stream()
                    .map(entity -> new Genre(entity.getName()))
                    .collect(Collectors.toSet());

            Set<String> foundNames = foundGenres.stream()
                    .map(Genre::name)
                    .collect(Collectors.toSet());

            Set<String> missingGenres = new HashSet<>(genreNames);
            missingGenres.removeAll(foundNames);

            return new LoadGenresResult.Success(foundGenres, missingGenres);
        } catch (Exception e) {
            return new LoadGenresResult.Failure("Failed to load genres: " + e.getMessage());
        }
    }

}
