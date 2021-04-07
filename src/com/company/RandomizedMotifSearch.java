package com.company;

import java.util.ArrayList;
import java.util.List;

public class RandomizedMotifSearch {


    public ArrayList<String> randomizedMotifSearch(ArrayList<String> DNAList, int kMer, int numberOfSequence){

        ArrayList<String> motifs = randomSelectKMerMotifs(DNAList, kMer, numberOfSequence);
        ArrayList<String> bestMotifs = deepCopyOfArrayList(motifs);

        while(true){

            double[][] profileMatrix = createProfileMatrix( motifs );

            ArrayList<String> currentMotifs = new ArrayList<>();

            for(int j=0; j < numberOfSequence; j++){

                String motif = profileMatrixHighScoreMotif(profileMatrix, DNAList.get(j), kMer);
                currentMotifs.add(motif);

            }

            if(score(currentMotifs) < score(bestMotifs)){
                bestMotifs = deepCopyOfArrayList(currentMotifs);
                motifs = deepCopyOfArrayList(currentMotifs);
            } else {
                break;
            }

        }

        return bestMotifs;
    }


    private String profileMatrixHighScoreMotif(double[][] profileMatrix, String sequence, int kMer) {

        String Kmer = sequence.substring(0, kMer);
        double score = profileMotifScore(Kmer, profileMatrix);

        int lastStartPoint = sequence.length() - kMer + 1;

        for(int i=1; i < lastStartPoint; i++){

            String currentSequence = sequence.substring(i, i+kMer);

            double currentProfileScore = profileMotifScore(currentSequence, profileMatrix);
            if(currentProfileScore > score){

                Kmer = currentSequence;
                score = currentProfileScore;
            }
        }
        return Kmer;

    }


    private  double profileMotifScore(String sequence, double[][] profileMatrix) {

        double score = 1;

        for(int i=0; i<sequence.length(); i++){

            double currentProfileScore = 0;

            if(sequence.charAt(i) == 'A'){
                currentProfileScore = profileMatrix[0][i];
            }else if(sequence.charAt(i) == 'T'){
                currentProfileScore = profileMatrix[1][i];
            }else if(sequence.charAt(i) == 'G'){
                currentProfileScore = profileMatrix[2][i];
            }else if(sequence.charAt(i) == 'C'){
                currentProfileScore = profileMatrix[3][i];
            }

            score = score * currentProfileScore;

        }

        return score;

    }

    public double score(ArrayList<String> motifList) {

        double score = 0;

        double A, T, G, C;

        for(int i=0; i<motifList.get(0).length(); i++){

                A = 0;
                T = 0;
                G = 0;
                C = 0;

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

            double max = A;
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

    private  double[][] createProfileMatrix(ArrayList<String> motifs) {

        double[][] profileMatrix = new double[4][motifs.get(0).length()];

        double A, T, G, C;


        for(int i=0; i < motifs.get(0).length(); i++){
            double totalSize = motifs.size();

            A = 1;
            T = 1;
            G = 1;
            C = 1;

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

            profileMatrix[0][i] = A/totalSize;
            profileMatrix[1][i] = T/totalSize;
            profileMatrix[2][i] = G/totalSize;
            profileMatrix[3][i] = C/totalSize;

        }
        return profileMatrix;
    }




    private ArrayList<String> deepCopyOfArrayList(ArrayList<String> stringArrayList){

        ArrayList<String> listClone = new ArrayList<>();

        for (String s : stringArrayList) {
            listClone.add(s);
        }

        return listClone;
    }



    private ArrayList<String> randomSelectKMerMotifs(List<String> DNAList, int kMer, int numberOfSequence) {

        ArrayList<String> kMerMotifs = new ArrayList<>();
        int endingIndexOfString = DNAList.get(0).length() - kMer;
        int randomNumber;

        for(int i = 0; i < numberOfSequence; i++){
            randomNumber = (int) (Math.random() * (endingIndexOfString + 1 ));
            String motif = DNAList.get(i).substring(randomNumber, randomNumber + kMer);
            kMerMotifs.add(motif);
        }
        return kMerMotifs;

    }

}
