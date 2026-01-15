package hr.tpopovic.myshowlist.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ReviewEntity, ReviewId> {

    @Query("select r from ReviewEntity r join fetch r.show where r.id.showId = ?1")
    List<ReviewEntity> findByShowId(UUID showId);
}
