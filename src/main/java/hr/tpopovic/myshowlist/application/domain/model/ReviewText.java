package hr.tpopovic.myshowlist.application.domain.model;

public record ReviewText(String value) {

    private static final int MAX_LENGTH = 10000;

    public ReviewText {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Review text cannot be null or blank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Review text cannot exceed " + MAX_LENGTH + " characters");
        }
    }
}
