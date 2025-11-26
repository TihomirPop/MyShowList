package hr.tpopovic.myshowlist.adapter.out.show;

import hr.tpopovic.myshowlist.application.domain.model.Show;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShows;
import hr.tpopovic.myshowlist.application.port.out.LoadShowsResult;

import java.util.List;

public class ShowLoader implements ForLoadingShows {

    private final ShowRepository showRepository;

    public ShowLoader(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public LoadShowsResult load() {
        List<Show> shows = showRepository.findAll()
                .stream()
                .map(ShowEntityMapper::toDomain)
                .toList();

        return new LoadShowsResult.Success(shows);
    }

}
