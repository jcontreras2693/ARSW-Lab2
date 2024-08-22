package edu.eci.arsw.primefinder;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		PrimeFinder pft = new PrimeFinder();
		pft.findPrimes(0, 30000000, 3);
		boolean running = true;

		long timeStart = System.currentTimeMillis();
		while (running){
			if (pft.threadsAlive()){
				if (System.currentTimeMillis() - timeStart >= 5250) {
					int primesInFiveSeconds = pft.getNumberOfPrimes();
					System.out.println("Prime numbers found in 5 seconds: " + primesInFiveSeconds);
					System.out.println("Keep finding primes? (Enter for Yes): ");

					Scanner scanner	= new Scanner(System.in);
					scanner.nextLine();
					pft.continueFinding();
					timeStart = System.currentTimeMillis();
				}
			}else{
				running = false;
			}

		}
	}

}
