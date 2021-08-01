package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ma.youcode.marsoul.enums.VoyageStatus;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voyages")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Voyage extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voyage_id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "seat_position")
    private Integer seatPosition;
    private VoyageStatus status;

}
