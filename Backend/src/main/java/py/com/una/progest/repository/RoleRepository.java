package py.com.una.progest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.una.progest.models.ERole;
import py.com.una.progest.models.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
