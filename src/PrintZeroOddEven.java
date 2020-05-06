import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class PrintZeroOddEven {
    int maxBound;
    Semaphore zeroSemaphore,evenSemaphore,oddSemaphore;

    public PrintZeroOddEven(int n) {
        this.maxBound = n;
        zeroSemaphore = new Semaphore(1);
        evenSemaphore = new Semaphore(0);
        oddSemaphore = new Semaphore(0);
    }

    public void printZero() throws InterruptedException {
        for (int i=1;i<=maxBound;i++) {
            zeroSemaphore.acquire();
            System.out.print("0");
            (i%2==0 ? evenSemaphore : oddSemaphore).release();
        }
    }

    public void printEven() throws InterruptedException {
        for (int i=2;i<=maxBound;i+=2) {
            evenSemaphore.acquire();
            System.out.print(i);
            zeroSemaphore.release();
        }
    }

    public void printOdd() throws InterruptedException {
        for (int i=1;i<=maxBound;i+=2) {
            oddSemaphore.acquire();
            System.out.print(i);
            zeroSemaphore.release();
        }
    }

    public static void main (String[] str) {
        PrintZeroOddEven printZeroOddEven = new PrintZeroOddEven(5);
        Runnable zero = () -> {
            try {
                printZeroOddEven.printZero();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable even = () -> {
            try {
                printZeroOddEven.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable odd = () -> {
            try {
                printZeroOddEven.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(3);
        service.submit(zero);
        service.submit(even);
        service.submit(odd);

        service.shutdown();

    }



}
