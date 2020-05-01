package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath, Class type, char seperator) throws CensusAnalyserException {
        try {
            if (type != IndiaCensusCSV.class)
                throw new CensusAnalyserException("Invalid Type", CensusAnalyserException.ExceptionType.INVALID_TYPE);
            checkSeperator(seperator);
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(type);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            csvToBeanBuilder.withSeparator(seperator);
            CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();
            Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVIterator;
            int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return numOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Invalid Header",
                    CensusAnalyserException.ExceptionType.INVALID_HEADER);
        }
    }

    public void checkSeperator(char seperator) throws CensusAnalyserException {
        if(seperator!=',')
            throw new CensusAnalyserException("Invalid seperator", CensusAnalyserException.ExceptionType.INVALID_SEPERATOR);
    }

}
