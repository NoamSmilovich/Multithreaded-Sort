import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation<T extends Comparable> {
    final private int sim_num;
    final private int len;
    private List<List<T>> test_set;
    private Multithreaded_Sort algo;
    private final int threshold;
    private boolean run_done;

    public Simulation(int sim_num, int len, int threshold, Multithreaded_Sort algo){
        this.sim_num = sim_num;
        this.len = len;
        this.algo = algo;
        this.threshold = threshold;
        this.run_done = false;
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        T t;
        test_set= new ArrayList<>();
        for(int i=0;i<sim_num;i++){
            test_set.add(new ArrayList<>());
        }
        for(int i=0;i<len*sim_num;i++){
            t = (T) create_random(rand, true);
            test_set.get(i/len).add(t);
        }
    }
    public double run(){
        if(!run_done) {
            long start, end;
            List<Long> res_set = new ArrayList<>();
            for (int i = 0; i < sim_num; i++) {
                try {
                    algo = algo.getClass()
                            .getDeclaredConstructor(int.class, int.class, List.class, int.class)
                            .newInstance(0, len-1, test_set.get(i), threshold);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                    e.printStackTrace();
                }
                start = System.nanoTime();
                algo.compute();
                end = System.nanoTime();
                System.out.println((end-start)/1000000);
                res_set.add((end-start)/1000000);
            }
            double sum=0;
            for (int i = 0; i < sim_num; i++) {
                sum = sum + (double)res_set.get(i);
            }
            run_done = true;
            System.out.println(test_correctness());
//            System.out.println(test_set.get(0));
            return sum/((double)sim_num);
        } else {
            return -1;
        }
    }
    private T create_random(Random r) {
        return create_random(r, false);
    }
    private T create_random(Random r, boolean is_gaussian){
        if(is_gaussian && this.getClass().getComponentType() == Double.class)
            return (T) (Double) (r.nextGaussian()*r.nextInt());
        if(this.getClass().getComponentType() == Integer.class)
            return (T) (Integer) r.nextInt();
        if(this.getClass().getComponentType() == Float.class)
            return (T) (Float) (r.nextFloat()*r.nextInt());
        if(this.getClass().getComponentType() == Double.class)
            return (T) (Double) (r.nextDouble()*r.nextInt());
        if(this.getClass().getComponentType() == Long.class)
            return (T) (Long) r.nextLong();
        return (T)(Integer) r.nextInt();
    }
    private boolean test_correctness(){
        if(run_done){
            for (int i = 0; i < len-1; i++) {
                if (test_set.get(0).get(i).compareTo(test_set.get(0).get(i+1)) >= 1)
                    return false;
            }
//            for(int i=0;i<sim_num-1;i++) {
//                for (int j = 0; j < len; j++) {
//                    if (test_set.get(i).get(j) != test_set.get(i+1).get(j))
//                        return false;
//                }
//            }
            return true;
        } else {
            return false;
        }
    }
    private void print_test_set(){
        for(int i=0;i<sim_num;i++)
            System.out.println(test_set.get(i));
    }
}
