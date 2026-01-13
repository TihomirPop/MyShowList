package hr.tpopovic.myshowlist.application.port.in;

import hr.tpopovic.myshowlist.application.domain.model.AverageScore;
import hr.tpopovic.myshowlist.application.domain.model.UserShow;

public record UserShowDetails(UserShow userShow, AverageScore averageScore) {

}
