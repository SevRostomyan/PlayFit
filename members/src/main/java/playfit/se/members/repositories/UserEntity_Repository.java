package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playfit.se.members.entity.User_Entity;

import java.util.Optional;

@Repository
public interface UserEntity_Repository extends JpaRepository<User_Entity, Long> {
 Optional<User_Entity> findUserByEmail (String email);
}
