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
        @JsonSubTypes.Type(value = CreateMovieRequest.class, name = "MOVIE"),
        @JsonSubTypes.Type(value = CreateTvSeriesRequest.class, name = "TV_SERIES")
})
public abstract sealed class CreateShowRequest permits CreateMovieRequest, CreateTvSeriesRequest {

    private String title;
    private String description;
    private Set<String> genres;

    protected CreateShowRequest() {
    }

    protected CreateShowRequest(String title, String description, Set<String> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

}
