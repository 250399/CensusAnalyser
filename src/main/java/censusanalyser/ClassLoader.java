package censusanalyser;

import censusanalyser.models.IndiaCensusCSV;
import censusanalyser.models.IndiaStateCode;
import censusanalyser.models.USCensusCSV;

public enum classLoader {
    INDIA(IndiaCensusCSV.class),
    US(USCensusCSV.class),
    WRONGCLASSNAME(CensusAnalyser.class),
    INDIASTATE(IndiaStateCode.class);

    private Class klass;

    classLoader(Class klass) {
        this.klass=klass;
    }

    public Class getKlass() {
        return klass;
    }
}
