package hr.tpopovic.myshowlist.adapter.out.show;

import hr.tpopovic.myshowlist.application.domain.model.Show;
import hr.tpopovic.myshowlist.application.port.out.ForLoadingShows;
import hr.tpopovic.myshowlist.application.port.out.LoadShowsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShowLoader implements ForLoadingShows {

    private static final Logger log = LoggerFactory.getLogger(ShowLoader.class);

    private final ShowRepository showRepository;

    public ShowLoader(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public LoadShowsResult load() {
        try {
            List<Show> shows = showRepository.findAll()
                    .stream()
                    .map(ShowEntityMapper::toDomain)
                    .toList();

            return new LoadShowsResult.Success(shows);
        } catch (RuntimeException e) {
            log.error("Error loading shows", e);
            return new LoadShowsResult.Failure();
        }

    }

}
