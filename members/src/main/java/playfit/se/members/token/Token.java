package playfit.se.members.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import playfit.se.members.entities.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokenGenerator")
    @SequenceGenerator(name = "tokenGenerator", sequenceName = "tokenSeq", allocationSize = 1)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    boolean expired;
    boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}
