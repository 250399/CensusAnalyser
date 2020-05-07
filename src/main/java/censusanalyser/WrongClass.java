package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class WrongClass {
    @CsvBindByName(column = "state")
    int name;
}
