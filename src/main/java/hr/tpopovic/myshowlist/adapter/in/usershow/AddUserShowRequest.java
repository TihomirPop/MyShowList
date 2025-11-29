package hr.tpopovic.myshowlist.adapter.in.usershow;

public record AddUserShowRequest(
        String showId,
        Integer progress,
        String status,
        Short score
) {

}
