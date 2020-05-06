package dao;

import models.USCensusCSV;

public class USCensusDAO {
    public Double totalArea;
    public int population;
    public String state;
    public String stateId;

    public USCensusDAO(USCensusCSV obj) {
        this.population=obj.population;
        this.stateId=obj.stateId;
        this.state=obj.state;
        this.totalArea=obj.totalArea;
    }
}
