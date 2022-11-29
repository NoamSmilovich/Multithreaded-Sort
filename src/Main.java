import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        UnitTest test = new UnitTest(new Sort_MS<Integer>());
        test.testAll();

        int points = 40;
        double[] res = new double[points];
        int [] lens = new int[points];
        long [] sss = new long[points];
        Simulation<Integer> s;
        for(int i=0;i<points;i++){
            lens[i] = 10000*i;
            s = new Simulation<>(10, 10000*i, 100, new Sort_QS());
            res[i] = s.run();
        }
        String[] header2 = {"List Length", "Execution Time (ms)", "Memory Used (kb)"};
        writeDataToCsv(header2, lens, res, sss, points);

//        double[] resArr = {0, 0, 0, 0};
//        Simulation<Integer> s1 = new Simulation<>(1, 1000000, 100000000, new Sort_QS());
//        resArr[0] = s1.run();
//        Simulation<Integer> s2 = new Simulation<>(20, 1000000, 100, new Sort_QS());
//        resArr[1] = s2.run();
//        Simulation<Integer> s3 = new Simulation<>(1, 1000000, 100000000, new Sort_MS());
//        resArr[2] = s3.run();
//        Simulation<Integer> s4 = new Simulation<>(1, 1000000, 100, new Sort_MS());
//        resArr[3] = s4.run();
//
//        System.out.println(resArr);

        if(false) {
            Simulation<Double> it;
            int len = 22000;
            int sims_per_res = 6;
            int sims_per_graph = 40;

            int low_th = len / 20;
            int high_th = len * 2;
            int step = (high_th - low_th) / sims_per_graph;

            int[] thresholds = new int[sims_per_graph];
            for (int i = 0; i < sims_per_graph; i++) {
                if (i > 3)
                    thresholds[i] = low_th + step * (i - 4);
                else
                    thresholds[i] = 200 * i + 100;
            }

            double[] graph_set = new double[sims_per_graph];
            long[] mem_set = new long[sims_per_graph];


            for (int i = 0; i < sims_per_graph; i++) {
                it = new Simulation<>(sims_per_res, len, thresholds[i], new Sort_QS());
                graph_set[i] = it.run();
            }

            String[] header = {"Threshold", "Execution Time (ms)", "Memory Used (kb)"};
            writeDataToCsv(header, thresholds, graph_set, mem_set, sims_per_graph);
        }
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
