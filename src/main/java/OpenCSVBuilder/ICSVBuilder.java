package OpenCSVBuilder;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {
//    public Iterator<E> getCSVFileIterator(Reader reader, Class className);
    public List getCSVFileList(Reader reader, Class className);
}
