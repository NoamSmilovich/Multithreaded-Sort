import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class Sort_MS<T extends Comparable> extends RecursiveAction implements Multithreaded_Sort {
    static int THRESHOLD;
    private final int begin;
    private final int end;
    private List<T> list;

    public Sort_MS(int begin, int end, List<T> list, int threshold) {
        this.begin = begin;
        this.end = end;
        this.list = list;
        this.THRESHOLD = threshold;
    }
    public Sort_MS(int begin, int end, List<T> list) {
        this.begin = begin;
        this.end = end;
        this.list = list;
    }
    public Sort_MS(){
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

                int mid = (end+begin)/2;

                Sort_MS leftTask = new Sort_MS(begin, mid, list);
                Sort_MS rightTask = new Sort_MS(mid + 1, end, list);

                leftTask.fork();
                rightTask.compute();
                leftTask.join();

                Merge();

            }
        }
    }
    void Merge() {
        int mid = (end+begin)/2;
        List<T> temp = new ArrayList<>();
        int i = begin, j = mid + 1;

        while (i <= mid && j <= end) {
            if (list.get(j).compareTo(list.get(i)) >= 1) {
                temp.add(list.get(i));
                i++;
            } else {
                temp.add(list.get(j));
                j++;
            }
        }
        while (i <= mid) {
            temp.add(list.get(i));
            i++;
        }
        while (j <= end) {
            temp.add(list.get(j));
            j++;
        }
        for (i = begin; i <= end; i++) {
            list.set(i, temp.remove(0));
        }
    }
}
