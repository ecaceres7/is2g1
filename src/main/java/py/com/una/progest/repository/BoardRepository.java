package py.com.una.progest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.una.progest.models.Board;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
  Optional<Board> findByName(String name);

  Boolean existsByName(String name);



}
