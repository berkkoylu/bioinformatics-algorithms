package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RandomizedMotifSearch {


    public ArrayList<String> randomizedMotifSearch(ArrayList<String> DNAList, int kMer, int tSequence){

        ArrayList<String> motifs = randomSelectKMerMotifs(DNAList, kMer, tSequence);
        ArrayList<String> bestMotifs = deepCopyOfArrayList(motifs);
        boolean run = true;

        while(run){

            double[][] profileMatrix = createProMatrix( motifs );

            ArrayList<String> currMotifs = new ArrayList<>();

            for(int j=0; j<tSequence; j++){

                String motif_j = profileMostProbable(profileMatrix, DNAList.get(j), kMer);

                currMotifs.add(motif_j);

            }

            if(motifsScore(currMotifs) < motifsScore(bestMotifs)){
                bestMotifs = deepCopyOfArrayList(currMotifs);
                motifs = deepCopyOfArrayList(currMotifs);

            } else {

                run = false;

            }

        }


        return bestMotifs;
    }


    private String profileMostProbable(double[][] profileMatrix,	String sequence, int k_mer) {

        String Kmer = sequence.substring(0, k_mer);
        double score = profileScore(Kmer, profileMatrix);

        int Len = sequence.length();
        int lastStartPoint = Len - k_mer + 1;

        for(int i=1; i<lastStartPoint; i++){

            String currSeq = sequence.substring(i, i+k_mer);

            double currPro = profileScore(currSeq, profileMatrix);
            if(currPro > score){

                Kmer = currSeq;
                score = currPro;
            }
        }
        return Kmer;

    }


    private  double profileScore(String seq, double[][] profileMatrix) {

        double score = 1;
        int Len = seq.length();

        for(int i=0; i<Len; i++){

            double currPro = 0;

            if(seq.charAt(i) == 'A'){
                currPro = profileMatrix[0][i];
            }else if(seq.charAt(i) == 'T'){
                currPro = profileMatrix[1][i];
            }else if(seq.charAt(i) == 'G'){
                currPro = profileMatrix[2][i];
            }else if(seq.charAt(i) == 'C'){
                currPro = profileMatrix[3][i];
            }

            score = score * currPro;

        }

        return score;

    }

    private  int motifsScore(ArrayList<String> motifList) {

        int score = 0;

        for(int i=0; i<motifList.get(0).length(); i++){

            int A = 0;
            int T = 0;
            int G = 0;
            int C = 0;

            for (String s : motifList) {

                if(s.charAt(i) == 'A'){
                    A++;
                }else if(s.charAt(i) == 'T'){

                    T++;
                }else if(s.charAt(i) == 'G'){
                    G++;
                }else if(s.charAt(i) == 'C'){
                    C++;
                }
            }

            int max = A;
            if( T > max) max = T;
            if( G > max) max = G;
            if( C > max) max = C;
            score = score + motifList.size() - max;

        }

        return score;

    }

    public void printArrayList(ArrayList<String> motifs) {
        System.out.println("\n Printout the Motifs arrayList:");
        for (String motif : motifs) {

            System.out.print(motif + "\r\n");

        }

    }

    private  double[][] createProMatrix(ArrayList<String> motifs) {

        double[][] profile = new double[4][motifs.get(0).length()];

        for(int i=0; i<motifs.get(0).length(); i++){
            double total = motifs.size();
            double A = 1;
            double T = 1;
            double G = 1;
            double C = 1;

            for (String motif : motifs) {

                if(motif.charAt(i) == 'A'){
                    A++;
                }else if(motif.charAt(i) == 'T'){
                    T++;
                }else if(motif.charAt(i) == 'G'){
                    G++;
                }else if(motif.charAt(i) == 'C'){
                    C++;
                }
            }

            profile[0][i] = A/total;
            profile[1][i] = T/total;
            profile[2][i] = G/total;
            profile[3][i] = C/total;

        }

        return profile;

    }//end createProMatrix() method;




    private ArrayList<String> deepCopyOfArrayList(ArrayList<String> stringArrayList){

        ArrayList<String> listClone = new ArrayList<>();

        for (String s : stringArrayList) {
            listClone.add(s);
        }

        return listClone;
    }




    private ArrayList<String> randomSelectKMerMotifs(List<String> DNAList, int kMer, int tSequence) {

        ArrayList<String> kMerMotifs = new ArrayList<>();
        int endingIndexOfString = DNAList.get(0).length() - kMer;

        for(int i = 0; i < tSequence; i++){
            int rand = (int) (Math.random() * endingIndexOfString);
            String motif = DNAList.get(i).substring(rand, rand + kMer);
            kMerMotifs.add(motif);
        }

        return kMerMotifs;

    }

}
