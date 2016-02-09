/*
 * Prateek Kumar A0123398Y
 */
package bgp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;

public class Task3 {
	
	final int ASMAX = 500000;
	final int MAXPATHS = 1000;
	final double L = 1;
	final double R = 60;
	
	int jmin = 0;
	String filelocation, outputlocation;
	LinkedHashSet<Integer>[] neighbor;
	Integer degree[];
	int u[];
	Map<ArrayList<Integer>, Integer> transit;
	MultiKeyMap<Integer, Integer> mtransit;
	MultiKeyMap<Integer, String> edge;
	MultiKeyMap<Integer, Integer> notpeering;
	
	public Task3(String string1, String string2) {
		filelocation = new String(string1);
		outputlocation = new String(string2);
		degree = new Integer[ASMAX];
		u = new int[MAXPATHS];
		neighbor = new LinkedHashSet[ASMAX];
		mtransit = new MultiKeyMap();
		edge = new MultiKeyMap();
		notpeering = new MultiKeyMap();
		for(int i=0; i<ASMAX; i++) neighbor[i] = new LinkedHashSet<Integer>();
	}
	
	public void phase1_1() throws FileNotFoundException, IOException, InterruptedException {
		
		u = new int[MAXPATHS];
		int size = 0;
		try(BufferedReader br = new BufferedReader(new FileReader(filelocation))) {
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
		//System.out.println();
		for(int j=0; j<ASMAX; j++) degree[j] = neighbor[j].size(); 

		/*Integer degreeCopy[] = new Integer[ASMAX];
		System.arraycopy(degree, 0, degreeCopy, 0, degree.length); 
		Arrays.sort(degreeCopy, Collections.reverseOrder() );		
		for (int j=0; j<10; j++) System.out.println("Task2: "+ Arrays.asList(degree).indexOf(degreeCopy[j].intValue()) + " : "+ degreeCopy[j].intValue());
		System.out.println();*/
}
	
	public void phase2_1() throws FileNotFoundException, IOException, InterruptedException {
		int size = 0;
		u = new int[MAXPATHS];
		try(BufferedReader br = new BufferedReader(new FileReader(filelocation))) {
		    for(String line; (line = br.readLine()) != null;) {
		    	if(line.trim().equals("")) continue;
		    	if(line.contains("Number")) continue;
		    	size++;
		    	
		    	int i = 1, n = 0;
		    	Scanner sc = new Scanner(line);
		    	while(sc.hasNextInt()) {
		    		u[i] = sc.nextInt(); i++;
		    	}
		    	n = i-1;
		    	
		    	Integer degreemax[] = new Integer[n];
		    	for (i=0; i<n; i++) degreemax[i] = degree[u[i+1]]; 
		    	
		    	Integer degmax = Collections.max(Arrays.asList(degreemax));

		    	int j = 0;
		    	Boolean found = false;
		    	
		    	for(j=1; j<=n; j++) {
		    		if( degree[u[j]].intValue() == degmax.intValue() ) { found = true; break; }
		    	}
		    	
		    	/*if(found) { System.out.println("FOUND j = " + j + " degmax = " + degmax.intValue() + " " + degree[u[j]] + " n = " + n); }
		    	else { System.out.println("Not found till j = " + j + " n = " + n); return; }*/
		    	
		    	for(i=1; i<=j-1; i++) {
		    		Integer mtransitcurrent = mtransit.get(u[i], u[i+1]);
		    		if(mtransitcurrent == null) 
		    			mtransit.put(u[i], u[i+1], 1); 
		    		else 
		    			mtransit.put(u[i], u[i+1], mtransitcurrent.intValue() + 1);
		    	}
		    	
		    	for(i=j; i<=n-1; i++) {
		    		Integer mtransitcurrent = mtransit.get(u[i+1], u[i]);
		    		if(mtransitcurrent == null) 
		    			mtransit.put(u[i+1], u[i], 1);
		    		else 
		    			mtransit.put(u[i+1], u[i], mtransitcurrent.intValue() + 1);
		    	}
		    	
		    }
		    br.close();
		}
	}
	
	public void phase3_1() throws FileNotFoundException, IOException, InterruptedException {

		int size = 0;
		u = new int[MAXPATHS];
		try(BufferedReader br = new BufferedReader(new FileReader(filelocation))) {
		    for(String line; (line = br.readLine()) != null;) {
		    	if(line.trim().equals("")) continue;
		    	if(line.contains("Number")) continue;
		    	size++;
		    	
		    	int i = 1, n = 0;
		    	Scanner sc = new Scanner(line);
		    	while(sc.hasNextInt()) {
		    		u[i] = sc.nextInt(); i++;
		    	}
		    	n = i-1;
		    	
		    	for(i=1; i<=n-1; i++) {
		    		
		    		Integer mtransitu1u = mtransit.get(u[i+1], u[i]);
		    		Integer mtransituu1 = mtransit.get(u[i], u[i+1]);
		    		if(mtransituu1 == null) mtransituu1 = 0;
		    		if(mtransitu1u == null) mtransitu1u = 0;
		    		
		    		if( (mtransitu1u.intValue() > L && mtransituu1.intValue() > L)  
		    			|| ( mtransituu1.intValue() <= L && mtransituu1.intValue() > 0
		    			&& mtransitu1u.intValue() <= L && mtransitu1u.intValue() > 0 ) ) 
		    			{ edge.put(u[i], u[i+1], "s2s");
		    			System.out.println(u[i] + " " + u[i+1] + " " + "s2s"); }
		    		else if( mtransitu1u.intValue() > L || mtransituu1.intValue() == 0 )
		    			{ edge.put(u[i], u[i+1], "p2c");
		    			System.out.println(u[i] + " " + u[i+1] + " " + "p2c"); }
		    		else if( mtransituu1.intValue() > L || mtransitu1u.intValue() == 0 )
		    			{ edge.put(u[i], u[i+1], "c2p");
		    			System.out.println(u[i] + " " + u[i+1] + " " + "c2p"); }
		    	}
		    		
		    }
		    br.close();
		}
	}
	
	public void phase2_2() throws FileNotFoundException, IOException, InterruptedException {
		int size = 0;
		u = new int[MAXPATHS];
		
		try(BufferedReader br = new BufferedReader(new FileReader(filelocation))) {
		    for(String line; (line = br.readLine()) != null;) {
		    	if(line.trim().equals("")) continue;
		    	if(line.contains("Number")) continue;
		    	size++;
		    	
		    	int i = 1, n = 0;
		    	Scanner sc = new Scanner(line);
		    	while(sc.hasNextInt()) {
		    		u[i] = sc.nextInt(); i++;
		    	}
		    	n = i-1;
		    	
		    	Integer degreemax[] = new Integer[n];
		    	for (i=0; i<n; i++) degreemax[i] = degree[u[i+1]]; 
		    	
		    	Integer degmax = Collections.max(Arrays.asList(degreemax));

		    	int j = 0;
		    	Boolean found = false;
		    	
		    	for(j=1; j<=n; j++) {
		    		if( degree[u[j]].intValue() == degmax.intValue() ) { found = true; break; }
		    	}
		    	jmin = j;
		    	
		    	//if(found) { System.out.println("FOUND j = " + j + " degmax = " + degmax.intValue() + " " + degree[u[j]] + " n = " + n); }
		    	//else { System.out.println("Not found till j = " + j + " n = " + n); return; }
		    	
		    	for(i=1; i<=j-2; i++) notpeering.put(u[i], u[i+1], 1);
		    	for(i=j+1; i<=n-1; i++) notpeering.put(u[i], u[i+1], 1);
		    	
		    	String edgeuminus1u = edge.get(u[j-1], u[j]);
		    	String edgejj1 = edge.get(u[j], u[j+1]);
		    	if(edgeuminus1u == null ) edgeuminus1u = new String("");
		    	if(edgejj1 == null) edgejj1 = new String("");
		    	
		    	if(!edgeuminus1u.equals("s2s") && !edgejj1.equals("s2s")) {
		    		//if(degree[u[j-1]]==0 || degree[u[j+1]]==0) continue;
		    		
		    		if(degree[u[j-1]].intValue() > degree[u[j+1]].intValue()) notpeering.put(u[j],  u[j+1], 1);
		    		else notpeering.put(u[j-1],  u[j], 1);
		    	}
	
		    }
		    br.close();
		}
	}
		    	
	public void phase3_2() throws FileNotFoundException, IOException, InterruptedException {
		int size = 0;
		u = new int[MAXPATHS];
		
		try(BufferedReader br = new BufferedReader(new FileReader(filelocation))) {
		    for(String line; (line = br.readLine()) != null;) {
		    	if(line.trim().equals("")) continue;
		    	if(line.contains("Number")) continue;
		    	size++;
		    	
		    	int i = 1, n = 0;
		    	Scanner sc = new Scanner(line);
		    	while(sc.hasNextInt()) {
		    		u[i] = sc.nextInt(); i++;
		    	}
		    	n = i-1;
		    	
		    	for(int j=1; j<=n-1; j++) {
		    		
		    		Integer notpeerindjj1 = notpeering.get(u[j], u[j+1]);
		    		Integer notpeeringj1j = notpeering.get(u[j+1], u[j]);
		    		if(notpeerindjj1 == null) notpeerindjj1 = 0;
		    		if(notpeeringj1j == null) notpeeringj1j = 0;
		    		
		    		double ujbyj1 = degree[u[j]].doubleValue() / degree[u[j+1]].doubleValue();
		    		double Rinverse = 1/R;
		    		
		    		if(notpeerindjj1.intValue() != 1 && notpeeringj1j.intValue() != 1 &&
		    			ujbyj1 < R && ujbyj1 < Rinverse ) {
		    			edge.put(u[j], u[j+1], "p2p");
		    			System.out.println(u[j] + " " + u[j+1] + " " + "p2p");
		    		}
		    	}
		    	
		    }
		    br.close();
		}
	}
		
	
	public void solve() throws FileNotFoundException, IOException, InterruptedException {
		
		System.setOut(new PrintStream(new File(outputlocation)));
		phase1_1();
		phase2_1();
		phase3_1();
		
		phase2_2();
		phase3_2();
		
		stripDuplicatesFromFile();
	}
	
	public void stripDuplicatesFromFile() throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader("files\\task3.txt"));
	    Set<String> lines = new LinkedHashSet<String>(1000000); 
	    String line;
	    while ((line = reader.readLine()) != null) {
	        lines.add(line);
	    }
	    reader.close();
	    BufferedWriter writer = new BufferedWriter(new FileWriter("files\\task3_noduplicates.txt"));
	    for (String unique : lines) {
	        writer.write(unique);
	        writer.newLine();
	    }
	    writer.close();
	}

}

