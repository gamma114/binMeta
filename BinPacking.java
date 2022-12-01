import java.util.*;


public class BinPacking implements Objective {
    private int n; //number of  elements in array
    private ArrayList<Integer> problem; // the elements to be packed
    private  int capacity;
    private ArrayList<Integer> buffer;


    public BinPacking (ArrayList<Integer> pb,int cap){
        try {
            if ( Collections.max(pb) > cap) throw new IndexOutOfBoundsException("The capacity should be higher or equal to the max element of the array ") ;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        this.problem = pb;
        this.n = pb.size();
        this.capacity = cap;
    }

    @Override
    public String getName() {
        return "BinPacking";
    }

    @Override
    public Data solutionSample() {
        return new Data(this.n,0.5);
    }

    public static BinPacking instance01() {
        ArrayList<Integer> test = new ArrayList<>();
        test.add(15);test.add(6);test.add(9);test.add(12);test.add(14);test.add(3);test.add(2);

        return new BinPacking(test,20);
    }

    @Override
    public double value(Data D) {
        if( D.numberOfBits() != n) {
            throw new IllegalArgumentException("The number of bits is not good in the Data");
        }
        ArrayList<Integer> tmpOne = new ArrayList<>();
        ArrayList<Integer> tmpTwo = new ArrayList<>();;
        Iterator<Integer> it = D.iterator();
        for (int i =0; i<n;i++){
            if (it.next()== 0){
                tmpOne.add(problem.get(i));
            }
            else{
                tmpTwo.add(problem.get(i));
            }
        }

        class Task extends Thread {
            ArrayList<Integer> side = buffer;
            int value;
            int binState = 0;
            @Override
            public void run() {
                this.value = 0;
                for( int a : side)
                {
                   if(binState+a <= capacity)
                   {
                       binState += a;
                   }
                   else{
                       binState = a;
                       value++;
                   }
                }
            }
        }

        buffer = tmpOne;
        Task left = new Task();
        buffer = tmpTwo;
        Task right = new Task();
        left.start();
        right.start();

        return 1+left.value+right.value;
    }


}
