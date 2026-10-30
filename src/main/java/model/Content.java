package model;

public class Content
{
    private final int id;
    private final String extractedTxt;
    private final String extractionMethod;
    private final String processedAt;

    public Content(int id, String extractedTxt, String extractionMethod, String processedAt) {
        this.id = id;
        this.extractedTxt = extractedTxt;
        this.extractionMethod = extractionMethod;
        this.processedAt = processedAt;
    }

    public int getId() {return this.id;}
    public String getTxt() {return this.extractedTxt;}
    public String getMethod() {return this.extractionMethod;}
    public String getDate() {return this.processedAt;}

}
