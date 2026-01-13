package az.baxtiyargil.kafkastreamsdemo.domain.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JsonNodeBlobConverter implements AttributeConverter<JsonNode, byte[]> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public byte[] convertToDatabaseColumn(JsonNode attribute) {
        try {
            return MAPPER.writeValueAsBytes(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(byte[] dbData) {
        try {
            return MAPPER.readTree(dbData);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON in DB", e);
        }
    }
}
