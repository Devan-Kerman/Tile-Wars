package yeet;

import java.util.Random;

import org.cache2k.Cache2kBuilder;
import org.cache2k.IntCache;

public class IO {
	public static void main(String[] args) {
		Cache2kBuilder<Integer, String> builder = new Cache2kBuilder<Integer, String>() {};
		builder.loader(IO::randStrn);
		builder.entryCapacity(10);
		
		IntCache<String> cache = builder.buildForIntKey();
		for(int x = 0; x < 100; x++)
			cache.get(x);
		System.out.println("Cache len: " + cache.asMap().size());
	}
	
	private static final char[] US_EN = "AaBbCcDdEeFfGHIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz".toCharArray();
	private static Random rand = new Random();
	private static String randStrn(int len) {
		char[] strn = new char[len];
		for (int i = 0; i < strn.length; i++) {
			strn[i] = US_EN[rand.nextInt(US_EN.length)];
		}
		return new String(strn);
	}

}
