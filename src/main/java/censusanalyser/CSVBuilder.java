package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class CSVBuilder <E> implements ICSVBuilder{

//    public  Iterator<E> getCSVFileIterator(Reader reader, Class className)  {
//        return getCsvToBean(reader,className).iterator();
//    }

    @Override
    public List getCSVFileList(Reader reader, Class className) {
        return getCsvToBean(reader,className).parse();
    }

    public CsvToBean getCsvToBean(Reader reader,Class className){
        return  (CsvToBean) new CsvToBeanBuilder<>(reader)
        .withType(className)
        .withIgnoreLeadingWhiteSpace(true)
        .withSeparator(',')
        .build();
    }
}
