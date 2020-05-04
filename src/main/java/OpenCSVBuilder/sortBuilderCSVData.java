package OpenCSVBuilder;

import censusanalyser.CensusAnalyserException;
import com.google.gson.Gson;
import com.sun.tools.jdeprscan.CSV;

import java.util.Comparator;
import java.util.List;

public class sortBuilderCSVData implements ISortBuilder {

     public String sortData(Comparator comparator, List CSVList){
        if(CSVList ==null|| CSVList.size()==0)
            throw new CSVBuilderException("Null census file",CSVBuilderException.ExceptionType.FIlE_NULL_EXCEPTION);
        quick(comparator,CSVList,0, CSVList.size()-1);
        String sortedString = new Gson().toJson(CSVList);
        return sortedString;
    }

    private static void quick(Comparator comparator, List csvList, int left, int right) {
         if(left<right){
             int pi=quickSort(comparator,csvList,left,right);
             quick(comparator,csvList,left,pi-1);
             quick(comparator,csvList,pi+1,right);
         }
     }

     private static int quickSort(Comparator comparator, List csvList, int left, int right) {
        int pIndex=left;
        Object pi=csvList.get(right);
        for (int index=left;index<right;index++){
            Object elementAtIndex=csvList.get(index);
            if(comparator.compare(elementAtIndex,pi)>0){
                csvList.set(index,csvList.get(pIndex));
                csvList.set(pIndex,elementAtIndex);
                pIndex++;
            }
        }
         csvList.set(right,csvList.get(pIndex));
         csvList.set(pIndex,pi);
        return pIndex;
     }

}
