package censusanalyser;

import models.IndiaCensusCSV;
import models.IndiaStateCode;
import models.USCensusCSV;

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
