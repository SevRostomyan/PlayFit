package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playfit.se.members.entities.OrganizationClubEntity;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationClubEntity, Long> {
}
