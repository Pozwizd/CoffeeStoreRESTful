package spaceLab.model.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spaceLab.entity.Location;

import java.io.Serializable;


/**
 * Response model for {@link Location}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse implements Serializable {
    Long id;
    String city;
    String latitude;
    String longitude;
    String street;
    String building;
}