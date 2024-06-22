package server.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.core.entity.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    EmployeeEntity findByUsername(String username);
    EmployeeEntity findByName(String name);
    boolean existsByUsername(String username);
    boolean existsByName(String name);
}
