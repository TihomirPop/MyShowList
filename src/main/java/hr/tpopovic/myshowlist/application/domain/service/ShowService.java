package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.domain.model.Show;
import hr.tpopovic.myshowlist.application.port.in.FetchShows;
import hr.tpopovic.myshowlist.application.port.in.FetchShowsResult;
import hr.tpopovic.myshowlist.application.port.in.ShowDetails;
import hr.tpopovic.myshowlist.application.port.out.ForFetchingScore;
import hr.tpopovic.myshowlist.application.port.out.LoadShowsResult;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShows;

import java.util.List;
import java.util.stream.Collectors;

public class ShowService implements FetchShows {

    private final ForLoadingShows forLoadingShows;
    private final ForFetchingScore forFetchingScore;

    public ShowService(ForLoadingShows forLoadingShows, ForFetchingScore forFetchingScore) {
        this.forLoadingShows = forLoadingShows;
        this.forFetchingScore = forFetchingScore;
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

}
