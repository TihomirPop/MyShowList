package hr.tpopovic.myshowlist.adapter.in.usershow;

public record UpsertUserShowRequest(
        String showId,
        Integer progress,
        String status,
        Short score
) {

}
