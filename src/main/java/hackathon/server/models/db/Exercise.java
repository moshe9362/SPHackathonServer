package hackathon.server.models.db;

import com.google.gson.JsonElement;
import hackathon.server.dal.convertors.GsonJsonElementConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id" ,insertable = false,updatable = false)
    private ExerciseType exerciseType;

    @Column(name = "start_day_in_protocol")
    private Integer startDayInProtocol;

    @Column(name = "end_day_in_protocol")
    private Integer endDayInProtocol;

    @Column(name = "protocol_id")
    private Long protocolId;

    @Convert(converter = GsonJsonElementConverter.class)
    @Column(name = "properties")
    private JsonElement properties;

    @OneToMany(targetEntity = ExcelData.class, mappedBy = "id",cascade = CascadeType.ALL)
    private List<ExcelData> excelDatas;

}
