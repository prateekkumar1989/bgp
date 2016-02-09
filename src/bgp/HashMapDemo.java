package bgp;

import java.util.*;

public class HashMapDemo {

   public static void main(String args[]) {
   
      // Create a hash map
      HashMap hm = new HashMap();
      // Put elements to the map
      
      LinkedHashSet hs1 = new LinkedHashSet();
      hs1.add("1"); hs1.add("2"); hs1.add("3"); //hs1.add("10");
      hm.put(hs1, hs1.size());
      
      LinkedHashSet hs2 = new LinkedHashSet();
      hs2.add("3"); hs2.add("2"); hs2.add("1");
      hm.put(hs2, hs2.size());
      
      HashSet hs3 = new HashSet();
      hs3.add("3"); hs3.add("2"); hs3.add("1"); 
      hm.put(hs3, hs3.size());

      // Get a set of the entries
      Set set = hm.entrySet();
      // Get an iterator
      Iterator i = set.iterator();
      // Display elements
      while(i.hasNext()) {
         Map.Entry me = (Map.Entry)i.next();
         System.out.print(me.getKey() + ": ");
         System.out.println(me.getValue());
      }
      System.out.println();
      
      i = hs1.iterator();
      while(i.hasNext()) System.out.println(i.next());
      
   }
}