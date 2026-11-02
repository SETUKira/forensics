package Output;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//create scans folder in root folder
//create 2026-06-12T13-46-02V0.01alpha folder in scans
//result of computation put inside folder 2026-06-12T13-46-02V0.01alpha


public class DirectoryFormatter {
    private static final Path projectRoot = Path.of("").toAbsolutePath(); // get path to root folder
    private static final Path scans_dir = projectRoot.resolve("scans"); // get path to scans folder

    DateTimeFormatter dateYear =
            DateTimeFormatter.ofPattern("yyyy-MM-dd"); // - 2026-06-12
    DateTimeFormatter dateTime =
            DateTimeFormatter.ofPattern("HH-mm-ss"); // - 13-46-02


    public void createScansDir() throws IOException {
        Files.createDirectories(scans_dir);
    }


    public Path createResultsDir() throws IOException {
        String timestamp = LocalDateTime.now().format(dateYear)
                + "T"
                + LocalDateTime.now().format(dateTime);

        Path resultsDir = scans_dir.resolve(timestamp + "V0.01alpha");
        return Files.createDirectory(resultsDir);

    }
}
