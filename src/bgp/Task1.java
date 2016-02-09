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
import java.util.Scanner;
import java.util.Set;

public class Task1 {
	
	String filelocation;
	
	public Task1(String string) {
		filelocation = new String(string);
	}

	public int solve() throws FileNotFoundException, IOException, InterruptedException {
		
		System.setOut(new PrintStream(new File("files\\task1.txt")));
		
		int size = 0;
		LinkedHashMap<LinkedHashSet<Integer>, Integer> hm = new LinkedHashMap<LinkedHashSet<Integer>, Integer>(); 
		LinkedHashSet<Integer> ASes = new LinkedHashSet<Integer>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(filelocation))) {
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
		    		ASes.add( as );
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
	           
	           numOfASes = new Integer( (int)map.getValue() + numOfASes.intValue() );
	           numOfASpaths = new Integer(numOfASpaths.intValue() + 1); 
	           
	           //Thread.sleep(1000);
	        }
	    	
	        System.out.println("Number of ASes: " + /*numOfASes.intValue()*/ ASes.size());
	        System.out.println("Number of AS paths: " + numOfASpaths.intValue());
			return numOfASes.intValue();
		
	}

}
