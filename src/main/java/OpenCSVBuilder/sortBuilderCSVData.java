package censusanalyser;

import com.google.gson.Gson;

import java.util.Comparator;
import java.util.List;

public class sortCSVData implements ISort{

     public String sortData(Comparator comparator, List CSVList){
        if(CSVList ==null|| CSVList.size()==0)
            throw new CensusAnalyserException("Null census file",CensusAnalyserException.ExceptionType.CENSUS_FILE_NULL);
        sort(comparator,CSVList);
        String sortedString = new Gson().toJson(CSVList);
        return sortedString;
    }

    private static void sort(Comparator comparator,List CSVList) {
        for (int i = 0 ; i < CSVList.size();i++){
            for(int j = 0 ; j < CSVList.size()-1-i;j++){
                Object census1 =  CSVList.get(j);
                Object censes2 =  CSVList.get(j+1);
                if(comparator.compare(census1,censes2)>0){
                    CSVList.set(j,censes2);
                    CSVList.set(j+1,census1);
                }
            }

        }
    }
}
