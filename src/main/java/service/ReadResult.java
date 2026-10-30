package service;

import java.util.ArrayList;
import java.util.List;

public class ReadResult {
    List<String> bodies = new ArrayList<>();
    List<String> contentsOfFile = new ArrayList<>();

    public ReadResult(List<String> bodies, List<String> contentsOfFile)
    {}

    public List<String> getBodies()
    {
        return this.bodies;
    }
    public List<String> getContentsOfFile()
    {
        return this.contentsOfFile;
    }

}
