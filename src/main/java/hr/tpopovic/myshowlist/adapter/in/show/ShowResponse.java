package hr.tpopovic.myshowlist.adapter.in.show;

import java.util.List;

public sealed interface ShowResponse {

    record Ok(List<ShowDto> shows) implements ShowResponse {

    }

    record Failure(String message) implements ShowResponse {

    }

}
