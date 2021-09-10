package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "app_users")
public class User extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", length = 180, nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", length = 180, nullable = false)
    private String lastName;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp="^(\\+212|0)([567])(\\d[\\-_/]*){8}$", message="Phone should be valid")
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "image", nullable = false)
    private String image;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate = LocalDateTime.now();

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @NotBlank(message = "Password is required")
    @Pattern(regexp="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$", message="Your password must be : more than 8 chars, at least one number and at least one special character")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @Column(name = "activated", nullable = false)
    private boolean activated = true;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_buses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id"))
    private List<Bus> buses = new ArrayList<>();

    public User(String firstName, String lastName, String phone, String email, String password, Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(Long id, String firstName, String lastName, String phone, String email, String password, Collection<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(Long id, String firstName, String lastName, String phone, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
}
