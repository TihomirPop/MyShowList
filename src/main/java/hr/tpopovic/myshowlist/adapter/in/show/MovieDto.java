package hr.tpopovic.myshowlist.adapter.in.show;

import java.time.LocalDate;
import java.util.Set;

public final class MovieDto extends ShowDto {

    private final LocalDate releaseDate;

    public MovieDto(String id, String title, String description, Set<String> genres, Double averageScore, String thumbnailUrl, LocalDate releaseDate) {
        super(id, title, description, genres, averageScore, thumbnailUrl);
        this.releaseDate = releaseDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

}
