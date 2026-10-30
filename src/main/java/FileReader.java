import model.Bodies;
import model.Content;
import service.ReadResult;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FileReader {

    public static void readTextFiles(String directory) throws IOException {
        List<String> bodies = new ArrayList<>();
        List<String> contentsOfFile = new ArrayList<>();

        AtomicInteger index = new AtomicInteger(); // id
        AtomicInteger index2 = new AtomicInteger(1); // scanId
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd, HH-mm-ss");


        try (Stream<Path> files = Files.list(Path.of(directory))) {
            files.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .forEach(path -> {
                        try {

                            String contents = Files.readString(path).strip();
                            String date = LocalDateTime.now().format(formatter);

                            Bodies body = new Bodies(
                                    index.get(), // id
                                    index2.get(), // scanId
                                    path.getFileName().toString(), // filename
                                    getFileExtension(path), // .txt
                                    path.toString(), // path to file
                                    Files.size(path),  // it's size in bytes
                                    sha256(path) // get files hash
                            );

                            Content content = new Content(
                                    index.get(),
                                    contents,
                                    "parsing",
                                    date
                            );

                            bodies.add(String.valueOf(body));
                            contentsOfFile.add(String.valueOf(content));
                            index.getAndIncrement();
                            index2.getAndIncrement();

                        } catch (IOException e) {
                            throw new RuntimeException("Не удалось прочитать " + path, e);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        ReadResult result = new ReadResult(bodies,contentsOfFile);
    }

    public static String getFileExtension(Path path)
    {
        String filename = path.getFileName().toString();
        int dot = filename.lastIndexOf('.');

        return (dot > 0)
                ? filename.substring(dot + 1)
                : "";
    }

    private static String sha256(Path path)
            throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        try (InputStream input = Files.newInputStream(path)) {
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }

        return HexFormat.of().formatHex(digest.digest());
    }
}