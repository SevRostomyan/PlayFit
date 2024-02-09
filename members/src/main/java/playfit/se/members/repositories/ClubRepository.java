package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playfit.se.members.entities.ClubEntity;


import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity, Long> {
    Optional<ClubEntity> findByOrgNr(String orgNr);
}
