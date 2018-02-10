package com.gradle.ML.EssayStatistics;

import com.google.common.collect.ImmutableList;
import com.gradle.ML.OpenNLP.WordSentenceParser;
import com.gradle.ML.WordCorrect.WordCorrect;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

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

    public ImmutableList<String> getNumberOfWrongWords(String essay) throws IOException{

        /**
         *  Only check the words more than 2-letters;
         */

        List<String> words= wordSentenceParser.tokenize(essay);

        return words.stream().filter(word -> word.length()>2) // only check word longer than 2-letter
                .map(word -> wordCorrect.isRightWord(word.trim())? "": word.trim())
                .filter(word -> word.length()>0)
                .collect(toImmutableList());
    }

}
