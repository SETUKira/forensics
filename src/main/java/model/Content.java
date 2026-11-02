package model;

public record Content(

    int id,
    String extractedTxt,
    String extractionMethod,
    String processedAt
){}