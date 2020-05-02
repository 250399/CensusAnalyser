package censusanalyser;

import com.opencsv.bean.CsvBindByName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class,',');
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH,IndiaCensusCSV.class,',');
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenTypeIncorrect_ThrowsException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaStateCode.class,',');
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid type",e.getMessage());
        }
    }
    @Test
    public void givenIndiaCensusData_WhenSeperator_ThrowsException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class,'.');
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid seperator",e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WhenHeaderNotCorrect_ThrowsException(){
        try {
//            new Refletor().changeValue("wrong");
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,IndiaCensusCSV.class,',');
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid Header",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH,IndiaStateCode.class,',');
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCode(WRONG_CSV_FILE_PATH,IndiaStateCode.class,',');
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WhenTypeIncorrect_ThrowsException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH,IndiaCensusCSV.class,',');
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid type",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WhenSeperator_ThrowsException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH,IndiaStateCode.class,'.');
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid seperator",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WhenHeaderNotCorrect_ThrowsException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class,',');
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid Header",e.getMessage());
        }
    }

//    @Test
//    public void sad(){
//        JSONStateCensusdata jsonStateCensusdata= new JSONStateCensusdata();
//           jsonStateCensusdata.addCelcusToJSON(INDIA_CENSUS_CSV_FILE_PATH);
//    }
}
