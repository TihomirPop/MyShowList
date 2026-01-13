package hr.tpopovic.myshowlist.adapter.out;

import hr.tpopovic.myshowlist.application.domain.model.AverageScore;
import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.port.out.ForFetchingScore;

public class ScoreFetcher implements ForFetchingScore {

    private final UserShowRepository userShowRepository;

    public ScoreFetcher(UserShowRepository userShowRepository1) {
        this.userShowRepository = userShowRepository1;
    }

    @Override
    public AverageScore fetch(ShowId showId) {
        return userShowRepository.fetchAverageScore(showId.id())
                .map(AverageScore::new)
                .orElseGet(() -> new AverageScore(0.0));
    }

}
