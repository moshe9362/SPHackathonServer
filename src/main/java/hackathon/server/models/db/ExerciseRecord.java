package hackathon.server.models.db;

import com.google.gson.JsonElement;
import hackathon.server.dal.convertors.GsonJsonElementConverter;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class ExerciseRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private long exerciseId;
    private String patientUuid;
    private Date startOfExercise;
    private long endOfExercise;

    @Convert(converter = GsonJsonElementConverter.class)
    private JsonElement extraData;

}
