package censusanalyser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import netscape.javascript.JSObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;
import java.io.Reader;


public class JSONStateCensusdata<E> {

//    public static void addCelcusToJSON(String csvFilePath) {
//        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
//            CsvToBean csvToBeanBuilder = new CsvToBeanBuilder(reader)
//                    .withType(IndiaCensusCSV.class)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .withSeparator(',')
//                    .build();
//
//            Iterator<IndiaCensusCSV> censusIterator = csvToBeanBuilder.iterator();
//            JsonParser parser = new JsonParser();
//            File fs = new File("C:\\Users\\Sagar\\IdeaProjects\\DemoOpenCsv\\src\\test\\resources\\Census.json");
//            JsonObject jobj = new JsonObject();
//
//          while (censusIterator.hasNext()){
//                IndiaCensusCSV celcusData = censusIterator.next();
//
//                Object obj = parser.parse(new FileReader("C:\\Users\\Sagar\\IdeaProjects\\DemoOpenCsv\\src\\test\\resources\\Census.json"));
//                JsonObject jsonObject = (JsonObject)obj ;
//                jobj.addProperty("state",celcusData.state);
//                jobj.addProperty("");
////
////                Gson gson = new Gson();
//                   PrintWriter pw = new PrintWriter(fs);
//                pw.append(gson.toJson(celcusData).toString());
//                pw.flush();
//
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
