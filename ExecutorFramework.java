import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorFramework {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<Integer>>list= new ArrayList<>();
        //Future<Integer> future = executorService.submit(new SumCallable(10));
        for (int i=0; i<100; i++) {
            Future<Integer> future = null;
           // System.out.println(future.get(2, TimeUnit.SECONDS) + Thread.currentThread().getName());
            future = executorService.submit(new SumCallable(i));
            list.add(future);
        }
        for(Future f: list)
        {
            System.out.println(f.get(3, TimeUnit.SECONDS)+Thread.currentThread().getName());
        }

        executorService.shutdown();
        // this is the example for fixed size thread pool
        //ExecutorService executorService= Executors.newCachedThreadPool();
        /*here in cached thread pool we dont have any predefined thread size instead it creates thread by its own
         * according the task requirements and when any is free it assigns the job else creates the new thread
         * and if any thred is sitting idle for more than 60 secs it kills that thread*/


//
//        for (int i=0; i<15; i++)
//        {
//            executorService.submit(new Mytask(i));
//        }
////          executorService.submit(new Mytask());
////        executorService.submit(new Mytask());
////        executorService.submit(new Mytask());
//        executorService.shutdown();
//    }
//}
//class Mytask implements Runnable {
//
//    private int taskid;
//
//    public Mytask(int taskid) {
//        this.taskid = taskid;
//    }
//
//    @Override
//    public void run() {
//        System.out.println("Hi i am executing: " + taskid + " Executed by thread: " + Thread.currentThread().getName());
//    }
    }
}
