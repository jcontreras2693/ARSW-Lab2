package edu.eci.arsw.primefinder;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	int a, b;
	Object lock;
	
	private List<Integer> primes = new LinkedList<Integer>();

	public PrimeFinderThread(int a, int b, Object lock) {
		super();
		this.a = a;
		this.b = b;
		this.lock = lock;
	}

	public void run(){
		long timeStart = System.currentTimeMillis();
		for (int i=a;i<=b;i++){
			if (System.currentTimeMillis() - timeStart >= 1000) {
				pause();
				timeStart = System.currentTimeMillis();
			}
			if (isPrime(i)){
				primes.add(i);
				System.out.println(i);
			}
		}
	}
	
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	
	private void pause(){
		synchronized (lock){
			try {
				lock.wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	
}
