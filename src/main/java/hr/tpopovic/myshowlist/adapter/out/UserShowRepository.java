package hr.tpopovic.myshowlist.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserShowRepository extends JpaRepository<UserShowEntity, UserShowId> {

    @Query("select u from UserShowEntity u join fetch u.show where u.id.userId = ?1")
    List<UserShowEntity> findByUserId(Integer userId);

    @Query("select avg(u.score) from UserShowEntity u where u.id.showId = ?1 and u.score is not null")
    Optional<Double> fetchAverageScore(UUID id);

}
