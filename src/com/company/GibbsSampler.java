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
		 printArrayList(motifs);
		 System.out.println("----------------------");
		 for (int j = 0; j < N; j++) {
			 int randNumber = rand.nextInt(tSequence);
			 String removedString = motifs.get(randNumber);
			 //System.out.println("removed = " + removedString);
			 motifs.remove(randNumber);
			 profile = profileMatrix(motifs, kMer, tSequence, removedString);
			 //printProfile(profile);
			 //System.out.println("----------------------");
			 String replacedString = probOfRemovedString(profile, randNumber, kMer, DNAList, removedString);
			 //System.out.println("replaced = " + replacedString);
			 motifs.add(randNumber, replacedString);
			 //printArrayList(motifs);
			 
			 //int oldScore = motifScore(motifs);
			 //int newScore = motifScore(bestMotifs);
			 //System.out.println(oldScore);
			 //System.out.println(newScore);
			 //System.out.println("----------------");
			 if(motifScore(motifs) < motifScore(bestMotifs)) {
				 bestMotifs = deepCopyOfArrayList(motifs);
			 }
			 
		}
		 return bestMotifs;
	 }
	 
	 private int motifScore(List<String> motifs) {
		 int score = 0;
		 int numberOfString = motifs.size();
		 int lenOfString = motifs.get(0).length();
		 
		 for(int i = 0 ; i < lenOfString ; i++) {
			 
			 int A = 0;
			 int T = 0;
			 int G = 0;
			 int C = 0;
			 
			 for(int j = 0 ; j < numberOfString ; j++) {
				 if(motifs.get(j).charAt(i) == 'a') {
					 A++;
				 }else if(motifs.get(j).charAt(i) == 't') {
					 T++;
				 }else if(motifs.get(j).charAt(i) == 'g') {
					 G++;
				 }else if(motifs.get(j).charAt(i) == 'c') {
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
		 double totalString = motifs.size();
		 for (int i = 0; i < kMer; i++) {
			double A = 0;
			double T = 0;
			double G = 0;
			double C = 0;
			
			for(String motif : motifs) {
				if(motif.charAt(i) == 'a') {
					A++;
				}
				else if(motif.charAt(i) == 't') {
					T++;
				}
				else if(motif.charAt(i) == 'g') {
					G++;
				}
				else if(motif.charAt(i) == 'c') {
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
			 if(sequence.charAt(i) == 'a') {
				 score = score * profile[0][i];
			 }
			 else if(sequence.charAt(i) == 't') {
				 score = score * profile[1][i];
			 }
			 else if(sequence.charAt(i) == 'g') {
				 score = score * profile[2][i];
			 }
			 else if(sequence.charAt(i) == 'c') {
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
