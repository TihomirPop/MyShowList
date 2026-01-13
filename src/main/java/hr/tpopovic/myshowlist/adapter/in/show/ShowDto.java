package hr.tpopovic.myshowlist.adapter.in.show;

import java.util.Set;

public abstract sealed class ShowDto permits MovieDto, TvSeriesDto {

    private final String id;
    private final String title;
    private final String description;
    private final Set<String> genres;
    private final Double averageScore;

    protected ShowDto(String id, String title, String description, Set<String> genres, Double averageScore) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.averageScore = averageScore;
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

}