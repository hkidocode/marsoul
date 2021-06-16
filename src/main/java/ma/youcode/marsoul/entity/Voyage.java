package ma.youcode.marsoul.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voyage")
public class Voyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voyage", nullable = false, updatable = false)
    private Long idVoyage;
    @Column(name = "seat_position")
    private Integer seatPosition;
    private VoyageStatus status;

}
