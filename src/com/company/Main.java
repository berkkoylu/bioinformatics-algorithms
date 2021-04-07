package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
            DNAList.add(currentDNAString);
        }

		
        RandomizedMotifSearch randomizedMotifSearch = new RandomizedMotifSearch();
        Map<List<String>, Double> consensusMap = new HashMap<>();
        ArrayList<String> bestMotifs = null;

        for (int i = 0; i <5000 ; i++) {
         bestMotifs = randomizedMotifSearch.randomizedMotifSearch(DNAList, kMer, numberOfDNAStrings);
         consensusMap.put(bestMotifs, randomizedMotifSearch.score(bestMotifs));

        }

        Map<List<String>, Double> consensusMapTemp = sortByValue(consensusMap);
        System.out.println(consensusMapTemp);



//        randomizedMotifSearch.printArrayList(bestMotifs);
//        System.out.println(randomizedMotifSearch.motifsScore(bestMotifs));
//


//		  String test = randomizedMotifSearch.findConsensusString(bestMotifs, kMer,
//		  numberOfDNAStrings); System.out.println();
//		  System.out.print("consensus string = " + test);
		 
//        GibbsSampler gibbsSampler = new GibbsSampler();
//        ArrayList<String> bestMotifs = gibbsSampler.gibbsSampler(DNAList, kMer, numberOfDNAStrings, 50000);
//        gibbsSampler.printArrayList(bestMotifs);

    }

    public static Map<List<String>, Double> sortByValue(Map<List<String>, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<List<String>, Double> > list =
                new LinkedList<Map.Entry<List<String>, Double> >(hm.entrySet());

        // Sort the list
        list.sort(new Comparator<Map.Entry<List<String>, Double>>() {
            public int compare(Map.Entry<List<String>, Double> o1,
                               Map.Entry<List<String>, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<List<String>, Double> temp = new LinkedHashMap<List<String>, Double>();
        for (Map.Entry<List<String>, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }






}
