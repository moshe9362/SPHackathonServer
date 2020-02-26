package hackathon.server.models.db;

import com.google.gson.JsonElement;
import hackathon.server.dal.convertors.GsonJsonElementConverter;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity(name = "exercise_record")
public class ExerciseRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(name = "patient_uuid")
    private String patientUuid;

    @Column(name = "start_of_exercise")
    private Date startOfExercise;

    @Column(name = "end_of_exercise")
    private Date endOfExercise;

    @Convert(converter = GsonJsonElementConverter.class)
    @Column(name = "exercise_data")
    private JsonElement exerciseData;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_id" ,insertable = false,updatable = false)
    private Exercise exercise;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_uuid" ,insertable = false,updatable = false)
    private Patient patient;

}
