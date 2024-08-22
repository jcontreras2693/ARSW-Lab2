package edu.eci.arsw.primefinder;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class PrimeFinder {

    int a, b, numThreads;
    Object lock = new Object();
    ArrayList<PrimeFinderThread> threads = new ArrayList<PrimeFinderThread>();
    private List<Integer> primes = new LinkedList<Integer>();

    public void findPrimes(int a, int b, int numThreads){
        this.a = a;
        this.b = b;
        this.numThreads = numThreads;
        int start = a;
        int range = b / numThreads;
        int end = range;

        for (int i = 0; i < numThreads; i++){
            threads.add(new PrimeFinderThread(start, end, lock));
            threads.get(i).start();
            start = end + 1;
            end += range;
        }

    }

    public void continueFinding(){
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    public List<Integer> getPrimes(){
        for (int i = 0; i < numThreads; i++){
            primes.addAll(threads.get(i).getPrimes());
        }
        return primes;
    }

    public int getNumberOfPrimes(){
        int quantity = 0;
        for (int i = 0; i < numThreads; i++){
            quantity += threads.get(i).getNumberOfPrimes();
        }
        return quantity;
    }

    public boolean threadsAlive(){
        boolean allAlive = false;
        for (int i = 0; i < numThreads; i++){
            allAlive = allAlive || threads.get(i).isAlive();
        }
        return allAlive;
    }
}
