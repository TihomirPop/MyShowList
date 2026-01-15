package hr.tpopovic.myshowlist.adapter.in.review;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${service.api.review.path}")
public class ReviewController {

    @PostMapping
    public ResponseEntity<Void> addReview(
            @RequestBody ReviewDto reviewDto,
            @PathVariable String showId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // upsert logic
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateReview(
            @RequestBody ReviewDto reviewDto,
            @PathVariable String showId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // upsert logic
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getReviews(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(List.of(new ReviewDto("Great show!"), new ReviewDto("Not bad.")));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReview(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().build();
    }

}
