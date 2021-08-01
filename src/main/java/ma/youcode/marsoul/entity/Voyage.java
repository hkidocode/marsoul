package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.youcode.marsoul.enums.VoyageStatus;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "voyage")
public class Voyage extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voyage_id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "seat_position")
    private Integer seatPosition;
    private VoyageStatus status;

}
