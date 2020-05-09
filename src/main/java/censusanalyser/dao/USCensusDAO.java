package censusanalyser.dao;

import censusanalyser.models.USCensusCSV;

public class USCensusDAO {
    public double populationDensity;
    public Double totalArea;
    public int population;
    public String state;
    public String stateId;

    public USCensusDAO(USCensusCSV obj) {
        this.population=obj.population;
        this.stateId=obj.stateId;
        this.state=obj.state;
        this.totalArea=obj.totalArea;
        this.populationDensity=obj.populationDensity;
    }
}
