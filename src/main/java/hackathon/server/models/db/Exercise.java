package hackathon.server.models.db;

import com.google.gson.JsonElement;
import hackathon.server.dal.convertors.GsonJsonElementConverter;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id",insertable = false,updatable = false)
    private ExerciseType exerciseType;

    @Column(name = "startDayInProtocol")
    private Integer startDayInProtocol;

    @Column(name = "endDayInProtocol")
    private Integer endDayInProtocol;

    @Convert(converter = GsonJsonElementConverter.class)
    private JsonElement properties;

}
