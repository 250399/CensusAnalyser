 package censusanalyser;

import dao.IndiaStateDAO;
import dao.USCensusDAO;
import exceptionclass.CensusAnalyserException;
import models.IndiaCensusCSV;
import models.IndiaCensusDAO;
import models.IndiaStateCode;
import models.USCensusCSV;
import opencsvbuilder.CSVBuilderException;
import opencsvbuilder.CSVBuilderFactory;
import opencsvbuilder.ICSVBuilder;
import opencsvbuilder.ISortBuilder;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    HashMap<String,List> hmap= new HashMap<>();

    public int loadIndiaCensusData(String csvFilePath,Class ...type)  {
        if (type.length!=0)
            checkType(IndiaCensusCSV.class,type[0]);
        match(csvFilePath);
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ArrayList<IndiaCensusDAO> arr = new ArrayList<>();
            ICSVBuilder builder = CSVBuilderFactory.getBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = builder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> censusIterable = ()-> censusCSVIterator;
            StreamSupport.stream(censusIterable.spliterator(),false)
                    .forEach(csvCensus -> arr.add(new IndiaCensusDAO(csvCensus)));
            hmap.put(getFileName(csvFilePath),arr);
            return hmap.get(getFileName(csvFilePath)).size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Invalid Header",
                    CensusAnalyserException.ExceptionType.INVALID_HEADER);
        }
    }

    public int loadUSCensusData(String csvFilePath,Class ...type)  {
        if (type.length!=0)
            checkType(USCensusCSV.class,type[0]);
        match(csvFilePath);
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ArrayList<USCensusDAO> arr = new ArrayList<>();
            ICSVBuilder builder = CSVBuilderFactory.getBuilder();
            Iterator<USCensusCSV> censusCSVIterator = builder.getCSVFileIterator(reader,USCensusCSV.class);
            Iterable<USCensusCSV> censusIterable = ()-> censusCSVIterator;
            StreamSupport.stream(censusIterable.spliterator(),false)
                    .forEach(csvCensus -> arr.add(new USCensusDAO(csvCensus)));
            hmap.put(getFileName(csvFilePath),arr);
            return hmap.get(getFileName(csvFilePath)).size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Invalid Header",
                    CensusAnalyserException.ExceptionType.INVALID_HEADER);
        }
    }

    public int loadIndiaStateCode(String csvFilePath,Class ...type)  {
        if(type.length!=0)
            checkType(IndiaStateCode.class,type[0]);
        match(csvFilePath);
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ArrayList<IndiaStateDAO> arr = new ArrayList<>();
            ICSVBuilder builder = CSVBuilderFactory.getBuilder();
            Iterator<IndiaStateCode> censusCSVIterator = builder.getCSVFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> censusIterable = ()-> censusCSVIterator;
            StreamSupport.stream(censusIterable.spliterator(),false)
                    .forEach(csvCensus -> arr.add(new IndiaStateDAO(csvCensus)));
            hmap.put(getFileName(csvFilePath),arr);
            return hmap.get(getFileName(csvFilePath)).size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Invalid Header",CensusAnalyserException.ExceptionType.INVALID_HEADER);
        }
    }

    String getFileName(String csvFilePath){
        String fileName = new File(csvFilePath).getName();
        return fileName.substring(0,fileName.length()-4);
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

    String sortGivenFileParameterWise(String csvFileName, String sortBy){
        Comparator comparator = Comparator.comparing(censusCSV->{
            try {

                return(Comparable) censusCSV.getClass().getDeclaredField(sortBy).get(censusCSV);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        });
        try {
            ArrayList arr = new ArrayList(hmap.get(csvFileName));
            return sortedData(comparator, arr);
        }catch (Exception e){
            throw new CSVBuilderException("No such file", CSVBuilderException.ExceptionType.FILE_NOT_FOUND_EXCEPTION);
        }
    }

     private String sortedData(Comparator comparator, List CSVList) {
         ISortBuilder iSortBuilder = CSVBuilderFactory.getSortBuilder();
         return iSortBuilder.sortData(comparator,CSVList);
     }

     void writeSortedOutput_InJSONFormat_IntoAFile(String csvFileName, String sortBy,String newFilePath)  {
        File fs = new File(newFilePath);
         try {
             if(fs.exists()==false){
                 fs.createNewFile();
             }
             FileWriter fw = new FileWriter(fs);
             new Gson().toJson(sortGivenFileParameterWise(csvFileName,sortBy),fw);
             fw.flush();
             fw.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

}

