package yeet;

import play.ai.devtech.core.api.testing.Bencher;
import play.ai.devtech.core.api.testing.Benchmark;

public class IO {
	public static void main(String[] args) throws InterruptedException {
		Bencher bencher = new Bencher("test.txt");
		for(int x = 0; x < 10; x++)
			testBench(bencher);
		
	}
	
	public static void testBench(Bencher bencher) throws InterruptedException {
		Benchmark bench = bencher.getBenchmarker();
		bench.start();
		Thread.sleep(200L);
		bench.stop();
		bench.start();
		Thread.sleep(200L);
		bench.stop();
		bench.submit();
	}
	

}
