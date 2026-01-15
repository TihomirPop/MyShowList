package hr.tpopovic.myshowlist.adapter.out.show;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends JpaRepository<GenreEntity, Short> {

    List<GenreEntity> findByNameIn(Set<String> names);

}
