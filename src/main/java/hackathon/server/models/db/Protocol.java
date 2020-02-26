package hackathon.server.models.db;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "protocol")
public class Protocol {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_template")
    private Boolean isTemplate;

}
