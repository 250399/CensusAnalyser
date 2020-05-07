package censusanalyser;

import exceptionclass.CensusAnalyserException;
import models.IndiaCensusCSV;
import models.IndiaStateCode;
import models.USCensusCSV;
import opencsvbuilder.CSVBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String JSON_OUTPUT_FILE="./src/test/resources/Census.json";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    CensusAnalyser censusAnalyser ;

    @Before
    public void init(){
        censusAnalyser = new CensusAnalyser();
    }

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("",e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH,IndiaCensusCSV.class);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenTypeIncorrect_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, WrongClass.class);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid type",e.getMessage());
        }
    }
    @Test
    public void givenIndiaCensusData_WhenSeperator_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid seperator",e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WhenHeaderNotCorrect_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,IndiaCensusCSV.class);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid Header",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,IndiaStateCode.class);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH,IndiaStateCode.class);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WhenTypeIncorrect_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,WrongClass.class);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid type",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WhenSeperator_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,IndiaStateCode.class);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid seperator",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WhenHeaderNotCorrect_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaStateCode.class);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid Header",e.getMessage());
        }
    }

    @Test
    public void getSorted_StateName_InJsonFormat_StartState(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCensusData","state");
        IndiaCensusCSV[] censusCSV=new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        Assert.assertEquals("West Bengal",censusCSV[0].state);
    }

    @Test
    public void getSorted_StateName_InJsonFormat_EndState(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCensusData","state");
        IndiaCensusCSV[] censusCSV=new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh",censusCSV[censusCSV.length-1].state);
    }


    @Test
    public void getSorted_StateCode_InJSONFormat_StartState(){
        censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,IndiaStateCode.class);
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCode","code");
        IndiaStateCode[] indiaStateCodes = new Gson().fromJson(sortedList,IndiaStateCode[].class);
        Assert.assertEquals("WB",indiaStateCodes[0].code);
    }

    @Test
    public void getSorted_StateCode_InJSONFormat_EndState(){
        censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,IndiaStateCode.class);
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCode","code");
        IndiaStateCode[] indiaStateCodes = new Gson().fromJson(sortedList,IndiaStateCode[].class);
        Assert.assertEquals("AD",indiaStateCodes[indiaStateCodes.length-1].code);
    }

    @Test
    public void getSorted_StatePopulation_InJSONFormat_StartState(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCensusData","population");
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        Assert.assertEquals(199812341,indiaCensusCSVS[0].population);
    }

    @Test
    public void getSorted_StatePopulation_InJSONFormat_EndState(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCensusData","population");
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        Assert.assertEquals(607688,indiaCensusCSVS[indiaCensusCSVS.length-1].population);
    }

    @Test
    public void givenFileName_WhenImproper_ThrowsException(){
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
            String sortedList = censusAnalyser.sortGivenFileParameterWise("IandiaStateCensusData","population");
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        }catch (CSVBuilderException e){
            Assert.assertEquals("No such file",e.getMessage());
        }
    }


    @Test
    public void whenFilePathIsProper_writeIntoJsonFile_SortedDensity(){
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
            censusAnalyser.writeSortedOutput_InJSONFormat_IntoAFile("IndiaStateCensusData","densityPerSqKm",JSON_OUTPUT_FILE);
    }

    @Test
    public void whenFilePathIsProper_writeIntoJsonFile_SortedByArea(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
        censusAnalyser.writeSortedOutput_InJSONFormat_IntoAFile("IndiaStateCensusData","areaInSqKm",JSON_OUTPUT_FILE);
    }

    @Test
    public void whenFilePath_IsImproper_ThrowException(){
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
            censusAnalyser.writeSortedOutput_InJSONFormat_IntoAFile("IndiaStateCensusData","population",JSON_OUTPUT_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("No such file",e.getMessage());
        }
    }

    @Test
    public void givenUSCensusCSVFile_ReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,USCensusCSV.class);
            Assert.assertEquals(51,numOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("",e.getMessage());
        }
    }

    @Test
    public void getSorted_ForUSCensus_InJSONFormat_StartState(){
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,USCensusCSV.class);
        String sortedList = censusAnalyser.sortGivenFileParameterWise("USCensusData","population");
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        Assert.assertEquals(37253956,indiaCensusCSVS[0].population);
    }


}
