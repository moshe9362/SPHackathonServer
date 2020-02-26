package hackathon.server.logic;

import hackathon.server.dal.DBInserter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBInserterConfig {

    @Bean
    public DBInserter get() {
        return new DBInserter();
    }

}
