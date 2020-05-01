package censusanalyser;

import com.opencsv.bean.CsvBindByName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class Refletor {

    public void changeValue(String value){
        Class indianCensusClass = IndiaCensusCSV.class;
        try {
            Constructor constructor = indianCensusClass.getConstructor();
            Object object = constructor.newInstance();
            setValue(indianCensusClass,object,value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setValue(Class classType,Object obj , String  value){
        IndiaCensusCSV object = (IndiaCensusCSV)obj;
        Field[] field = classType.getFields();
        for(Field f : field){
            if(f.isAnnotationPresent(CsvBindByName.class)){
                CsvBindByName csvBindByName = f.getAnnotation(CsvBindByName.class);
                try {
                    f.set(obj,value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                try {
                    f.set(object,csvBindByName.column());
                }catch (Exception e){}
            }
        }

    }
}
