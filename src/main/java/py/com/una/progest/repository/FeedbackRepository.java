package py.com.una.progest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.una.progest.models.Feedback;
import py.com.una.progest.models.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
  Optional<List<Feedback>> findByTask(Task task);


}
