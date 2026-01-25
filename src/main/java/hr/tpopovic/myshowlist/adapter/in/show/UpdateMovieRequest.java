package hr.tpopovic.myshowlist.adapter.in.show;

import java.time.LocalDate;
import java.util.Set;

public final class UpdateMovieRequest extends UpdateShowRequest {

    private LocalDate releaseDate;

    public UpdateMovieRequest() {
    }

    public UpdateMovieRequest(String id, String title, String description, Set<String> genres, String thumbnailUrl, LocalDate releaseDate) {
        super(id, title, description, genres, thumbnailUrl);
        this.releaseDate = releaseDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

}
