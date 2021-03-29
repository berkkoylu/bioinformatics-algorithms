package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Implemented k-mer will be AAAAAAAAAA with 4 mutations in each line AAATAACGCA
    //
    //
    //
    // Added after inside txt file in each line


    public static void main(String[] args) throws FileNotFoundException {
	// write your code here

        Scanner readIn = new Scanner(new File("randomgenerateddna.txt"));

//        int kMer = readIn.nextInt();
//        int numberOfDNAStrings = readIn.nextInt();

//        GGTGTTCA
//        TGTGTAAG
//        AGTATACA
//        GGTGCACG
//        GTTGGCCT

        int kMer = 10;
        int numberOfDNAStrings = 10;

        ArrayList<String> DNAList = new ArrayList<String>();

        while(readIn.hasNextLine()){
            String currentDNAString = readIn.nextLine();
            if(currentDNAString.length() > 1) {
                DNAList.add(currentDNAString);
            }

        }

		
//		  RandomizedMotifSearch randomizedMotifSearch = new RandomizedMotifSearch();
//		  
//		  ArrayList<String> bestMotifs = randomizedMotifSearch.randomizedMotifSearch(
//		  DNAList, kMer, numberOfDNAStrings);
//		  randomizedMotifSearch.printArrayList(bestMotifs); String test =
//		  randomizedMotifSearch.findConsensusString(bestMotifs, kMer,
//		  numberOfDNAStrings); System.out.println();
//		  System.out.print("consensus string = " + test);
		 
        GibbsSampler gibbsSampler = new GibbsSampler();
        ArrayList<String> bestMotifs = gibbsSampler.gibbsSampler(DNAList, kMer, numberOfDNAStrings, 50000);
        gibbsSampler.printArrayList(bestMotifs);

    }
}
