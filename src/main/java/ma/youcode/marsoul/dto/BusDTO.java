package ma.youcode.marsoul.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusDTO {

    private String startCity;
    private String cityDestination;
    private String startAgency;
    private String agencyDestination;
    private Date voyageDate;
    private Time startHour;
    private Time endHour;
    private SocietyDTO society;

}
