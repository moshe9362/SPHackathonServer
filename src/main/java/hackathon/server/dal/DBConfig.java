package hackathon.server.dal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db.properties")
public class DBConfig {

    @Bean
    public Gson dbGson() {
        return new GsonBuilder().create();
    }

}
