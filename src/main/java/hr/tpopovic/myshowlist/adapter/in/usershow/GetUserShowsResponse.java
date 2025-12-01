package hr.tpopovic.myshowlist.adapter.in.usershow;

import java.util.List;

public sealed interface GetUserShowsResponse {

    record Success(List<UserShowDto> shows) implements GetUserShowsResponse {

    }

}
