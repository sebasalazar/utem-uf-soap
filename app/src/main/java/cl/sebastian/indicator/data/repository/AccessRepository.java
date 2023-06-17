package cl.sebastian.indicator.data.repository;

import cl.sebastian.indicator.data.model.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {

}
