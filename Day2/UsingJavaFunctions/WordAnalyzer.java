package Day2.UsingJavaFunctions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface WordAnalyzer {
    List<Map.Entry<String, Long>> analyze(String filePath, int topN) throws IOException;
}