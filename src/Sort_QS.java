import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveAction;


public class Sort_QS<T extends Comparable> extends RecursiveAction implements Multithreaded_Sort{
    static int THRESHOLD;
    private final int begin;
    private final int end;
    private List<T> list;

    public Sort_QS(int begin, int end, List<T> list, int threshold) {
        this.begin = begin;
        this.end = end;
        this.list = list;
        this.THRESHOLD = threshold;
    }
    public Sort_QS(int begin, int end, List<T> list) {
        this.begin = begin;
        this.end = end;
        this.list = list;
    }
    public Sort_QS(){
        this.begin = 0;
        this.end = 2;
        this.list = new ArrayList<>(2);
    }
    public void compute() {
        if (end - begin < THRESHOLD) {
            T min;
            int min_idx;
            for (int i = begin; i <= end; i++) {
                min = list.get(i);
                min_idx = i;
                for (int j = i; j <= end; j++)
                    if (min.compareTo(list.get(j))>=1){
                        min = list.get(j);
                        min_idx = j;
                    }
                Collections.swap(list, i, min_idx);
            }
        } else {
            if (begin < end) {

                int pi = partition();

                Sort_QS leftTask = new Sort_QS(begin, pi -1, list);
                Sort_QS rightTask = new Sort_QS(pi + 1, end, list);

                leftTask.fork();
                rightTask.compute();
                leftTask.join();

            }
        }
    }
    private int partition()
    {
        T pivot = list.get(end);
        int i = (begin-1);
        for (int j=begin; j<end; j++) {
            if (pivot.compareTo(list.get(j))>=0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i+1, end);
        return i+1;
    }
}
