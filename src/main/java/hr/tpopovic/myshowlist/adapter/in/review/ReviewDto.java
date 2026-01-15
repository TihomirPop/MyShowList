package hr.tpopovic.myshowlist.adapter.in.review;

public record ReviewDto(
        String reviewText,
        String username
) {
    public ReviewDto(String reviewText) {
        this(reviewText, null);
    }
}
