package com.gradle.ML.EssayStatistics;

import com.gradle.ML.OpenNLP.WordSentenceParser;
import com.gradle.ML.WordCorrect.WordCorrect;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;

@AllArgsConstructor
public class Statistics {

    private String essay;
    private WordSentenceParser wordSentenceParser;
    private WordCorrect wordCorrect;

    public int getNumberOfCharacters(){
        return essay.length();
    }

    public int getNumberOfWords() throws IOException{
        return wordSentenceParser.tokenize(essay).size();
    }

    public int getNumberofSentence() throws IOException{
        return wordSentenceParser.sentenceDetector(essay).size();
    }

    public int getNumberOfWrongWords() throws IOException{

        List<String> words= wordSentenceParser.tokenize(essay);
        return words.stream().map(word -> wordCorrect.rightWord(word)?1:0 ).reduce(0, (x, y)-> x+y);
    }
}
