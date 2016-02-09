/*
 * Prateek Kumar A0123398Y
 */
package bgp;

import org.apache.commons.collections4.map.MultiKeyMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Task4 { //have to use MultiMap instead

	String task1filelocation, task3filelocation;
	MultiKeyMap<Integer, String> edge;
	Multimap<Integer, Integer> s2s;
	Multimap<Integer, Integer> p2c;
	Multimap<Integer, Integer> c2p;
	Multimap<Integer, Integer> p2p;
	LinkedHashSet hs;
	LinkedHashSet densecores;
	
	public Task4(String string1, String string2) {
		task1filelocation = new String(string1);
		task3filelocation = new String(string2);
		s2s = ArrayListMultimap.create();
		p2c = ArrayListMultimap.create();
		c2p = ArrayListMultimap.create();
		p2p = ArrayListMultimap.create();
		hs = new LinkedHashSet();
		densecores = new LinkedHashSet();
	}

	private void readData() throws FileNotFoundException, IOException, InterruptedException {
		
		int size = 0;
		
		try(BufferedReader br = new BufferedReader(new FileReader(task3filelocation))) {
		    for(String line; (line = br.readLine()) != null;) {
		    	if(line.trim().equals("")) continue;
		    	if(line.contains("Number")) continue;
		    	size++;
		    	
		    	Scanner sc = new Scanner(line);
		    	int AS1 = sc.nextInt();
		    	int AS2 = sc.nextInt();
		    	String relationship = sc.next();
		    	
		    	//System.out.println(AS1 + " " + AS2 + " " + relationship);
		    	
		    	if(relationship.equals("s2s")) { s2s.put(AS1, AS2); s2s.put(AS2, AS1); } //symmetric but not used at all in the paper
		    	if(relationship.equals("p2p")) { p2p.put(AS1, AS2); p2p.put(AS2, AS1); } //symmetric
		    	if(relationship.equals("p2c")) { p2c.put(AS1, AS2); }
		    	if(relationship.equals("c2p")) { c2p.put(AS1, AS2); }
		    }
		    br.close();
		}
		
		try(BufferedReader br = new BufferedReader(new FileReader(task1filelocation))) {
		    for(String line; (line = br.readLine()) != null;) {
		    	if(line.trim().equals("")) continue;
		    	if(line.contains("Number")) continue;
		    	
		    	Scanner sc = new Scanner(line);
		    	
		    	int num = 0;
		    	while(sc.hasNextInt()) {
		    		hs.add(new Integer(sc.next()));
		    		num++;
		    	}
		    }
		    br.close();
		}
		//System.out.println("Size:" + hs.size());
		//Iterator ihs = hs.iterator();
		//while (ihs.hasNext()) System.out.println(ihs.next());
	}
		
	public void solve() throws FileNotFoundException, IOException, InterruptedException {
		
		readData();
		System.setOut(new PrintStream(new File("files\\task4.txt")));
		int size = 0;
		Iterator ihs;
		
		ihs = hs.iterator(); size = 0;
		while (ihs.hasNext()) {
			int AS = (int) ihs.next();
			
			if( !p2p.containsKey(AS) && !p2p.containsValue(AS) && !p2c.containsKey(AS)) {

				//remove from c2p, p2c and p2p
				c2p.removeAll(AS);

				if(p2c.containsValue(AS)) {
					for (Integer provider : p2c.keys()) {
			            if (provider.intValue() == AS) {
			                p2c.remove(provider, AS);
			            }
			        }
				}
				if(p2p.containsValue(AS)) {
					for (Integer peer : p2p.keys()) {
			            if (peer.intValue() == AS) {
			                p2p.remove(peer, AS);
			            }
			        }
				}
				
				size++;
				System.out.println(AS + " stub"); ihs.remove();
			}
		}
		//System.out.println("# stubs: " + size);
		
		ihs = hs.iterator(); size = 0;
		while (ihs.hasNext()) {
			int AS = (int) ihs.next();
			
			if( !p2p.containsKey(AS) && !p2p.containsValue(AS) && !p2c.containsKey(AS)) {

				//remove from c2p, p2c and p2p
				c2p.removeAll(AS);

				if(p2c.containsValue(AS)) {
					for (Integer provider : p2c.keys()) {
			            if (provider.intValue() == AS) {
			                p2c.remove(provider, AS);
			            }
			        }
				}
				if(p2p.containsValue(AS)) {
					for (Integer peer : p2p.keys()) {
			            if (peer.intValue() == AS) {
			                p2p.remove(peer, AS);
			            }
			        }
				}
				
				size++;
				System.out.println(AS + " regional ISP"); ihs.remove();
			}
		}
		//System.out.println("# regional ISPs: " + size);
		
		ihs = hs.iterator(); size = 0;
		while (ihs.hasNext()) {
			int AS = (int) ihs.next();
			
			if(!p2c.containsValue(AS)) {
				size++;
				System.out.println(AS + " dense core");
				densecores.add(AS);
				ihs.remove();
			}
		}
		//System.out.println("# dense cores: " + size);
		
		ihs = hs.iterator(); size = 0;
		while (ihs.hasNext()) {
			int AS = (int) ihs.next();
			Boolean alreadyremoved = false;
			
			Iterator idc = densecores.iterator();
			while(idc.hasNext()) {
				int dc = (int) idc.next();
				if(p2p.containsEntry(AS, dc) || p2p.containsEntry(dc, AS)) {
					System.out.println(AS + " transit core"); size++; 
					ihs.remove(); alreadyremoved = true;
				}
			}
			
			if(p2p.containsKey(AS)) {
				System.out.println(AS + " transit core"); size++;
            	if(!alreadyremoved) { ihs.remove(); alreadyremoved = true; }
			}
			//below necessary?
			if(p2p.containsValue(AS)) {
				for (Integer peer : p2p.keys()) {
		            if (peer.intValue() == AS) {
		            	System.out.println(peer + " transit core"); size++;
		            	if(!alreadyremoved) { ihs.remove(); alreadyremoved = true; }
		            }
		        }
			}
			
		}
		//System.out.println("# transit cores: " + size);
		
		ihs = hs.iterator(); size = 0;
		while (ihs.hasNext()) {
			int AS = (int) ihs.next();
			System.out.println(AS + " outer cores");
			size++;
			ihs.remove();
		}
		//System.out.println("# outer cores: " + size);
		
	}
}
