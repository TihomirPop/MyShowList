package hr.tpopovic.myshowlist.adapter.in.show;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.FetchShowResult;
import hr.tpopovic.myshowlist.application.port.in.ShowDetails;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SingleShowResponseMapperTest {

    @Test
    void should_map_success_result_to_ok_response() {
        // given
        ShowId showId = new ShowId(UUID.randomUUID());
        Movie movie = new Movie(
                showId,
                new Title("Inception"),
                new Description("A mind-bending thriller"),
                Set.of(new Genre("Sci-Fi")),
                new ThumbnailUrl("https://example.com/inception.png"),
                LocalDate.of(2010, 7, 16)
        );
        AverageScore averageScore = new AverageScore(8.5);
        ShowDetails showDetails = new ShowDetails(movie, averageScore);
        FetchShowResult.Success success = new FetchShowResult.Success(showDetails);

        // when
        SingleShowResponse response = SingleShowResponseMapper.toResponse(success);

        // then
        assertThat(response).isInstanceOf(SingleShowResponse.Ok.class);
        SingleShowResponse.Ok ok = (SingleShowResponse.Ok) response;
        assertThat(ok.show()).isInstanceOf(MovieDto.class);

        MovieDto movieDto = (MovieDto) ok.show();
        assertThat(movieDto.getId()).isEqualTo(showId.id().toString());
        assertThat(movieDto.getTitle()).isEqualTo("Inception");
        assertThat(movieDto.getDescription()).isEqualTo("A mind-bending thriller");
        assertThat(movieDto.getGenres()).containsExactly("Sci-Fi");
        assertThat(movieDto.getAverageScore()).isEqualTo(8.5);
        assertThat(movieDto.getThumbnailUrl()).isEqualTo("https://example.com/inception.png");
        assertThat(movieDto.getReleaseDate()).isEqualTo(LocalDate.of(2010, 7, 16));
    }

    @Test
    void should_map_not_found_result_to_not_found_response() {
        // given
        FetchShowResult.NotFound notFound = new FetchShowResult.NotFound();

        // when
        SingleShowResponse response = SingleShowResponseMapper.toResponse(notFound);

        // then
        assertThat(response).isInstanceOf(SingleShowResponse.NotFound.class);
    }

    @Test
    void should_map_failure_result_to_failure_response() {
        // given
        FetchShowResult.Failure failure = new FetchShowResult.Failure();

        // when
        SingleShowResponse response = SingleShowResponseMapper.toResponse(failure);

        // then
        assertThat(response).isInstanceOf(SingleShowResponse.Failure.class);
        SingleShowResponse.Failure failureResponse = (SingleShowResponse.Failure) response;
        assertThat(failureResponse.message()).isEqualTo("Failed to fetch show");
    }

}
