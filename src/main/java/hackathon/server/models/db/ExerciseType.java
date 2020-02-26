package hackathon.server.models.db;

import com.google.gson.JsonElement;
import hackathon.server.dal.convertors.GsonJsonElementConverter;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "exercise_type")
public class ExerciseType {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Convert(converter = GsonJsonElementConverter.class)
    @Column(name = "properties_schema")
    private JsonElement propertiesSchema;

}
