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

        Scanner readIn = new Scanner(new File("/Users/berkkoylu/Desktop/bioinformatics-algorithms/deneme.txt"));

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

        RandomizedMotifSearch randomizedMotifSearch =  new RandomizedMotifSearch();

        ArrayList<String> bestMotifs =  randomizedMotifSearch.randomizedMotifSearch( DNAList,  kMer, numberOfDNAStrings);
        randomizedMotifSearch.printArrayList(bestMotifs);



    }
}