package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class GibbsSampler {

	 private ArrayList<String> randomSelectKMerMotifs(List<String> DNAList, int kMer, int tSequence) {
		 ArrayList<String> selectedKMers = new ArrayList<String>();
		 Random rand = new Random();
		 int randNumber = rand.nextInt(500 - kMer);
		 for (int i = 0; i < tSequence; i++) {
			String motif = DNAList.get(i).substring(randNumber, randNumber + kMer);
			selectedKMers.add(motif);
		}
		 return selectedKMers;
	 }
	 
	 public ArrayList<String> gibbsSampler(List<String> DNAList, int kMer, int tSequence, int N){
		 ArrayList<String> motifs = randomSelectKMerMotifs(DNAList, kMer, tSequence);
		 ArrayList<String> bestMotifs = deepCopyOfArrayList(motifs);
		 double[][] profile = new double[4][kMer];
		 Random rand = new Random();
		 double score = motifScore(motifs);
		 int countOfUnchangedIterations = 0;
		 for (int j = 0; j < N; j++) {
			 int randNumber = rand.nextInt(tSequence);
			 String removedString = motifs.get(randNumber);
			 motifs.remove(randNumber);
			 profile = profileMatrix(motifs, kMer, tSequence, removedString);
			 String replacedString = probOfRemovedString(profile, randNumber, kMer, DNAList, removedString);
			 motifs.add(randNumber, replacedString);
			 if(motifScore(motifs) < motifScore(bestMotifs)) {
				 bestMotifs = deepCopyOfArrayList(motifs);
			 }else {
				 countOfUnchangedIterations++;
			 }
			 if(countOfUnchangedIterations == 50) {
				 break;
			 }
		}
		 return bestMotifs;
	 }
	 
	 public int motifScore(List<String> motifs) {
		 int score = 0;
		 int numberOfString = motifs.size();
		 int lenOfString = motifs.get(0).length();
		 
		 for(int i = 0 ; i < lenOfString ; i++) {
			 
			 int A = 0;
			 int T = 0;
			 int G = 0;
			 int C = 0;
			 
			 for(int j = 0 ; j < numberOfString ; j++) {
				 if(motifs.get(j).charAt(i) == 'A') {
					 A++;
				 }else if(motifs.get(j).charAt(i) == 'T') {
					 T++;
				 }else if(motifs.get(j).charAt(i) == 'G') {
					 G++;
				 }else if(motifs.get(j).charAt(i) == 'C') {
					 C++;
				 }
			 }
			 
			 int max = Math.max(Math.max(A, C), Math.max(G, T));
			 score += numberOfString - max;
		 }
		 
		 return score;
	 }
	 
	 private double[][] profileMatrix(List<String> motifs, int kMer, int tSequence, String removedString){
		 double[][] profile = new double[4][kMer];
		 double totalString = motifs.size() + 4;
		 int numberOfString = motifs.size();
		 for (int i = 0; i < kMer; i++) {
			double A = 1;
			double T = 1;
			double G = 1;
			double C = 1;
			
			for(int j = 0 ; j < numberOfString ; j++) {
				 if(motifs.get(j).charAt(i) == 'A') {
					 A++;
				 }else if(motifs.get(j).charAt(i) == 'T') {
					 T++;
				 }else if(motifs.get(j).charAt(i) == 'G') {
					 G++;
				 }else if(motifs.get(j).charAt(i) == 'C') {
					 C++;
				 }
			}
			
			profile[0][i] = A / totalString;
			profile[1][i] = T / totalString;
			profile[2][i] = G / totalString;
			profile[3][i] = C / totalString;
			
		}
		 return profile;
	 }

	 private String probOfRemovedString(double[][] profile, int removedLine, int kMer, List<String> DNAList, String removed) {
		 String line = DNAList.get(removedLine);
		 String sequence = line.substring(0, kMer);
		 double score;
		 double bestScore = 0;
		 int firstIndexOfSequence = 0;
		 for(int i = 0 ; i < 500 - kMer ; i++) {
			 sequence = line.substring(i, i + kMer);
			 score = scoreOfSequence(profile, sequence);
			 if(score > bestScore) {
				 bestScore = score;
				 firstIndexOfSequence = i;
			 }
		 }
		 String replacedString = line.substring(firstIndexOfSequence,firstIndexOfSequence + kMer);
		 return replacedString;
	 }
	 
	 private double scoreOfSequence(double[][] profile, String sequence) {
		 double score = 1;
		 int len = sequence.length();
		 for(int i = 0 ; i < len ; i++) {
			 if(sequence.charAt(i) == 'A') {
				 score = score * profile[0][i];
			 }
			 else if(sequence.charAt(i) == 'T') {
				 score = score * profile[1][i];
			 }
			 else if(sequence.charAt(i) == 'G') {
				 score = score * profile[2][i];
			 }
			 else if(sequence.charAt(i) == 'C') {
				 score = score * profile[3][i];
			 }
		 }
		 
		 //System.out.println("score = " + score);
		 return score;
	 }
	 

	    private ArrayList<String> deepCopyOfArrayList(ArrayList<String> stringArrayList){

	        ArrayList<String> listClone = new ArrayList<>();

	        for (String s : stringArrayList) {
	            listClone.add(s);
	        }

	        return listClone;
	    }
	    
	    public void printArrayList(ArrayList<String> motifs) {
	        System.out.println("\n Printout the Motifs arrayList:");
	        for (String motif : motifs) {

	            System.out.print(motif + "\r\n");

	        }

	    }
	    
	    public void printProfile(double[][] profile) {
	    	int len = profile[0].length;
	    	System.out.print("A: ");
	        for(int i = 0 ; i < len ; i++) {
	        	System.out.print(profile[0][i] + " ");
	        }
	        System.out.println();
	        System.out.print("T: ");
	        
	        for(int i = 0 ; i < len ; i++) {
	        	
	        	System.out.print(profile[1][i] + " ");
	        	
	        }
	        System.out.println();
	        System.out.print("G: ");
	        
	        for(int i = 0 ; i < len ; i++) {
	        	
	        	System.out.print(profile[2][i] + " ");
	        	
	        }
	        System.out.println();
	        System.out.print("C: ");
	        for(int i = 0 ; i < len ; i++) {
	        	System.out.print(profile[3][i] + " ");
	        }
	        System.out.println();
	    }
}
