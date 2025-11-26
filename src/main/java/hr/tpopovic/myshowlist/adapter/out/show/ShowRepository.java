package hr.tpopovic.myshowlist.adapter.out.show;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShowRepository extends JpaRepository<ShowEntity, UUID> {

}
