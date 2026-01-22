package hr.tpopovic.myshowlist.adapter.in.show;

import hr.tpopovic.myshowlist.application.port.in.FetchShowResult;

public class SingleShowResponseMapper {

    private SingleShowResponseMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    static SingleShowResponse toResponse(FetchShowResult result) {
        return switch (result) {
            case FetchShowResult.Success success -> {
                ShowDto showDto = ShowDtoMapper.toDto(
                    success.showDetails().show(),
                    success.showDetails().averageScore()
                );
                yield new SingleShowResponse.Ok(showDto);
            }
            case FetchShowResult.NotFound _ -> new SingleShowResponse.NotFound();
            case FetchShowResult.Failure _ -> new SingleShowResponse.Failure("Failed to fetch show");
        };
    }
}
