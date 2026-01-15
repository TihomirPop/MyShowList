package hr.tpopovic.myshowlist.adapter.out.show;

import hr.tpopovic.myshowlist.application.domain.model.Genre;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingGenres;
import hr.tpopovic.myshowlist.application.port.out.ValidateGenresResult;

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
    public ValidateGenresResult validate(Set<Genre> genres) {
        try {
            Set<String> genreNames = genres.stream()
                    .map(Genre::name)
                    .collect(Collectors.toSet());

            List<GenreEntity> foundEntities = genreRepository.findByNameIn(genreNames);

            Set<Genre> foundGenres = foundEntities.stream()
                    .map(entity -> new Genre(entity.getName()))
                    .collect(Collectors.toSet());

            Set<String> foundNames = foundGenres.stream()
                    .map(Genre::name)
                    .collect(Collectors.toSet());

            Set<String> missingGenres = new HashSet<>(genreNames);
            missingGenres.removeAll(foundNames);

            return new ValidateGenresResult.Success(foundGenres, missingGenres);
        } catch (Exception e) {
            return new ValidateGenresResult.Failure("Failed to load genres: " + e.getMessage());
        }
    }

}
