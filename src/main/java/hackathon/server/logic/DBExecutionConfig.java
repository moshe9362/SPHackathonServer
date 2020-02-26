package hackathon.server.logic;

import hackathon.server.dal.DBInserter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBExecutionConfig {

    @Bean
    public DBInserter get() {
        return new DBInserter();
    }

}
