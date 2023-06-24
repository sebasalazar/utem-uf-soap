package cl.sebastian.indicator.data.repository;

import cl.sebastian.indicator.data.model.Uf;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UfRepository extends JpaRepository<Uf, Long> {

    public Uf findByDate(LocalDate date);
}
