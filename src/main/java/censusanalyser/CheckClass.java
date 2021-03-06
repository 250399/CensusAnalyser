package censusanalyser;

import censusanalyser.models.IndiaCensusCSV;
import censusanalyser.models.IndiaStateCode;
import censusanalyser.models.USCensusCSV;

public enum CheckClass {
    INDIA(IndiaCensusCSV.class),
    US(USCensusCSV.class),
    WRONGCLASSNAME(CensusAnalyser.class),
    INDIASTATE(IndiaStateCode.class);

    private Class klass;

    CheckClass(Class klass) {
        this.klass=klass;
    }

    public Class getKlass() {
        return klass;
    }
}
