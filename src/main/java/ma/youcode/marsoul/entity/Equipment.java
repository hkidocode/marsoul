package ma.youcode.marsoul.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    @NotBlank(message = "Name is required")
    @Size(min=4, message="Name : minimum 4 characters")
    @Column(name = "name", length = 240, nullable = false)
    private String name;

    public Equipment(String name) {
        this.name = name;
    }

}
