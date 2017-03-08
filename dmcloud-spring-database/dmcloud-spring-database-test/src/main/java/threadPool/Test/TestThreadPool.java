package threadPool.Test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class TestThreadPool {
public static void main(String args[]) throws InterruptedException {
// only two threads
ExecutorService exec = Executors.newFixedThreadPool(100);
for(int index = 0; index < 100; index++) {
final int i = index;	
Runnable run = new Runnable() {
public void run() {
long time = (100-i)*200;

try {
	System.out.println("my name is "  + i);
	 Thread.currentThread().setName("my name is "  + i);
	 System.err.println( Thread.currentThread().getName());
Thread.sleep(time);
} catch (InterruptedException e) {
}
}
};
exec.execute(run);
}
// must shutdown
exec.shutdown();
}
}

