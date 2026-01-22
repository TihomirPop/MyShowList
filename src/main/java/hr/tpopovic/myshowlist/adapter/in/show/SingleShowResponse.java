package hr.tpopovic.myshowlist.adapter.in.show;

public sealed interface SingleShowResponse {
    record Ok(ShowDto show) implements SingleShowResponse {}
    record NotFound() implements SingleShowResponse {}
    record Failure(String message) implements SingleShowResponse {}
}
