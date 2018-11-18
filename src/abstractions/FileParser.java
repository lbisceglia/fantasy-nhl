package abstractions;

public abstract class FileParser {
    public static final String savePath = "C:/Users/Lorenzo Bisceglia/Google Drive/1 - School/1 - BCS/CPSC 210/Project/projectw1_team29/src";
    protected WebPageParser webPageParser;

    public FileParser() {
        webPageParser = new WebPageParser();
    }


}
