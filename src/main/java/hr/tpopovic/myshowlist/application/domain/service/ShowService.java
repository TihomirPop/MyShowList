package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.*;
import hr.tpopovic.myshowlist.application.port.in.*;
import hr.tpopovic.myshowlist.application.port.out.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShowService implements FetchShows, FetchShow, CreateShow, UpdateShow, DeleteShow {

    private final ForLoadingShows forLoadingShows;
    private final ForFetchingScore forFetchingScore;
    private final ForLoadingGenres forLoadingGenres;
    private final ForSavingShow forSavingShow;
    private final ForUpdatingShow forUpdatingShow;
    private final ForDeletingShow forDeletingShow;

    public ShowService(
            ForLoadingShows forLoadingShows,
            ForFetchingScore forFetchingScore,
            ForLoadingGenres forLoadingGenres,
            ForSavingShow forSavingShow,
            ForUpdatingShow forUpdatingShow,
            ForDeletingShow forDeletingShow
    ) {
        this.forLoadingShows = forLoadingShows;
        this.forFetchingScore = forFetchingScore;
        this.forLoadingGenres = forLoadingGenres;
        this.forSavingShow = forSavingShow;
        this.forUpdatingShow = forUpdatingShow;
        this.forDeletingShow = forDeletingShow;
    }

    @Override
    public FetchShowsResult fetchShows() {

        LoadShowsResult result = forLoadingShows.load();

        return switch (result) {
            case LoadShowsResult.Success(List<Show> shows) -> handleShowsLoaded(shows);
            case LoadShowsResult.Failure _ -> new FetchShowsResult.Failure();
        };
    }

    private FetchShowsResult handleShowsLoaded(List<Show> shows) {
        return shows.stream()
                .map(show -> new ShowDetails(show, forFetchingScore.fetch(show.id())))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        FetchShowsResult.Success::new
                ));
    }

    @Override
    public FetchShowResult fetchShow(ShowId showId) {
        LoadShowResult result = forLoadingShows.load(showId);

        return switch (result) {
            case LoadShowResult.Success(Show show) -> {
                AverageScore averageScore = forFetchingScore.fetch(show.id());
                ShowDetails showDetails = new ShowDetails(show, averageScore);
                yield new FetchShowResult.Success(showDetails);
            }
            case LoadShowResult.NotFound _ -> new FetchShowResult.NotFound();
            case LoadShowResult.Failure _ -> new FetchShowResult.Failure();
        };
    }

    @Override
    public CreateShowResult create(CreateShowCommand command) {
        ValidateGenresResult genresResult = forLoadingGenres.validate(command.genres());

        return switch (genresResult) {
            case ValidateGenresResult.Success(Set<Genre> foundGenres, Set<String> missingGenres) ->
                    handleGenresValidated(command, foundGenres, missingGenres);
            case ValidateGenresResult.Failure(String message) -> new CreateShowResult.Failure(message);
        };
    }

    private CreateShowResult handleGenresValidated(CreateShowCommand command, Set<Genre> foundGenres, Set<String> missingGenres) {
        if (!missingGenres.isEmpty()) {
            return new CreateShowResult.GenresNotFound(missingGenres);
        }

        Show show = buildShowFromCreateCommand(command, foundGenres);
        SaveShowResult saveResult = forSavingShow.save(new SaveShowCommand(show));

        return switch (saveResult) {
            case SaveShowResult.Success(ShowId showId) -> new CreateShowResult.Success(showId);
            case SaveShowResult.Failure(String message) -> new CreateShowResult.Failure(message);
        };
    }

    private Show buildShowFromCreateCommand(CreateShowCommand command, Set<Genre> genres) {
        ShowId showId = new ShowId(UUID.randomUUID());
        return switch (command) {
            case CreateShowCommand.CreateMovie(Title title, Description description, Set<Genre> _, ThumbnailUrl thumbnailUrl, LocalDate releaseDate) ->
                    new Movie(showId, title, description, genres, thumbnailUrl, releaseDate);
            case CreateShowCommand.CreateTvSeries(Title title, Description description, Set<Genre> _, ThumbnailUrl thumbnailUrl, EpisodeCount episodeCount, DateRange airingPeriod) ->
                    new TvSeries(showId, title, description, genres, thumbnailUrl, episodeCount, airingPeriod);
        };
    }

    @Override
    public UpdateShowResult update(UpdateShowCommand command) {
        ValidateGenresResult genresResult = forLoadingGenres.validate(command.genres());

        return switch (genresResult) {
            case ValidateGenresResult.Success(Set<Genre> foundGenres, Set<String> missingGenres) ->
                    handleGenresValidatedForUpdate(command, foundGenres, missingGenres);
            case ValidateGenresResult.Failure(String message) -> new UpdateShowResult.Failure(message);
        };
    }

    private UpdateShowResult handleGenresValidatedForUpdate(UpdateShowCommand command, Set<Genre> foundGenres, Set<String> missingGenres) {
        if (!missingGenres.isEmpty()) {
            return new UpdateShowResult.GenresNotFound(missingGenres);
        }

        Show show = buildShowFromUpdateCommand(command, foundGenres);
        UpdateShowPortResult updateResult = forUpdatingShow.update(new UpdateShowPortCommand(show));

        return switch (updateResult) {
            case UpdateShowPortResult.Success _ -> new UpdateShowResult.Success();
            case UpdateShowPortResult.ShowNotFound _ -> new UpdateShowResult.ShowNotFound();
            case UpdateShowPortResult.Failure(String message) -> new UpdateShowResult.Failure(message);
        };
    }

    private Show buildShowFromUpdateCommand(UpdateShowCommand command, Set<Genre> genres) {
        return switch (command) {
            case UpdateShowCommand.UpdateMovie(ShowId showId, Title title, Description description, Set<Genre> _, ThumbnailUrl thumbnailUrl, LocalDate releaseDate) ->
                    new Movie(showId, title, description, genres, thumbnailUrl, releaseDate);
            case UpdateShowCommand.UpdateTvSeries(ShowId showId, Title title, Description description, Set<Genre> _, ThumbnailUrl thumbnailUrl, EpisodeCount episodeCount, DateRange airingPeriod) ->
                    new TvSeries(showId, title, description, genres, thumbnailUrl, episodeCount, airingPeriod);
        };
    }

    @Override
    public DeleteShowResult delete(ShowId showId) {
        DeleteShowPortResult result = forDeletingShow.delete(showId);

        return switch (result) {
            case DeleteShowPortResult.Success _ -> new DeleteShowResult.Success();
            case DeleteShowPortResult.ShowNotFound _ -> new DeleteShowResult.ShowNotFound();
            case DeleteShowPortResult.Failure(String message) -> new DeleteShowResult.Failure(message);
        };
    }

}
