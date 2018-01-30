package com.gradle.ML.EssayStatistics;

import com.gradle.ML.OpenNLP.WordSentenceParser;
import com.gradle.ML.WordCorrect.WordCorrect;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Statistics {

    private WordSentenceParser wordSentenceParser;
    private WordCorrect wordCorrect;

    public int getNumberOfCharacters(String essay){
        return essay.length();
    }

    public int getNumberOfWords(String essay) throws IOException{
        return wordSentenceParser.tokenize(essay).size();
    }

    public int getNumberOfSentence(String essay) throws IOException{
        return wordSentenceParser.sentenceDetector(essay).size();
    }

    public List<String> getNumberOfWrongWords(String essay) throws IOException{
        List<String> words= wordSentenceParser.tokenize(essay);
        System.out.println(words.toString());
        List<String> res = new ArrayList<>();
//        return words.stream().map(word -> wordCorrect.isRightWord(word)?0:1).reduce(0, (x, y)-> x+y);
//        return words.stream().map(word -> wordCorrect.isRightWord(word)? "": word ).collect(Collectors.toList());

        for(String w:words){

            System.out.println(w.trim() + " "+ wordCorrect.isRightWord(w.trim().toLowerCase()));

            if(!wordCorrect.isRightWord(w.trim().toLowerCase())){

                res.add(w);
            }

        }
        return res;
    }

}
