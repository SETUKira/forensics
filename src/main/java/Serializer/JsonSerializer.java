package Serializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;



public class JsonSerializer {
    ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT) // init JSON serializer + pretty print
            .disable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);

    public String saveArrays (Path resultsDir,Bodies body, Content content) throws IOException // Serialize arraylist into JSON and put results into generated folder
    {
        if (body == null || content == null) {
            return "failed to save";
        }

        appendToJsonArray(resultsDir.resolve("body.json"),body);
        appendToJsonArray(resultsDir.resolve("content.json"),content);
            return "saved successfully";
    }
    private void appendToJsonArray(Path file, Object value) throws IOException {
        ArrayNode array;

        if (Files.exists(file) && Files.size(file) > 0) {
            JsonNode existing = mapper.readTree(file.toFile());

            if (!existing.isArray()) {
                throw new IOException("expected: " + file);
            }

            array = (ArrayNode) existing;
        } else {
            array = mapper.createArrayNode();
        }

        array.add(mapper.valueToTree(value));

        mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), array);
    }
}
