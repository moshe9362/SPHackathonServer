package hackathon.server.models.db;

import com.google.gson.JsonElement;
import hackathon.server.dal.convertors.GsonJsonElementConverter;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ExerciseType {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Convert(converter = GsonJsonElementConverter.class)
    @Column(name = "propertiesSchema")
    private JsonElement propertiesSchema;

}
