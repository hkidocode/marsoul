package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "societies")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Society extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "society_id", nullable = false, updatable = false)
    private Integer id;
    private String name;
    @Column(name = "bus_count")
    private Integer busCount;
    @OneToMany(mappedBy = "society")
    private List<Bus> buses = new ArrayList<>();
    @OneToMany(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private List<User> people = new ArrayList<>();

    public Society(String name, List<Bus> buses) {
        this.name = name;
        this.busCount = buses.size();
        this.buses = buses;
    }
}
