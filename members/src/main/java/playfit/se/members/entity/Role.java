package playfit.se.members.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Role {
    @Id
    private int id;
    private String roleType;
    @OneToOne
    private Organisation_Club organisationClub;
}
