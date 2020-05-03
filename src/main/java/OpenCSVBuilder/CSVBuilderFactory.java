package OpenCSVBuilder;

public class CSVBuilderFactory {
    public static ICSVBuilder getBuilder() {
        return new CSVBuilder();
    }
    public static ISortBuilder getSortBuilder(){return new sortBuilderCSVData();}
}
