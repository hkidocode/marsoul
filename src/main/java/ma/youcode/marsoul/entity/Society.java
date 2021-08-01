package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "society")
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
    @OneToMany(targetEntity = Person.class)
    @JoinColumn(name = "id_user")
    private List<Person> people = new ArrayList<>();

    public Society(String name, List<Bus> buses) {
        this.name = name;
        this.busCount = buses.size();
        this.buses = buses;
    }
}
