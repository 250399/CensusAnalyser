package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class CSVBuilder <E> implements ICSVBuilder{
    public  Iterator<E> getCSVFileIterator(Reader reader, Class className, char seperator)  {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(className);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        csvToBeanBuilder.withSeparator(seperator);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }
}
