package dao;

import models.IndiaCensusCSV;

public class IndiaCensusDAO {
    public String state;
    public int densityPerSqKm;
    public int areaInSqKm;
    public int population;

    public IndiaCensusDAO(IndiaCensusCSV obj) {
        this.state=obj.state;
        this.densityPerSqKm=obj.densityPerSqKm;
        this.areaInSqKm=obj.areaInSqKm;
        this.population=obj.population;
    }
}
