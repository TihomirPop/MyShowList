package hr.tpopovic.myshowlist.adapter.in.show;

import java.time.LocalDate;
import java.util.Set;

public final class CreateMovieRequest extends CreateShowRequest {

    private LocalDate releaseDate;

    public CreateMovieRequest() {
    }

    public CreateMovieRequest(String title, String description, Set<String> genres, LocalDate releaseDate) {
        super(title, description, genres);
        this.releaseDate = releaseDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

}
