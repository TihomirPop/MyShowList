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
        @JsonSubTypes.Type(value = UpdateMovieRequest.class, name = "MOVIE"),
        @JsonSubTypes.Type(value = UpdateTvSeriesRequest.class, name = "TV_SERIES")
})
public abstract sealed class UpdateShowRequest permits UpdateMovieRequest, UpdateTvSeriesRequest {

    private String id;
    private String title;
    private String description;
    private Set<String> genres;
    private String thumbnailUrl;

    protected UpdateShowRequest() {
    }

    protected UpdateShowRequest(String id, String title, String description, Set<String> genres, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
