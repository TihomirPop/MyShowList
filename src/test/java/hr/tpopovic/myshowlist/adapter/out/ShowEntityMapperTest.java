package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

class ShowEntityMapperTest {

    @Test
    void should_map_movie_entity_to_movie() {
        // given
        var movieEntity = new MovieEntity();
        UUID id = UUID.randomUUID();
        movieEntity.setId(id);
        movieEntity.setTitle("Inception");
        movieEntity.setDescription("A mind-bending thriller");
        movieEntity.setReleaseDate(LocalDate.of(2010, 7, 16));

        // when
        Show show = ShowEntityMapper.toDomain(movieEntity);

        // then
        assertThat(show)
                .isNotNull()
                .asInstanceOf(type(Movie.class))
                .extracting(
                        Movie::id,
                        Movie::title,
                        Movie::description,
                        Movie::releaseDate
                )
                .containsExactly(
                        new ShowId(UUID.fromString(id.toString())),
                        new Title("Inception"),
                        new Description("A mind-bending thriller"),
                        LocalDate.of(2010, 7, 16)
                );

    }

}