package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
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
    @Column(name = "start_city")
    private String startCity;
    @Column(name = "city_destination")
    private String cityDestination;
    @Column(name = "start_agency")
    private String startAgency;
    @Column(name = "agency_destination")
    private String agencyDestination;
    @Column(name = "voyage_date")
    private Date voyageDate;
    @Column(name = "start_hour")
    private Time startHour;
    @Column(name = "end_hour")
    private Time endHour;
    @Column(name = "empty_places")
    private Integer emptyPlaces;
    @ManyToOne(targetEntity = Society.class)
    private Society society;
    @OneToMany(targetEntity = Voyage.class)
    private List<Voyage> voyages = new ArrayList<>();
    @OneToMany(targetEntity = Equipment.class)
    private List<Equipment> equipments = new ArrayList<>();

}
