package hr.tpopovic.myshowlist.adapter.in;

import java.util.List;

public sealed interface ShowResponse {

    record Ok(List<ShowDto> shows) implements ShowResponse {

    }

}
