package main;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Serverside {
	public static void main(String[] args) {
		init();
		Scanner s = new Scanner(System.in);
		Scanner t;
		while (true) {
			t = new Scanner(s.nextLine());
			String temp = t.next();
			if (temp.equals("close")) {
				break;
			} else if (temp.equals("help")) {
				System.out.println("avilable commands:");
				System.out.println("close - closes application");
				System.out.println("help - shows this list");
				System.out.println("render <Xcoord> <Ycoord> - render a specific chunck at specified coordinates");
				System.out.println();
			} else if (temp.equals("render")) {
				try {
					int x = t.nextInt();
					int y = t.nextInt();
					//insert future code here
					System.out.println("sucessfully rendered chunck at (" + x + ", " + y + ")");
				}catch(InputMismatchException e)
				{
					System.out.println("Invalid command, usage : render <Xcord> <Ycord>");
				}catch(NoSuchElementException e)
				{
					System.out.println("Invalid command, usage : render <Xcord> <Ycord>");
				}

				System.out.println();
			} else
				System.out.println("nvalid command, try \"help\" for a list of avilable commands");
		}
		s.close();
	}

	public static void init() {
		PrintStream p = new PrintStream(System.out) {
		    @Override
		    public void println(String x) {
		        super.printf("[%s]:\t%s\n", new SimpleDateFormat("hh:mm:ss a").format(new Date()),x);
		    }
		};
		System.setOut(p);
	}
}
