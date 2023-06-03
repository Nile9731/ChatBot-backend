import java.util.concurrent.Callable;

public class SumCallable implements Callable<Integer> {
    private int input;
    public SumCallable(int input) {
        this.input = input;
    }




    @Override
    public Integer call() throws Exception {
        Thread.sleep(2000);
        int sum=0;
        for (int i=0; i<=input; i++)
        {
            sum+=i;
        }
        return sum;
    }
}
