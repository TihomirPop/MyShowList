package hr.tpopovic.myshowlist.application.domain.service;

import hr.tpopovic.myshowlist.application.port.in.FetchShows;
import hr.tpopovic.myshowlist.application.port.in.FetchShowsResult;
import hr.tpopovic.myshowlist.application.port.out.LoadShowsResult;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShows;

public class ShowService implements FetchShows {

    private final ForLoadingShows forLoadingShows;

    public ShowService(ForLoadingShows forLoadingShows) {
        this.forLoadingShows = forLoadingShows;
    }

    @Override
    public FetchShowsResult fetchShows() {

        LoadShowsResult result = forLoadingShows.load();

        return switch (result) {
            case LoadShowsResult.Success success -> new FetchShowsResult.Success(success.shows());
            case LoadShowsResult.Failure _ -> new FetchShowsResult.Failure();
        };
    }

}
