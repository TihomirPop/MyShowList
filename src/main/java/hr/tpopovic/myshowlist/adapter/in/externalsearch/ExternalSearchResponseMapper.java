package hr.tpopovic.myshowlist.adapter.in.externalsearch;

import hr.tpopovic.myshowlist.adapter.in.show.ShowDto;
import hr.tpopovic.myshowlist.adapter.in.show.ShowDtoMapper;
import hr.tpopovic.myshowlist.application.domain.model.AverageScore;
import hr.tpopovic.myshowlist.application.port.in.SearchExternalShowsResult;

import java.util.List;

public final class ExternalSearchResponseMapper {
    private static final AverageScore ZERO_AVERAGE_SCORE = new AverageScore(0.0);

    private ExternalSearchResponseMapper() {
    }

    public static ExternalSearchResponse toResponse(SearchExternalShowsResult result) {
        return switch (result) {
            case SearchExternalShowsResult.Success success -> {
                List<ShowDto> tvSeriesDtos = success.tvSeries().stream()
                        .map(tvSeries -> ShowDtoMapper.toDto(tvSeries, ZERO_AVERAGE_SCORE))
                        .toList();

                List<ShowDto> movieDtos = success.movies().stream()
                        .map(movie -> ShowDtoMapper.toDto(movie, ZERO_AVERAGE_SCORE))
                        .toList();

                yield new ExternalSearchResponse.Ok(tvSeriesDtos, movieDtos);
            }
            case SearchExternalShowsResult.Failure failure ->
                    new ExternalSearchResponse.Failure(failure.message());
        };
    }
}
