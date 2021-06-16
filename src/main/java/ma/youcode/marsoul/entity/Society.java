package ma.youcode.marsoul.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "society")
public class Society {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_society", nullable = false, updatable = false)
    private Integer idBusSociety;
    private String name;
    @Column(name = "bus_count")
    private Integer busCount;
    @OneToMany(mappedBy = "society")
    private List<Bus> buses = new ArrayList<>();
    @OneToMany(targetEntity = Equipment.class)
    private List<Equipment> equipments = new ArrayList<>();
}
