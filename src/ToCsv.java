import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ToCsv {

    public ToCsv(String[] header, int[] x, double[] y1, long[] y2, int len) {

        File file = new File("res.csv");
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(header);

            for (int i = 0; i < len; i++) {
                String[] line = {String.valueOf(x[i]), String.valueOf(y1[i]), String.valueOf(y2[i])};
                writer.writeNext(line);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}



