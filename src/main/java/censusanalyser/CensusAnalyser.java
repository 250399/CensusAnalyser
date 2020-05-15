 package censusanalyser;

import censusanalyser.exceptionclass.CensusAnalyserException;
import censusanalyser.models.IndiaCensusCSV;
import censusanalyser.models.IndiaStateCode;
import censusanalyser.models.USCensusCSV;
import opencsvbuilder.CSVBuilderException;
import opencsvbuilder.CSVBuilderFactory;
import opencsvbuilder.ISortBuilder;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

 public class CensusAnalyser {
    HashMap<String,List> hmap= new HashMap<>();

    public <E> int loadCensusData(String csvFilePath,String className)  {
        Class type = CheckClass.valueOf(className.toUpperCase()).getKlass();
        checkType(type);
        match(csvFilePath);
        hmap.put(getFileName(csvFilePath),new DataLoader().loadData(csvFilePath,type));
        return hmap.get(getFileName(csvFilePath)).size();
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

