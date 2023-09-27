package py.com.una.progest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.una.progest.models.ListColumn;
import py.com.una.progest.models.Task;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  Boolean existsByTitleAndListColumn(String title, ListColumn column);

  Optional<Set<Task>> findByListColumnId(Long columnId);

}
