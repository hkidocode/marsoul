package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "buses")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bus extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id", nullable = false, updatable = false)
    private Integer id;

    @NotBlank(message = "Start city is required")
    @Size(min=3, message="Start city : minimum 3 characters")
    @Column(name = "start_city", length = 20, nullable = false)
    private String startCity;

    @NotBlank(message = "City destination is required")
    @Size(min=3, message="City destination : minimum 3 characters")
    @Column(name = "city_destination", length = 20, nullable = false)
    private String cityDestination;

    @NotBlank(message = "Start agency is required")
    @Size(min=3, message="Start agency : minimum 3 characters")
    @Column(name = "start_agency", length = 480, nullable = false)
    private String startAgency;

    @NotBlank(message = "Agency destination is required")
    @Size(min=3, message="Agency destination : minimum 3 characters")
    @Column(name = "agency_destination", length = 480, nullable = false)
    private String agencyDestination;

    @FutureOrPresent(message = "Voyage date should be today or after")
    @NotBlank(message = "Voyage date is required")
    @Column(name = "voyage_date", nullable = false)
    private Date voyageDate;

    @NotBlank(message = "Start hour is required")
    @Column(name = "start_hour", nullable = false)
    private Time startHour;

    @NotBlank(message = "End hour is required")
    @Column(name = "end_hour", nullable = false)
    private Time endHour;

    @NotBlank(message = "Empty places is required")
    @Column(name = "empty_places")
    private int emptyPlaces;

    @ManyToOne(targetEntity = Society.class)
    @JoinColumn(name = "society_id")
    private Society society;

    @OneToMany(targetEntity = Equipment.class)
    @JoinColumn(name = "equipment_id")
    private List<Equipment> equipments = new ArrayList<>();

    public Bus(String startCity, String cityDestination, String startAgency, String agencyDestination, Date voyageDate, Time startHour, Time endHour, Integer emptyPlaces) {
        this.startCity = startCity;
        this.cityDestination = cityDestination;
        this.startAgency = startAgency;
        this.agencyDestination = agencyDestination;
        this.voyageDate = voyageDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.emptyPlaces = emptyPlaces;
    }

    public Bus(Integer id, String startCity, String cityDestination, String startAgency, String agencyDestination, Date voyageDate, Time startHour, Time endHour, int emptyPlaces) {
        this.id = id;
        this.startCity = startCity;
        this.cityDestination = cityDestination;
        this.startAgency = startAgency;
        this.agencyDestination = agencyDestination;
        this.voyageDate = voyageDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.emptyPlaces = emptyPlaces;
    }

}
