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
    public int loadIndiaCensusData(String csvFilePath,Class type, char seperator)  {
        checkType(IndiaCensusCSV.class,type);
        checkSeperator(seperator);
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder builder = CSVBuilderFactory.getBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = builder.getCSVFileIterator(reader, type ,seperator);
            return getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Invalid Header",
                    CensusAnalyserException.ExceptionType.INVALID_HEADER);
        }
    }

    public int loadIndiaStateCode(String csvFilePath,Class type,char seperator)  {
        checkType(IndiaStateCode.class,type);
        checkSeperator(seperator);
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder builder = CSVBuilderFactory.getBuilder();
            Iterator<IndiaStateCode> stateCSVIterator = builder.getCSVFileIterator(reader,type,seperator);
            return getCount(stateCSVIterator);
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Invalid Header",CensusAnalyserException.ExceptionType.INVALID_HEADER);
        }
    }

    public void checkSeperator(char seperator) {
        if(seperator!=',')
            throw new CensusAnalyserException("Invalid seperator", CensusAnalyserException.ExceptionType.INVALID_SEPERATOR);
    }

    public void checkType(Class requiredType, Class providedType ){
        if(requiredType.equals(providedType)==false){
            throw new CensusAnalyserException("Invalid type",CensusAnalyserException.ExceptionType.INVALID_TYPE);
        }
    }

    <E> int getCount(Iterator<E> csvIterator){
        Iterable<E> csvIterable = () -> csvIterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEnteries;
    }
}
