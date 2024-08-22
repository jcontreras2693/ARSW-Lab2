package edu.eci.arsw.primefinder;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class PrimeFinder {

    int a, b, numThreads;
    Object lock = new Object();
    ArrayList<PrimeFinderThread> threads = new ArrayList<PrimeFinderThread>();
    ArrayList<Integer> primesFound = new ArrayList<Integer>();

    public List<Integer> findPrimes(int a, int b, int numThreads){
        this.a = a;
        this.b = b;
        this.numThreads = numThreads;
        int start = a;
        int range = b / numThreads;
        int end = range;

        for (int i = 0; i < 3; i++){
            threads.add(new PrimeFinderThread(start, end, lock));
            threads.get(i).start();
            start = end + 1;
            end += range;
        }

        return primesFound;

    }

    public void continueFinding(){
        lock.notifyAll();
    }
}
