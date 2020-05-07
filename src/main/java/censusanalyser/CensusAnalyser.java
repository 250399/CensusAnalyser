 package censusanalyser;

import dao.IndiaStateDAO;
import dao.USCensusDAO;
import exceptionclass.CensusAnalyserException;
import models.IndiaCensusCSV;
import dao.IndiaCensusDAO;
import models.IndiaStateCode;
import models.USCensusCSV;
import opencsvbuilder.CSVBuilderException;
import opencsvbuilder.CSVBuilderFactory;
import opencsvbuilder.ICSVBuilder;
import opencsvbuilder.ISortBuilder;
import com.google.gson.Gson;

import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    HashMap<String,List> hmap= new HashMap<>();

    public <E> int loadCensusData(String csvFilePath,Class type)  {
        checkType(type);
        match(csvFilePath);
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ArrayList<Object> arr = new ArrayList<>();
            ICSVBuilder builder = CSVBuilderFactory.getBuilder();
            Iterator<E> censusCSVIterator = builder.getCSVFileIterator(reader,type);
            Iterable<E> censusIterable = ()-> censusCSVIterator;
            StreamSupport.stream(censusIterable.spliterator(),false)
                    .forEach(csvCensus -> {
                        if(type.equals(IndiaCensusCSV.class))
                            arr.add(new IndiaCensusDAO((IndiaCensusCSV) csvCensus));
                        else if(type.equals(USCensusCSV.class))
                            arr.add(new USCensusDAO((USCensusCSV) csvCensus));
                        else if(type.equals(IndiaStateCode.class))
                            arr.add(new IndiaStateDAO((IndiaStateCode) csvCensus));
                    });
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

    public void checkType(Class providedType ){
        if(providedType.equals(IndiaStateCode.class)==false&&
        providedType.equals(IndiaCensusCSV.class)==false&&
        providedType.equals(USCensusCSV.class)==false)
            throw new CensusAnalyserException("Invalid type", CensusAnalyserException.ExceptionType.INVALID_TYPE);
    }

    String sortGivenFileParameterWise(String csvFileName, String sortBy,String ...additionalCsvFileName){
        Comparator comparator = Comparator.comparing(censusCSV->{
            try {
                return(Comparable) censusCSV.getClass().getDeclaredField(sortBy).get(censusCSV);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new CensusAnalyserException("No such column to sort", CensusAnalyserException.ExceptionType.NO_SUCH_FIELD);
            }
        });
        try {
//            ArrayList a1 = new ArrayList(Collections.singleton(hmap.get(csvClass)
//            .getClass()
//            .getDeclaredField(sortBy)));
            ArrayList<Object> arr = new ArrayList(hmap.get(csvFileName));
            for(String s : additionalCsvFileName)
                arr.addAll(hmap.get(s));
            return sortedData(comparator, arr);
        }catch (NullPointerException e){
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

