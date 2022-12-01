import java.util.*;

public class Coverage implements Objective{
	protected int n; // number of sets in sets 
	protected ArrayList<HashSet<Integer>> sets; //Array of sets of int 
		
	public Coverage(ArrayList<HashSet<Integer>> Values) {
		try {
			
			if (Values == null) throw new Exception ("The values should be set");
			if (Values.size() < 1) throw new Exception ("There should be at least one set in the values");

		}
		 catch (Exception e)
	      {
	         e.printStackTrace();
	         System.exit(1);
	      }
		this.sets = Values;
		this.n = sets.size();
	}
	
	public static Coverage instance01()
	{
		ArrayList<HashSet<Integer>> q;
		q = new ArrayList<HashSet<Integer>>();
		HashSet<Integer> a,b,c,d;
		a = new HashSet<Integer>();
		a.add(0); a.add(7);
		a.add(3);
		b = new HashSet<Integer>();
		b.add(6);b.add(3); b.add(1);
		c = new HashSet<Integer>();
		c.add(4);c.add(2);
		d = new HashSet<Integer>();
		d.add(6);d.add(7);d.add(2);
		q.add(a);q.add(b);q.add(c);q.add(d);
		//create a problem that is formed as [{0,7,3},{6,3,1},{4,2},{6,7,2}]
		return new Coverage(q);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Coverage";
	}

	@Override
	public Data solutionSample() {
		// Each bit of data sets to 1 would be a set selected for the coverage 
		return new Data(this.n,0.7);
	}
	
	public HashSet<Integer> getUnion (ArrayList<HashSet<Integer>> q) {
		HashSet<Integer>  union = new HashSet<Integer>();
		for (HashSet<Integer> set : q)
			{union.addAll(set);}
		return union;	
	}
	
	@Override
	public double value(Data D) {
		 try
	      {
	         if (D.numberOfBits() != n)
	           throw new Exception("Impossible to evaluate Coverage objective: number of bits in Data object differs from expected value");
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	         System.exit(1);
	      }
		 
		// check if the solution is good, negative answer would be the elements of the sets that aren't covered, positive answer would be the number of sets that are used in the solution to cover all.
		// The closest positive to 0 is the greatest value;
		Iterator<Integer> it = D.iterator();
		HashSet<Integer> covered = new HashSet<Integer>();
		int value = 0;
		for(int i=0; i< this.n; i++)
		{
			int j = it.next();
			if (j == 1) {
				covered.addAll(sets.get(i));
				value++;
				}
		}



		if (covered.containsAll(this.getUnion(sets))) 
		{
			
			return value;
		}
		
		return -value;
	}
	
	
	public static void main(String [] args)
	{
		  Random R = new Random();
	      int numberOfElement = R.nextInt(10);
	      ArrayList<HashSet<Integer>> Test = new ArrayList<HashSet<Integer>>(numberOfElement);
	      ArrayList<Integer> numberOfElementInSets = new ArrayList<Integer>();
	      for (int i = 0;i<numberOfElement;i++) {
	    	  numberOfElementInSets.add(R.nextInt(4));
	      }
	      
	      for(int i = 0;i<numberOfElement;i++)
	      {
	    	  int boucle = numberOfElementInSets.get(i);
	    	  Test.add(new HashSet<Integer>(boucle));
	    	     		  for (int j=0;j<boucle;j++)
	    	     		  {
	    	     			  
	    	     			  Test.get(i).add(R.nextInt(10));
	    	     		  }	    	     		  
	      }
	      for (HashSet<Integer> a :Test) {
	    	  for(Integer b : a) 
	    	  {
	    		  System.out.print(b);
	    	  }
	    	  System.out.print("\n");
	      }
	      Coverage ceci = new Coverage(Test);
	      
	      System.out.println(ceci.value(ceci.solutionSample()));
	}
}
