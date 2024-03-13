package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import playfit.se.members.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
            SELECT u FROM UserEntity u WHERE u.email = :email AND u.clubEntity.id = :clubId
            """)
    Optional<UserEntity> findByEmailAndClubId(@Param("email") String userEmail, @Param("clubId") Long clubId);

    Optional<UserEntity> findUserByEmail(String email);

    List<UserEntity> findByCreatedAtBeforeAndAccountStatusIsFalse(LocalDateTime createdAt);

}




