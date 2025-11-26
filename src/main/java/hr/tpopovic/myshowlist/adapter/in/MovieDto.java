package hr.tpopovic.myshowlist.adapter.in;

import java.time.LocalDate;

public final class MovieDto extends ShowDto {

    private final LocalDate releaseDate;

    public MovieDto(String id, String title, String description, LocalDate releaseDate) {
        super(id, title, description);
        this.releaseDate = releaseDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

}
