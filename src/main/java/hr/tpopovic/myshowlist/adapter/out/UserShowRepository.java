package hr.tpopovic.myshowlist.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserShowRepository extends JpaRepository<UserShowEntity, UserShowId> {

    @Query("select u from UserShowEntity u join fetch u.show where u.id.userId = ?1")
    List<UserShowEntity> findByUserId(Integer userId);

}
