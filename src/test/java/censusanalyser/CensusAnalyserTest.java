package censusanalyser;

import censusanalyser.exceptionclass.CensusAnalyserException;
import censusanalyser.models.IndiaCensusCSV;
import censusanalyser.models.IndiaStateCode;
import censusanalyser.models.USCensusCSV;
import opencsvbuilder.CSVBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
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
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"India");
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
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH,"India");
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenTypeIncorrect_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, "wrongclassname");
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid type",e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSeperator_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"India");
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid seperator",e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WhenHeaderNotCorrect_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,"India");
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid Header",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,"IndiaState");
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
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH,"IndiaState");
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WhenTypeIncorrect_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,"wrongclassname");
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid type",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WhenSeperator_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,"indiastate");
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid seperator",e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WhenHeaderNotCorrect_ThrowsException(){
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"indiastate");
        }catch (CensusAnalyserException e) {
            Assert.assertEquals("Invalid Header",e.getMessage());
        }
    }

    @Test
    public void getSorted_StateName_InJsonFormat_StartState(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"india");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCensusData","state");
        IndiaCensusCSV[] censusCSV=new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        Assert.assertEquals("West Bengal",censusCSV[0].state);
    }

    @Test
    public void getSorted_StateName_InJsonFormat_EndState(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"india");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCensusData","state");
        IndiaCensusCSV[] censusCSV=new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh",censusCSV[censusCSV.length-1].state);
    }

    @Test
    public void getSorted_StateCode_InJSONFormat_StartState(){
        censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,"indiastate");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCode","code");
        IndiaStateCode[] indiaStateCodes = new Gson().fromJson(sortedList,IndiaStateCode[].class);
        Assert.assertEquals("WB",indiaStateCodes[0].code);
    }

    @Test
    public void getSorted_StateCode_InJSONFormat_EndState(){
        censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,"indiastate");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCode","code");
        IndiaStateCode[] indiaStateCodes = new Gson().fromJson(sortedList,IndiaStateCode[].class);
        Assert.assertEquals("AD",indiaStateCodes[indiaStateCodes.length-1].code);
    }

    @Test
    public void getSorted_StatePopulation_InJSONFormat_StartState(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"india");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCensusData","population");
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        Assert.assertEquals(199812341,indiaCensusCSVS[0].population);
    }

    @Test
    public void getSorted_StatePopulation_InJSONFormat_EndState(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"india");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("IndiaStateCensusData","population");
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        Assert.assertEquals(607688,indiaCensusCSVS[indiaCensusCSVS.length-1].population);
    }

    @Test
    public void givenFileName_WhenImproper_ThrowsException(){
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"india");
            String sortedList = censusAnalyser.sortGivenFileParameterWise("IandiaStateCensusData","population");
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
        }catch (CSVBuilderException e){
            Assert.assertEquals("No such file",e.getMessage());
        }
    }


    @Test
    public void whenFilePathIsProper_writeIntoJsonFile_SortedDensity(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"india");
        censusAnalyser.writeSortedOutput_InJSONFormat_IntoAFile("IndiaStateCensusData","densityPerSqKm",JSON_OUTPUT_FILE);
        Assert.assertNotNull(new File(JSON_OUTPUT_FILE));
    }

    @Test
    public void whenFilePathIsProper_writeIntoJsonFile_SortedByArea(){
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"india");
        censusAnalyser.writeSortedOutput_InJSONFormat_IntoAFile("IndiaStateCensusData","areaInSqKm",JSON_OUTPUT_FILE);
        Assert.assertNotNull(new File(JSON_OUTPUT_FILE));
    }

    @Test
    public void whenFilePath_IsImproper_ThrowException(){
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"india");
            censusAnalyser.writeSortedOutput_InJSONFormat_IntoAFile("IndiaStateCensusData","population",JSON_OUTPUT_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("No such file",e.getMessage());
        }
    }

    @Test
    public void givenUSCensusCSVFile_ReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,"US");
            Assert.assertEquals(51,numOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals("",e.getMessage());
        }
    }

    @Test
    public void getSortedPopulation_ForUSCensus_InJSONFormat_StartState(){
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,"US");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("USCensusData","population");
        USCensusCSV[] usCensusCSVS = new Gson().fromJson(sortedList,USCensusCSV[].class);
        Assert.assertEquals(37253956,usCensusCSVS[0].population);
    }

    @Test
    public void getSortedPopulation_ForUSCensus_InJSONFormat_EndState(){
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,"US");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("USCensusData","population");
        USCensusCSV[] usCensusCSVS = new Gson().fromJson(sortedList,USCensusCSV[].class);
        Assert.assertEquals(563626,usCensusCSVS[usCensusCSVS.length-1].population);
    }

    @Test
    public void givenInvalidField_ForUSCensus_ToSortThrowsException(){
        try {
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,"us");
            String sortedList = censusAnalyser.sortGivenFileParameterWise("USCensusData","paopulation");
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortedList,IndiaCensusCSV[].class);
            Assert.assertEquals(563626,indiaCensusCSVS[indiaCensusCSVS.length-1].population);
        }catch (CensusAnalyserException e){
            Assert.assertEquals("No such column to sort",e.getMessage());
        }
    }

    @Test
    public void getSortedArea_ForUSCensus_InJSONFormat_StartState(){
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,"us");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("USCensusData","totalArea");
        USCensusCSV[] usCensusCSVS = new Gson().fromJson(sortedList,USCensusCSV[].class);
        Assert.assertEquals(1723338.01,usCensusCSVS[0].totalArea,0);
    }

    @Test
    public void getSortedArea_ForUSCensus_InJSONFormat_EndState(){
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,"us");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("USCensusData","totalArea");
        USCensusCSV[] usCensusCSVS = new Gson().fromJson(sortedList,USCensusCSV[].class);
        Assert.assertEquals(177.0,usCensusCSVS[usCensusCSVS.length-1].totalArea,0);
    }

    @Test
    public void getSortedDensity_ForUSCensus_InJSONFormat_StartState(){
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,"us");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("USCensusData","populationDensity");
        USCensusCSV[] usCensusCSVS = new Gson().fromJson(sortedList,USCensusCSV[].class);
        Assert.assertEquals(3805.61,usCensusCSVS[0].populationDensity,0);
    }

    @Test
    public void getSortedDensity_ForUSCensus_InJSONFormat_EndState(){
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,"Us");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("USCensusData","populationDensity");
        USCensusCSV[] usCensusCSVS = new Gson().fromJson(sortedList,USCensusCSV[].class);
        Assert.assertEquals(0.46,usCensusCSVS[usCensusCSVS.length-1].populationDensity,0);
    }

    @Test
    public void getSortedDensity_ForUSCensusAndIndia_InJSONFormat_StartState(){
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,"US");
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"india");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("USCensusData","population","IndiaStateCensusData");
        USCensusCSV[] usCensusCSVS = new Gson().fromJson(sortedList,USCensusCSV[].class);
        Assert.assertEquals("Uttar Pradesh",usCensusCSVS[0].state);
    }

    @Test
    public void getSortedDensity_ForUSCensusAndIndia_InJSONFormat_EndState(){
        censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH,"Us");
        censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,"india");
        String sortedList = censusAnalyser.sortGivenFileParameterWise("USCensusData","population","IndiaStateCensusData");
        USCensusCSV[] usCensusCSVS = new Gson().fromJson(sortedList,USCensusCSV[].class);
        Assert.assertEquals("Wyoming",usCensusCSVS[usCensusCSVS.length-1].state);
    }


}
