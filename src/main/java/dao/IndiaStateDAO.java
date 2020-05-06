package dao;

import models.IndiaStateCode;

public class IndiaStateDAO {

    public String name;
    public String code;

     public IndiaStateDAO(IndiaStateCode obj){
        this.code=obj.code;
        this.name=obj.name;

    }
}
