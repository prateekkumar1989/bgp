/*
 * Prateek Kumar A0123398Y
 */
package bgp;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class PA {

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		
		long starttime = System.currentTimeMillis();
		Task1 task1 = new Task1("files\\INPUT.txt");
		task1.solve(); 
		
		Task2 task2 = new Task2("files\\task1.txt", "files\\task2.txt");
		task2.solve();
		
		Task3 task3 = new Task3("files\\task1.txt", "files\\task3.txt");
		task3.solve();
		
		Task4 task4 = new Task4("files\\task1.txt", "files\\task3.txt");
		task4.solve();
		
		Task5 task5 = new Task5();
		task5.solve();
		
		long endtime = System.currentTimeMillis();
	}

}