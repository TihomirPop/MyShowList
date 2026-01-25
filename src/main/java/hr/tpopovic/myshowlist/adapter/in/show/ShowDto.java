package hr.tpopovic.myshowlist.adapter.in.show;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MovieDto.class, name = "MOVIE"),
        @JsonSubTypes.Type(value = TvSeriesDto.class, name = "TV_SERIES")
})
public abstract sealed class ShowDto permits MovieDto, TvSeriesDto {

    private final String id;
    private final String title;
    private final String description;
    private final Set<String> genres;
    private final Double averageScore;
    private final String thumbnailUrl;

    protected ShowDto(String id, String title, String description, Set<String> genres, Double averageScore, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.averageScore = averageScore;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

}