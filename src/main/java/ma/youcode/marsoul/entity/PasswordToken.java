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
@Table(name = "password_tokens")
public class PasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name="token", nullable = false)
    private String token;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "expired_date", nullable = false)
    private LocalDateTime expireDate;

    @Column(name = "confirmed_date")
    private LocalDateTime confirmDate;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    public PasswordToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.createDate = LocalDateTime.now();
        this.expireDate = LocalDateTime.now().plusMinutes(60);
        this.user = user;
    }


}
