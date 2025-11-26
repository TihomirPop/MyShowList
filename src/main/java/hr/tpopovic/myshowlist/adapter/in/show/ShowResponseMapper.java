package hr.tpopovic.myshowlist.adapter.in.show;

import hr.tpopovic.myshowlist.application.port.in.FetchShowsResult;

import java.util.List;

public class ShowResponseMapper {

    private ShowResponseMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    static ShowResponse toResponse(FetchShowsResult result) {
        return switch (result) {
            case FetchShowsResult.Success success -> success(success);
        };
    }

    private static ShowResponse success(FetchShowsResult.Success success) {
        List<ShowDto> shows = success.shows()
                .stream()
                .map(ShowDtoMapper::toDto)
                .toList();

        return new ShowResponse.Ok(shows);
    }

}
