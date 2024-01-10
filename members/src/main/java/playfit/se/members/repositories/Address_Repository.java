package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playfit.se.members.entity.Address_Entity;

@Repository
public interface Address_Repository extends JpaRepository<Address_Entity, Long> {
}
