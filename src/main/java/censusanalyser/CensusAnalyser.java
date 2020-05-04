 package censusanalyser;

import ExceptionClass.CensusAnalyserException;
import Models.IndiaCensusCSV;
import Models.IndiaStateCode;
import OpenCSVBuilder.CSVBuilderException;
import OpenCSVBuilder.CSVBuilderFactory;
import OpenCSVBuilder.ICSVBuilder;
import OpenCSVBuilder.ISortBuilder;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

 public class CensusAnalyser {
    List censusCSVList=null;
    List stateCSVList;

    public int loadIndiaCensusData(String csvFilePath,Class type)  {
        checkType(IndiaCensusCSV.class,type);
        match(csvFilePath);
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

    public int loadIndiaStateCode(String csvFilePath,Class type)  {
        checkType(IndiaStateCode.class,type);
        match(csvFilePath);
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder builder = CSVBuilderFactory.getBuilder();
            stateCSVList = builder.getCSVFileList(reader,type);
            return stateCSVList.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Invalid Header",CensusAnalyserException.ExceptionType.INVALID_HEADER);
        }
    }

     private void match(String path) {
         try {
             String firstLine = new BufferedReader(new FileReader(path)).readLine();
             if(!Pattern.matches("^([a-zA-Z0-9]([ ][a-zA-Z0-9]+)?([,][a-zA-Z0-9]+)?)+$",firstLine))
                 throw new CensusAnalyserException("Invalid seperator", CensusAnalyserException.ExceptionType.INVALID_SEPERATOR);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public void checkType(Class requiredType, Class providedType ){
        if(requiredType.equals(providedType)==false){
            throw new CensusAnalyserException("Invalid type",CensusAnalyserException.ExceptionType.INVALID_TYPE);
        }
    }

//    <E> int getCount(Iterator<E> csvIterator){
//        Iterable<E> csvIterable = () -> csvIterator;
//        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
//        return numOfEnteries;
//    }

    String sortStateParameterWise(String sortBy){
        Comparator<IndiaCensusCSV> comparator;
        switch (sortBy.toLowerCase()){
            case "state":
                comparator = Comparator.comparing(censusCSV->censusCSV.state);
                break;
            case "population":
                comparator = Comparator.comparing(censusCSV -> censusCSV.population);
                break;
            case "area":
                comparator = Comparator.comparing(censusCSV -> censusCSV.areaInSqKm);
                break;
//            case "density":
//                comparator = Comparator.comparing(censusCSV -> censusCSV.densityPerSqKm);
//                break;
            default:
                return "Invalid choice";
        }
        return sortedData(comparator,censusCSVList);
    }


     String sortStateCodeDate(){
         Comparator<IndiaStateCode> comparator = Comparator.comparing(indiaStateCode -> indiaStateCode.code);
         return sortedData(comparator,stateCSVList);
     }

     private String sortedData(Comparator comparator, List CSVList) {
         ISortBuilder iSortBuilder = CSVBuilderFactory.getSortBuilder();
         return iSortBuilder.sortData(comparator,CSVList);
     }

     void writeStatePopulation_InJSONFormat_IntoAFile(String filePath, String sortBy)  {
        File fs = new File(filePath);
         try {
             if(fs.exists()==false){
                 //throw new CSVBuilderException("No such file", CSVBuilderException.ExceptionType.FILE_NOT_FOUND_EXCEPTION);
                 fs.createNewFile();
             }
             FileWriter fw = new FileWriter(fs);
             new Gson().toJson(sortStateParameterWise(sortBy),fw);
             fw.flush();
             fw.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

}

