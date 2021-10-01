package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.youcode.marsoul.enums.VoyageStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;

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

    @NotBlank(message = "Seat position is required")
    @Positive(message = "Seat position should be positive")
    @Column(name = "seat_position", nullable = false)
    private Integer seatPosition;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VoyageStatus status;

    @NotBlank(message = "Voyage date is required")
    @Column(name = "voyage_date", nullable = false)
    private Date voyageDate;

    public Voyage(Integer seatPosition, VoyageStatus status, Date voyageDate) {
        this.seatPosition = seatPosition;
        this.status = status;
        this.voyageDate = voyageDate;
    }
}
