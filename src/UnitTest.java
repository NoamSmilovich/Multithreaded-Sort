import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {
    private Multithreaded_Sort algo;
    private List<Integer> test_list;

    public UnitTest(Multithreaded_Sort algo){
        this.algo = algo;
    }
    @Test
    void testAll(){
        int[] thresholds = {0, 1, 2, 5, 20};
        for (int i=0;i<thresholds.length;i++) {
            testAlreadySorted(thresholds[i]);
            testUnsorted(thresholds[i]);
            testSingleElement(thresholds[i]);
            testEmpty(thresholds[i]);
        }
    }
    @Test
    void testAlreadySorted(int th) {
        Integer[] A = {-32, -32, 32, 32, 32, 32, 32, 32, 34};
        test_list = Arrays.asList(A);
        algo = newAlgoInstance(algo, 9, test_list, th);
        algo.compute();
        boolean t = isSorted(test_list);
        assertEquals(true, t);
    }
    @Test
    void testUnsorted(int th) {
        Integer[] B = {32, 32, 34, 33};
        test_list = Arrays.asList(B);
        algo = newAlgoInstance(algo, 4, test_list, th);
        algo.compute();
        boolean t = isSorted(test_list);
        assertEquals(true, t);
    }
    @Test
    void testSingleElement(int th) {
        Integer[] C = {32};
        test_list = Arrays.asList(C);
        algo = newAlgoInstance(algo, 1, test_list, th);
        algo.compute();
        boolean t = isSorted(test_list);
        assertEquals(true, t);
    }
    @Test
    void testEmpty(int th){
        Integer[] D = {};
        test_list = Arrays.asList(D);
        algo = newAlgoInstance(algo, 0, test_list, th);
        algo.compute();
        boolean t = isSorted(test_list);
        assertEquals(true, t);
    }
    private <T extends Comparable> Multithreaded_Sort newAlgoInstance(Multithreaded_Sort a, int len, List<T> l, int threshold) {
        try {
            return a.getClass()
                    .getDeclaredConstructor(int.class, int.class, List.class, int.class)
                    .newInstance(0, len - 1, l, threshold);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    private <T extends Comparable> boolean isSorted(List<T> l){
        for(int i=0;i<l.size()-1;i++){
            if (l.get(i).compareTo(l.get(i+1))>=1)
                return false;
        }
        return true;
    }
}
