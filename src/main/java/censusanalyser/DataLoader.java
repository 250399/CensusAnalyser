package censusanalyser;

import censusanalyser.dao.IndiaCensusDAO;
import censusanalyser.dao.IndiaStateDAO;
import censusanalyser.dao.USCensusDAO;
import censusanalyser.exceptionclass.CensusAnalyserException;
import censusanalyser.models.IndiaCensusCSV;
import censusanalyser.models.IndiaStateCode;
import censusanalyser.models.USCensusCSV;
import opencsvbuilder.CSVBuilderException;
import opencsvbuilder.CSVBuilderFactory;
import opencsvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class DataLoader {
    public <E> List loadData(String csvFilePath,Class className) {
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ArrayList<Object> arr = new ArrayList<>();
            ICSVBuilder builder = CSVBuilderFactory.getBuilder();
            Iterator<E> censusCSVIterator = builder.getCSVFileIterator(reader,className);
            Iterable<E> censusIterable = ()-> censusCSVIterator;
            StreamSupport.stream(censusIterable.spliterator(),false)
                    .forEach(csvCensus -> {
                        if(className.equals(IndiaCensusCSV.class))
                            arr.add(new IndiaCensusDAO((IndiaCensusCSV) csvCensus));
                        else if(className.equals(USCensusCSV.class))
                            arr.add(new USCensusDAO((USCensusCSV) csvCensus));
                        else if(className.equals(IndiaStateCode.class))
                            arr.add(new IndiaStateDAO((IndiaStateCode) csvCensus));
                    });
            return arr;
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Invalid Header",
                    CensusAnalyserException.ExceptionType.INVALID_HEADER);
        }
    }
}
