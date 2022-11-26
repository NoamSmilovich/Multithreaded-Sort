public class Main {
    public static void main(String[] args) {
        UnitTest test = new UnitTest(new Sort_MS<Integer>());
        test.testAll();

        Simple_Profiler sp;// = new Simple_Profiler();

        Simulation<Integer> it;
        int len = 20000;
        int sims_per_res = 12;
        int sims_per_graph = 18;

        int[] thresholds = new int[sims_per_graph];
        for (int i=1;i<=sims_per_graph;i++)
            thresholds[i-1] = (int) Math.pow(2, i);

        double[] graph_set = new double[sims_per_graph];
        long[] mem_set = new long[sims_per_graph];
//        it = new Simulation<Integer>(10, 10000, 10000, new Sort_MS());
//        it.run();

        for(int i=0;i<sims_per_graph;i++){
                sp = new Simple_Profiler();
                it = new Simulation<Integer>(sims_per_res, len, thresholds[i], new Sort_QS());
                graph_set[i] = it.run();
                mem_set[i] = sp.end()[0];
            }

        String[] header ={ "Threshold", "Execution Time (ms)", "Memory Used (kb)" };
        ToCsv writeData = new ToCsv(header, thresholds, graph_set, mem_set,sims_per_graph);

    }
}
