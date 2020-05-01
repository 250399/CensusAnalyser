package censusanalyser;

public class CSVBuilderFactory {
    public static ICSVBuilder getBuilder() {
        return new CSVBuilder();
    }
}
