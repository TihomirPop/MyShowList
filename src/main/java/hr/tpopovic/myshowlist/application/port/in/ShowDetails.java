package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.AverageScore;
import hr.tpopovic.myshowlist.application.domain.model.Show;

import static java.util.Objects.requireNonNull;

public record ShowDetails(Show show, AverageScore averageScore) {

    public ShowDetails {
        requireNonNull(show, "show");
        requireNonNull(averageScore, "averageScore");
    }

}
