package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.FetchShowResult;
import hr.tpopovic.myshowlist.application.port.out.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShowServiceTest {

    @Test
    void should_fetch_show_successfully() {
        // given
        ForLoadingShows forLoadingShows = mock(ForLoadingShows.class);
        ForFetchingScore forFetchingScore = mock(ForFetchingScore.class);
        ForLoadingGenres forLoadingGenres = mock(ForLoadingGenres.class);
        ForSavingShow forSavingShow = mock(ForSavingShow.class);
        ForUpdatingShow forUpdatingShow = mock(ForUpdatingShow.class);
        ForDeletingShow forDeletingShow = mock(ForDeletingShow.class);

        ShowService showService = new ShowService(
                forLoadingShows,
                forFetchingScore,
                forLoadingGenres,
                forSavingShow,
                forUpdatingShow,
                forDeletingShow
        );

        ShowId showId = new ShowId(UUID.randomUUID());
        Movie movie = new Movie(
                showId,
                new Title("Inception"),
                new Description("A mind-bending thriller"),
                Set.of(new Genre("Sci-Fi")),
                LocalDate.of(2010, 7, 16)
        );
        AverageScore averageScore = new AverageScore(8.5);

        when(forLoadingShows.load(showId)).thenReturn(new LoadShowResult.Success(movie));
        when(forFetchingScore.fetch(showId)).thenReturn(averageScore);

        // when
        FetchShowResult result = showService.fetchShow(showId);

        // then
        assertThat(result).isInstanceOf(FetchShowResult.Success.class);
        FetchShowResult.Success success = (FetchShowResult.Success) result;
        assertThat(success.showDetails().show()).isEqualTo(movie);
        assertThat(success.showDetails().averageScore()).isEqualTo(averageScore);
    }

    @Test
    void should_return_not_found_when_show_does_not_exist() {
        // given
        ForLoadingShows forLoadingShows = mock(ForLoadingShows.class);
        ForFetchingScore forFetchingScore = mock(ForFetchingScore.class);
        ForLoadingGenres forLoadingGenres = mock(ForLoadingGenres.class);
        ForSavingShow forSavingShow = mock(ForSavingShow.class);
        ForUpdatingShow forUpdatingShow = mock(ForUpdatingShow.class);
        ForDeletingShow forDeletingShow = mock(ForDeletingShow.class);

        ShowService showService = new ShowService(
                forLoadingShows,
                forFetchingScore,
                forLoadingGenres,
                forSavingShow,
                forUpdatingShow,
                forDeletingShow
        );

        ShowId showId = new ShowId(UUID.randomUUID());
        when(forLoadingShows.load(showId)).thenReturn(new LoadShowResult.NotFound());

        // when
        FetchShowResult result = showService.fetchShow(showId);

        // then
        assertThat(result).isInstanceOf(FetchShowResult.NotFound.class);
    }

    @Test
    void should_return_failure_when_loading_fails() {
        // given
        ForLoadingShows forLoadingShows = mock(ForLoadingShows.class);
        ForFetchingScore forFetchingScore = mock(ForFetchingScore.class);
        ForLoadingGenres forLoadingGenres = mock(ForLoadingGenres.class);
        ForSavingShow forSavingShow = mock(ForSavingShow.class);
        ForUpdatingShow forUpdatingShow = mock(ForUpdatingShow.class);
        ForDeletingShow forDeletingShow = mock(ForDeletingShow.class);

        ShowService showService = new ShowService(
                forLoadingShows,
                forFetchingScore,
                forLoadingGenres,
                forSavingShow,
                forUpdatingShow,
                forDeletingShow
        );

        ShowId showId = new ShowId(UUID.randomUUID());
        when(forLoadingShows.load(showId)).thenReturn(new LoadShowResult.Failure());

        // when
        FetchShowResult result = showService.fetchShow(showId);

        // then
        assertThat(result).isInstanceOf(FetchShowResult.Failure.class);
    }

}
