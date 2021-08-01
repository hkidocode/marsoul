package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Equipment extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id", nullable = false, updatable = false)
    private Integer id;
    private String name;

    public Equipment(String name) {
        this.name = name;
    }

}
