package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import util.datamanagement.manager.ChunkManager;
import visualizer.VPanel;

public class Serverside {
	private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss");
	
	public static void main(String[] args) throws IOException {
		cmds();
	}

	public static void cmds() throws IOException {
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
					Visuals.visualize(x, y);
					//insert future code here
					System.out.println("Sucessfully rendered chunk at (" + x + ", " + y + ")");
				}catch(InputMismatchException e)
				{
					System.out.println("Invalid command, usage : render <Xcord> <Ycord>");
				}catch(NoSuchElementException e)
				{
					System.out.println("Invalid command, usage : render <Xcord> <Ycord>");
				}
			} else if (temp.equals("export")) {
				try {
					int x = t.nextInt();
					int y = t.nextInt();
					File f = new File("yeet.png");
					f.delete();
					new VPanel(ChunkManager.getSafe(x, y).data).Export(f);
					Desktop.getDesktop().open(f);
					//insert future code here
					System.out.println("Sucessfully exported chunk at (" + x + ", " + y + ")");
				}catch(InputMismatchException e)
				{
					System.out.println("Invalid command, usage : render <Xcord> <Ycord>");
				}catch(NoSuchElementException e)
				{
					System.out.println("Invalid command, usage : render <Xcord> <Ycord>");
				}

				System.out.println();
			} else if(temp.equals("Exit")) {
				break;
			}
			else
				System.out.println("nvalid command, try \"help\" for a list of avilable commands");
		}
		t.close();
		s.close();
	}
}
