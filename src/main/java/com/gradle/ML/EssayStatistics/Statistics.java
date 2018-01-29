package com.gradle.ML.EssayStatistics;

import com.gradle.ML.OpenNLP.WordSentenceParser;
import lombok.AllArgsConstructor;
import java.io.IOException;

@AllArgsConstructor
public class Statistics {

    public String essay;
    public WordSentenceParser wordSentenceParser;

    public int getNumberOfCharacters(){
        return essay.length();
    }

    public int getNumberOfWords() throws IOException{
        return wordSentenceParser.tokenize(essay).size();
    }

    public int getNumberofSentence() throws IOException{
        return wordSentenceParser.sentenceDetetor(essay).size();
    }


}
