import Output.DirectoryFormatter;
public Scanner sc = new Scanner(System.in);

void main() throws IOException {
    DirectoryFormatter dirForm = new DirectoryFormatter();
    dirForm.createScansDir();
    app();
}

public void app() throws IOException {
    IO.println("Enter Path: "); // /Users/setu20117306/Desktop/testicals
    String path = sc.nextLine();
    FileReader.readTextFiles(path);

}


