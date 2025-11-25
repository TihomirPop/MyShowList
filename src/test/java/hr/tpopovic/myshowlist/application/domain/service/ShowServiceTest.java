package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.Description;
import hr.tpopovic.myshowlist.application.domain.model.Movie;
import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.domain.model.Title;
import hr.tpopovic.myshowlist.application.port.in.FetchShowsResult;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShows;
import hr.tpopovic.myshowlist.application.port.out.LoadShowsResult;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShowServiceTest {

    @Test
    void should_fetch_shows() {
        // given
        ForLoadingShows forLoadingShows = mock(ForLoadingShows.class);
        ShowService showService = new ShowService(forLoadingShows);
        Movie movie = new Movie(
                new ShowId(java.util.UUID.randomUUID()),
                new Title("Inception"),
                new Description("A mind-bending thriller"),
                LocalDate.of(2010, 7, 16)
        );
        when(forLoadingShows.load())
                .thenReturn(new LoadShowsResult.Success(List.of(movie)));

        // when
        FetchShowsResult result = showService.fetchShows();

        // then
        assertThat(result)
                .isNotNull()
                .isInstanceOf(FetchShowsResult.Success.class);
        FetchShowsResult.Success successResult = (FetchShowsResult.Success) result;
        assertThat(successResult.shows())
                .hasSize(1)
                .first()
                .isEqualTo(movie);
    }

}