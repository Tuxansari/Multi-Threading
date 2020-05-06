import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Print Odd-Even alternate till N
 */
public class PrintEvenOddService {

    int maxBound;
    Semaphore evenSemaphore, oddSemaphore;

    public PrintEvenOddService(int n) {
        this.maxBound = n;
        evenSemaphore = new Semaphore(0);
        oddSemaphore = new Semaphore(1);
    }

    public void printEven() throws InterruptedException {
        for (int i=2;i<=maxBound;i+=2) {
            evenSemaphore.acquire();
            System.out.println("Thread-Id : " + Thread.currentThread().getId() + " :  " + i);
            oddSemaphore.release();
        }
    }

    public void printOdd() throws  InterruptedException {
        for (int i=1;i<=maxBound;i+=2) {
            oddSemaphore.acquire();
            System.out.println("Thread-Id : " + Thread.currentThread().getId() + " :  " + i);
            evenSemaphore.release();
        }
    }

    public static void main(String[] args) {
        PrintEvenOddService printEvenOddService = new PrintEvenOddService(25);

        Runnable even = () -> {
            try {
                printEvenOddService.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable odd = () -> {
            try {
                printEvenOddService.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(even);
        service.execute(odd);

        service.shutdown();

    }
}
