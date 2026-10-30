package model;

public class Bodies {
    private final int id;
    private final int scanId;
    private final String filename;
    private final String extension;
    private final String path;
    private final long fileSize;
    private final String fileHash;


    public Bodies(int id, int scanId, String filename, String extension, String path, long fileSize, String fileHash)
    {
        this.id = id;
        this.scanId = scanId;
        this.filename = filename;
        this.extension = extension;
        this.path = path;
        this.fileSize = fileSize;
        this.fileHash = fileHash;
    }



    public int getId(){return this.id;}
    public int getScanId(){return this.scanId;}
    public String getFilename(){return this.filename;}
    public String getExtension(){return this.extension;}
    public String getPath(){return this.path;}
    public long getFileSize(){return this.fileSize;}
    public String getFileHash(){return this.fileHash;}

}

