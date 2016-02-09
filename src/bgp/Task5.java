/*
 * Prateek Kumar A0123398Y
 */
package bgp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Task5 {
	
	File fout;
	FileOutputStream fos;
	BufferedWriter bw;
	
	public Task5() throws FileNotFoundException {
		fout = new File("files\\task5.txt");
		fos = new FileOutputStream(fout);
		bw = new BufferedWriter(new OutputStreamWriter(fos));
	}
	
	public void solve() throws IOException, InterruptedException {

		int numberOfASes[] = new int[12];
		int highestDegrees[] = new int[12];
		for(int year = 2001; year<=2011; year++) numberOfASes[year-2000] = getNumberOfASes(String.valueOf(year));
		for(int year = 2001; year<=2011; year++) highestDegrees[year-2000] = getHighestDegree(String.valueOf(year));
		for(int year = 2001; year<=2011; year++) { bw.write("Highest degree in year " + year + " : " + highestDegrees[year-2000]); bw.newLine(); }
		for(int year = 2001; year<=2011; year++) { bw.write("Number of ASes in year " + year + " : " + numberOfASes[year-2000]); bw.newLine(); }
		
		for(int year = 2001; year<=2011; year++) {
			Task2 task2 = new Task2("files\\output_" + year + "_task1.txt", "files\\output_" + year + "_task2.txt");
			task2.solve();
			
			Task3 task3 = new Task3("files\\output_" + year + "_task1.txt", "files\\output_" + year + "_task3.txt");
			task3.solve();
		}
		bw.close();
	}

	private int getHighestDegree(String year) throws FileNotFoundException, IOException, InterruptedException {
		
		final int ASMAX = 500000;
		final int MAXPATHS = 1000;
		Integer degree[] = new Integer[ASMAX];
		int u[] = new int[MAXPATHS];
		LinkedHashSet<Integer>[] neighbor = new LinkedHashSet[ASMAX];
		for(int i=0; i<ASMAX; i++) neighbor[i] = new LinkedHashSet<Integer>();
		
		int size = 0;
		try(BufferedReader br = new BufferedReader(new FileReader("files\\output_" + year + "_task1.txt"))) {
		    for(String line; (line = br.readLine()) != null ;) {
		    	if(line.trim().equals("")) continue;
		    	if(line.contains("Number")) continue;
		    	size++;
		    	
		    	int i = 1, n = 0;
		    	Scanner sc = new Scanner(line);
		    	while(sc.hasNextInt()) {
		    		u[i] = sc.nextInt(); i++;
		    	}
		    	n = i-1;
		    	
		    	for(i = 1; i<n; i++) {
	    			neighbor[u[i]].add(u[i+1]);
	    			neighbor[u[i+1]].add(u[i]);
		    	}
		    }
			br.close();
		}
		
		for(int j=0; j<ASMAX; j++) degree[j] = neighbor[j].size(); 
		bw.write("Top 10 ASes with largest degrees in year " + year); bw.newLine();
		Integer degreeCopy[] = new Integer[ASMAX];
		System.arraycopy(degree, 0, degreeCopy, 0, degree.length); 
		Arrays.sort(degreeCopy, Collections.reverseOrder() );		
		for (int j=0; j<10; j++) { 
			bw.write("AS: " + Arrays.asList(degree).indexOf(degreeCopy[j].intValue()) + " Degree: "+ degreeCopy[j].intValue());
			bw.newLine();
		}
		
		return Collections.max(Arrays.asList(degree));
	}

	private int getNumberOfASes(String year) throws FileNotFoundException, IOException, InterruptedException {
		
		System.setOut(new PrintStream(new File("files\\output_" + year + "_task1.txt")));
		double sum = 0;
		int size = 0;
		LinkedHashMap<LinkedHashSet<Integer>, Integer> hm = new LinkedHashMap<LinkedHashSet<Integer>, Integer>(); 
		LinkedHashSet<Integer> ASes = new LinkedHashSet<Integer>();
		
		try(BufferedReader br = new BufferedReader(new FileReader("files\\" + year + ".txt"))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	if(line.trim().equals("")) continue;
		    	if(line.contains("{")) continue;
		    	size++;
		    	
		    	line = line.replace("ASPATH: ", "");
		    	
		    	Scanner sc = new Scanner(line);
		    	LinkedHashSet<Integer> hs = new LinkedHashSet<Integer>();
		    	
		    	while(sc.hasNextInt()) {
		    		//System.out.print(sc.nextInt() + " ");
		    		int as = sc.nextInt();
		    		hs.add( as );
		    		ASes.add(as);
		    	}
		    	
		    	if(hs.size()!=0) hm.put(hs, new Integer(hs.size()));
		    	//System.out.println(line); 
		    }
		}
		
		Set set = hm.entrySet();
    	Iterator itr = set.iterator();
        Integer numOfASes = 0 , numOfASpaths = 0;
        
		while(itr.hasNext()) {
	           Map.Entry map = (Map.Entry)itr.next();
	           
	           LinkedHashSet<Integer> hs = (LinkedHashSet<Integer>) map.getKey();
	           Iterator ihs = hs.iterator();
	           while(ihs.hasNext()) System.out.print(ihs.next() + " ");	           
	           System.out.println();
	           sum += hs.size();
	           numOfASes = new Integer( (int)map.getValue() + numOfASes.intValue() );
	           numOfASpaths = new Integer(numOfASpaths.intValue() + 1); 
	        }
	    	double avgASpathLength = sum / numOfASpaths;
	    	bw.write("Average AS path length in year " + year + ": " + avgASpathLength); bw.newLine();
	        System.out.println("Number of ASes: " + /*numOfASes.intValue()*/ ASes.size());
	        System.out.println("Number of AS paths: " + numOfASpaths.intValue());
			return numOfASes.intValue();
	}

}
