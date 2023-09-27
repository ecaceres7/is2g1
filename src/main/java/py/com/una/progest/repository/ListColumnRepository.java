package py.com.una.progest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.una.progest.models.ListColumn;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ListColumnRepository extends JpaRepository<ListColumn, Long> {
  Optional<ListColumn> findByBoardIdAndName(Long board, String name);
  Optional<Set<ListColumn>> findByBoardId(Long board);


}
