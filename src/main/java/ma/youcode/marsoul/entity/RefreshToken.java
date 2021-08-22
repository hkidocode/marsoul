package ma.youcode.marsoul.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "expired_date", nullable = false)
    private LocalDateTime expireDate;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    public RefreshToken(User user) {
        token = UUID.randomUUID().toString();
        createDate = LocalDateTime.now();
        expireDate = LocalDateTime.now().plusDays(30);
        this.user = user;
    }
}
