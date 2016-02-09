/*
 * Prateek Kumar A0123398Y
 */
package bgp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Task2 {
	
	String filelocation, outputlocation;

	public Task2(String string1, String string2) {
		filelocation = new String(string1);
		outputlocation = new String(string2);
	}

	public void solve() throws FileNotFoundException, IOException {
		
		System.setOut(new PrintStream(new File(outputlocation)));
		int i = 0;
		int size = 0;
		LinkedHashMap hm = new LinkedHashMap(); 
		LinkedHashSet<Integer> ASes = new LinkedHashSet<Integer>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(filelocation))) {
		    for(String line; (line = br.readLine()) != null ; ) {
		    	if(line.trim().equals("")) continue;
		    	if(line.contains("Number")) continue;
		    	size++;
		    	Scanner sc = new Scanner(line);
		    	while(sc.hasNextInt()) {
		    		int as = sc.nextInt();
		    		hm.put(as, 0);
		    		ASes.add(as);
		    		//System.out.println("AS: " + hm.get(as));
		    	}
		    	//System.out.println(line);
		    }
			br.close();
		}
		//System.out.println();
		try(BufferedReader br = new BufferedReader(new FileReader(filelocation))) {
		    for(String line; (line = br.readLine()) != null ; ) {
		    	if(line.trim().equals("")) continue;
		    	if(line.contains("Number")) continue;
		    	
		    	LinkedHashSet hs = new LinkedHashSet();
		    	Scanner sc = new Scanner(line);
		    	
		    	int num = 0;
		    	while(sc.hasNextInt()) {
		    		hs.add(new Integer(sc.next()));
		    		num++;
		    	}
		    	
		    	Iterator ihs = hs.iterator();
		    	Integer first = new Integer( (int) ihs.next() );
		    	int numread = 1;
		    	int degree;

		    	degree = (int) hm.get(first); 
		    	hm.put(first, degree+1);
		    	int AS = 0, last = 0;
		    	
		    	if(ihs.hasNext()) {
			    	while(ihs.hasNext()) {
			    		AS = (int) ihs.next();
			    		last = AS;
			    		degree = (int) hm.get( AS );
			    		hm.put(AS, degree+2);
			    	}
			    	hm.put(last, ((int)hm.get(last)) - 1);
		    	}
		    	else {
		    		degree = (int) hm.get(first);
			    	hm.put(first, degree-1);
		    	}
		    	
		    	//System.out.println( first );
		    }
			br.close();
		}
		
		//System.out.println();
		Set set = hm.entrySet();
    	Iterator itr = set.iterator();
    	int sum = 0;
		while(itr.hasNext()) {
			Map.Entry me = (Map.Entry) itr.next();
			sum = sum + (int)me.getValue();
	        //System.out.print(me.getKey() + ": ");
	        //System.out.println(me.getValue());
	    }
		double average = 1.0d*sum / hm.size();
		//System.out.println();
        
    	for (i = 0; i<10; i++) {
    		set = hm.entrySet();
        	itr = set.iterator();
	    	Map.Entry first = (Map.Entry) itr.next();
	    	int largestAS = (int)first.getKey();
	    	int largestDegree = (int)first.getValue();
	    	
	    	while(itr.hasNext()) {
	    		Map.Entry me = (Map.Entry) itr.next();
		        if ( (int)me.getValue() > largestDegree ) { 
		        	largestAS = (int)me.getKey();
		        	largestDegree = (int)me.getValue();
		        }
		    }
	    	hm.remove(largestAS);
	    	System.out.println(largestAS + " with degree " + largestDegree);
    	}
		System.out.println("Average node degree: " + average);
		
	}

}
