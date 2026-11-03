package Serializer;
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
            .enable(SerializationFeature.INDENT_OUTPUT); // init JSON serializer + pretty print

    public String saveArrays (Path resultsDir,Bodies body, Content content) throws IOException // Serialize arraylist into JSON and put results into generated folder
    {
        if (body == null || content == null) {
            return "failed to save";
        }

            String jsonBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(body);// Serialize obj to json
            String jsonContent = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(content); //same

            Path outputFileBody = resultsDir.resolve("body.json"); // get path to body.json
            Path outputFileContent = resultsDir.resolve("content.json"); // same for content.json

        Files.writeString( // write file  body.json
                outputFileBody,
                jsonBody,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE_NEW
        );
        Files.writeString( // write file content.json
                outputFileContent,
                jsonContent,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE_NEW
        );

            return "saved successfully";


    }
}
