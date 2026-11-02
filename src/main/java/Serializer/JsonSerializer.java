package Serializer;
import model.*;
import Output.DirectoryFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class JsonSerializer {
    ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT); // init JSON serializer + pretty print

    DirectoryFormatter dirForm = new DirectoryFormatter(); // init dir formatter

    public String saveArrays (List<String> body, List<String> content) throws IOException // Serialize arraylist into JSON and put results into generated folder
    {
        if (body == null || body.isEmpty() || content == null || content.isEmpty()) {
            return "failed to save";
        }

            Path resultsDir = dirForm.createResultsDir(); // create result dir see format in Output/DirectoryFormatter

            String jsonBody = mapper.writeValueAsString(body);// Serialize obj to json
            String jsonContent = mapper.writeValueAsString(content); //same

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
