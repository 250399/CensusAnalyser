package models;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {

    @CsvBindByName(column = "State Name" , required = true)
    public String name;

    @CsvBindByName(column = "StateCode" ,  required = true)
    public String code;
}
