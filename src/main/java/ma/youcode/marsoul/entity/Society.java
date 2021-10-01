package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Size(min=3, message="Name : minimum 3 characters")
    private String name;

    @Column(name = "image", nullable = false)
    @NotBlank(message = "Image is required")
    private String image;

    @Column(name = "bus_count", nullable = false)
    @Positive(message="Bus count should be greater than 0")
    private Integer busCount;

    @JsonIgnore
    @OneToMany(mappedBy = "society")
    private List<Bus> buses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(targetEntity = User.class)
    private List<User> people = new ArrayList<>();


    public Society(String name, Integer busCount, String image) {
        this.name = name;
        this.busCount = busCount;
        this.image  = image;
    }

    public Society(Long id, String name, Integer busCount, String image) {
        this.id = id;
        this.name = name;
        this.busCount = busCount;
        this.image = image;
    }

}
