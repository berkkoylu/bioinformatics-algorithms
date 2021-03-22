package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RandomizedMotifSearch {







    private ArrayList<String> deepCopyOfArrayList(ArrayList<String> stringArrayList){

        ArrayList<String> listClone = new ArrayList<>();

        for (String s : stringArrayList) {
            listClone.add(s);
        }

        return listClone;
    }


    private ArrayList<String> randomizedMotifSearch(ArrayList<String> DNAList, int kMer, int tSequence){

        ArrayList<String> motifs = randomSelectKMerMotifs(DNAList, kMer, tSequence);
        ArrayList<String> bestMotifs = deepCopyOfArrayList(motifs);

        



        return null;
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
