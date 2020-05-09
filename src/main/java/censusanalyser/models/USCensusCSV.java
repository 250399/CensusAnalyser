package censusanalyser.models;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "state")
    public String state;

    @CsvBindByName(column = "StateId")
    public String stateId;

    @CsvBindByName(column = "TotalArea")
    public Double totalArea;

    @CsvBindByName(column = "Population")
    public int population;

    @CsvBindByName(column = "PopulationDensity")
    public double populationDensity;

}
