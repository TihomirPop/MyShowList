package hr.tpopovic.myshowlist.adapter.in.usershow;

import hr.tpopovic.myshowlist.adapter.in.show.ShowDto;

public record UserShowDto(
        ShowDto show,
        Integer progress,
        String status,
        Short score
) {

}
