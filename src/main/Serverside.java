package main;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Serverside {
	public static void main(String[] args) {
		init();
		Scanner s = new Scanner(System.in);
		while (true) {
			Scanner t = new Scanner(s.nextLine());
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
				System.out.println("invalid command, try \"help\" for a list of avilable commands");
		}
	}

	public static void init() {

	}
}
