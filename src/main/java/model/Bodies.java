package model;

public record Bodies(
    int id,
    int scanId,
    String filename,
    String extension,
    String path,
    long fileSize,
    String fileHash
    ){}

