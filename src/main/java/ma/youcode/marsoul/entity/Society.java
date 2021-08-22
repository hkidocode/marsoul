package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
    private Long id;

    @Column(name = "name", length = 240, nullable = false)
    @NotBlank(message = "Name is required")
    @Size(min=3, message="Name : minimum 3 characters")
    private String name;

    @Column(name = "image", nullable = false)
    @NotBlank(message = "Image is required")
    private String image;

    @Column(name = "bus_count", nullable = false)
    @NotBlank(message = "Bus count is required")
    @Positive(message="Bus count should be greater than 0")
    private Integer busCount;

    @OneToMany(mappedBy = "society")
    private List<Bus> buses = new ArrayList<>();

//    @OneToMany(targetEntity = User.class)
//    @JoinColumn(name = "user_id")
//    private List<User> people = new ArrayList<>();


    public Society(String name, List<Bus> buses) {
        this.name = name;
        this.busCount = buses.size();
        this.image = image;
        this.buses = buses;
    }

    public Society(String name, Integer busCount) {
        this.name = name;
        this.busCount = busCount;
    }

    public Society(Long id, String name, Integer busCount) {
        this.id = id;
        this.name = name;
        this.busCount = busCount;
    }

}
