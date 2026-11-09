import Output.DirectoryFormatter;
import Serializer.JsonSerializer;
import model.Bodies;
import model.Content;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class FileReader {

    public static void readTextFiles(String directory) throws IOException {
        JsonSerializer serializer = new JsonSerializer();
        AtomicInteger index = new AtomicInteger(); // id
        AtomicInteger index2 = new AtomicInteger(1); // scanId
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd, HH-mm-ss");
        DirectoryFormatter dirForm = new DirectoryFormatter(); // init dir formatter


        try (Stream<Path> files = Files.list(Path.of(directory))) { // get list of files in directory

            Path resultsDir = dirForm.createResultsDir(); // create result dir see format in Output/DirectoryFormatter
            files.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt") || path.toString().endsWith(".docx")) // filter only /.txt files
                    .forEach(path -> {
                        try {
                            String date = LocalDateTime.now().format(formatter);
                            switch(getFileExtension(path))
                            {
                                case "docx": {
                                    InputStream iS = Files.newInputStream(path);
                                    XWPFDocument document = new XWPFDocument(iS);
                                    XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document);
                                    String contentsDocX = wordExtractor.getText();
                                    IO.println(serializer.saveArrays(resultsDir,
                                            createBody(index, index2, path),
                                            createContent(index, contentsDocX, "parsing", date)));
                                    // save array into relevant dir in JSON format
                                }
                                    break;

                                case "txt": {
                                    String contentsTxT = Files.readString(path).strip();
                                    IO.println(serializer.saveArrays(resultsDir,
                                            createBody(index, index2, path),
                                            createContent(index, contentsTxT, "parsing", date)));
                                }
                                    break;

                                default:
                                    break;
                            }
                            index.getAndIncrement(); // increment id and scanId
                            index2.getAndIncrement();

                        } catch (IOException e) {
                            throw new RuntimeException("Can't read" + path, e);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

    }


    public static Bodies createBody(AtomicInteger index, AtomicInteger index2, Path path) throws IOException, NoSuchAlgorithmException {
        return new Bodies(
                index.get(), // id
                index2.get(), // scanId
                path.getFileName().toString(), // filename
                getFileExtension(path), // .txt
                path.toString(), // path to file
                Files.size(path),  // it's size in bytes
                sha256(path) // get files hash
        );
    }
    public static Content createContent (AtomicInteger index, String contents, String method, String date)
    {
        return new Content( // init content obj
                index.get(),
                contents,
                method,
                date
        );
    }
    public static String getFileExtension(Path path)// get file extension like ".txt"
    {
        String filename = path.getFileName().toString();
        int dot = filename.lastIndexOf('.');

        return (dot > 0)
                ? filename.substring(dot + 1)
                : "";
    }

    private static String sha256(Path path) // get file hash
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