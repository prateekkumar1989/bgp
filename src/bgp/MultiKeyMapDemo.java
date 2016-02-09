package bgp;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

class MultiKeyMapDemo {
	  
	  public static void main(String args[]){
	   
	      MultiKeyMap<Integer, Integer> multiKeyMap = new MultiKeyMap();

	      multiKeyMap.put(1, 2, 3);
	      
	      
	      /*multiKeyMap.put("Key 1A","Key 1B","Value 1");
	      multiKeyMap.put("Key 2A","Key 2B","Value 2");
	      multiKeyMap.put("Key 3A","Key 3B","Value 3");
	      
	      MapIterator it = multiKeyMap.mapIterator();

	      while (it.hasNext()) {
	          it.next();
	      
	          MultiKey mk = (MultiKey) it.getKey();
	      
	          System.out.println("Option 1");
	          System.out.println(mk.getKey(0));
	          System.out.println(mk.getKey(1));
	      
	          System.out.println("Option 2");
	          for (Object subkey : mk.getKeys())
	            System.out.println(subkey);
	          
	          System.out.println("Option 3");
	          System.out.println(it.getValue());
	      }*/
	      
	      //System.out.println(multiKeyMap.get(1, 1));
	      
	      Multimap<String, String> outdoorElements = ArrayListMultimap.create();
	      outdoorElements.put("fish", "walleye");
	      outdoorElements.put("fish", "muskellunge");
	      outdoorElements.put("fish", "bass");
	      outdoorElements.put("insect", "ants");
	      outdoorElements.put("insect", "water boatman");
	      outdoorElements.put("insect", "Lord Howe Island stick insect");
	      outdoorElements.put("tree", "oak");
	      outdoorElements.put("tree", "birch");
	      
	      Collection<String> fishies = outdoorElements.get("fish");
	      //System.out.println(fishies.size() + fishies.toString());
	      //System.out.println(outdoorElements.containsValue("walleye"));
	      //System.out.println(outdoorElements.containsKey("tree"));
	      
	      
	      LinkedHashSet densecores = new LinkedHashSet();
	      densecores.add("walleye"); densecores.add("muskellunge"); densecores.add("bass");
	      
	      System.out.println(outdoorElements.containsValue(densecores));
	  }
	}