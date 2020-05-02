 package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List censusCSVList=null;


    public int loadIndiaCensusData(String csvFilePath,Class type, char seperator)  {
        checkType(IndiaCensusCSV.class,type);
        checkSeperator(seperator);
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder builder = CSVBuilderFactory.getBuilder();
             censusCSVList = builder.getCSVFileList(reader, type);
            return censusCSVList.size();
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
            Iterator<IndiaStateCode> stateCSVIterator = builder.getCSVFileIterator(reader,type);
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

    String sortStatePopulationWise(){
        if(censusCSVList==null||censusCSVList.size()==0)
            throw new CensusAnalyserException("Null census file",CensusAnalyserException.ExceptionType.CENSUS_FILE_NULL);
        Comparator<IndiaCensusCSV> comparator = Comparator.comparing(censusCSV->censusCSV.state);
        sort(comparator);
        String sortedString = new Gson().toJson(censusCSVList);
        return sortedString;
    }

    private void sort(Comparator<IndiaCensusCSV> comparator) {
        for (int i = 0 ; i < censusCSVList.size();i++){
            for(int j = 0 ; j < censusCSVList.size()-1-i;j++){
//                IndiaCensusCSV census1 = (IndiaCensusCSV) censusCSVList.get(j);
//                IndiaCensusCSV censes2 = (IndiaCensusCSV) censusCSVList.get(j+1);
//                if(comparator.compare(census1,censes2)>0){
//                    censusCSVList.set(j,censes2);
//                    censusCSVList.set(j+1,census1);
                if(((IndiaCensusCSV) censusCSVList.get(j)).state.compareTo(((IndiaCensusCSV) censusCSVList.get(j + 1)).state)>0){
                    IndiaCensusCSV temp= (IndiaCensusCSV) censusCSVList.get(j);
                    censusCSVList.set(j,censusCSVList.get(j+1));
                    censusCSVList.set(j+1,temp);
                }
            }

        }
    }
}

