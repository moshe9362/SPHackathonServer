package hackathon.server.models.db;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Protocol {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "isTemplate")
    private Boolean isTemplate;

}
