package censusanalyser;

public class CensusAnalyserException extends RuntimeException {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        INVALID_TYPE,
        INVALID_SEPERATOR,
        INVALID_HEADER
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
