package hackathon.server.models.db;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "excel_data")
public class ExcelData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "exercise_record_id")
    private Long exerciseRecordId;

    @Column(name = "time_stamp")
    private Timestamp timestamp;

    @Column(name = "angle")
    private Float angle;

}
