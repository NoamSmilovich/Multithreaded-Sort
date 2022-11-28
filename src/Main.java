import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        UnitTest test = new UnitTest(new Sort_QS<Integer>());
        test.testAll();

        Simple_Profiler sp;// = new Simple_Profiler();

        Simulation<Long> it;
        int len = 22000;
        int sims_per_res = 6;
        int sims_per_graph = 40;

        int low_th = len/20;
        int high_th = len*2;
        int step = (high_th-low_th)/sims_per_graph;

        int[] thresholds = new int[sims_per_graph];
        for(int i=0;i<sims_per_graph;i++) {
            if(i>3)
                thresholds[i] = low_th + step * (i-4);
            else
                thresholds[i] = 200 * i + 100;
        }

        double[] graph_set = new double[sims_per_graph];
        long[] mem_set = new long[sims_per_graph];

//        double res;
//        it = new Simulation<Integer>(8, 50000, 4000, new Sort_MS());
//        res = it.run();

        for(int i=0;i<sims_per_graph;i++){
//                sp = new Simple_Profiler();
                it = new Simulation<>(sims_per_res, len, thresholds[i], new Sort_MS());
                graph_set[i] = it.run();
//                mem_set[i] = sp.end()[0];
            }

        String[] header ={ "Threshold", "Execution Time (ms)", "Memory Used (kb)" };
        writeDataToCsv(header, thresholds, graph_set, mem_set,sims_per_graph);

    }
    static void writeDataToCsv(String[] header, int[] x, double[] y1, long[] y2, int len){
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
