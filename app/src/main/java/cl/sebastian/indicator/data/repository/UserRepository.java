package cl.sebastian.indicator.data.repository;

import cl.sebastian.indicator.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsernameIgnoreCaseAndPassword(String username, String password);

}
