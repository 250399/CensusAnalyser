package censusanalyser;

public class CSVBuilderFactory {
    public static ICSVBuilder getBuilder() {
        return new CSVBuilder();
    }
    public static ISort getSortBuilder(){return new sortCSVData();}
}
