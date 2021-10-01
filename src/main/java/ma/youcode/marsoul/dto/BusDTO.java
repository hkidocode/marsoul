package ma.youcode.marsoul.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.sql.Time;

@Setter
@Getter
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
    private int emptyPlaces;
    private int price;

}
