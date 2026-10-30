public Scanner sc = new Scanner(System.in);

void main() throws IOException {
    app();
}

public void app() throws IOException {
    IO.println("Enter Path: ");
    String path = sc.nextLine();
    FileReader.readTextFiles(path);

}



