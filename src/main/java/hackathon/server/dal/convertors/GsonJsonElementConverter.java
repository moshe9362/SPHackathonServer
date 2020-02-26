package hackathon.server.dal.convertors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.AttributeConverter;

public class GsonJsonElementConverter implements AttributeConverter<JsonElement, String> {

    private Gson gson;

    public GsonJsonElementConverter() {
        this.gson = new GsonBuilder().create();
    }

    @Override
    public String convertToDatabaseColumn(JsonElement attribute) {
        return gson.toJson(attribute);
    }

    @Override
    public JsonElement convertToEntityAttribute(String dbData) {
        return new JsonParser().parse(dbData);
    }
}
