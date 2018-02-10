package com.gradle.ML.OpenNLP;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

/**
 *  This class uses OpenNLP to tokenize texts for
 */

@AllArgsConstructor
public class WordSentenceParser {

    static String trainingMaterialPath = "/Users/zhanshu/workspace/WebDemo/openNLPtrainingFile/";

    public ImmutableList<String> sentenceDetector(String essay) throws FileNotFoundException, IOException {

        InputStream inputStream = new FileInputStream(trainingMaterialPath+ "en-sent.bin");
        SentenceModel sentenceModel = new SentenceModel(inputStream);
        SentenceDetectorME sentenceDetectorMe = new SentenceDetectorME(sentenceModel);
        String[] sentences = sentenceDetectorMe.sentDetect(essay);

        return Arrays.stream(sentences).collect(toImmutableList());
    }

    public ImmutableList<String> tokenize(String essay) throws FileNotFoundException,IOException {

        InputStream is = new FileInputStream(trainingMaterialPath + "en-token.bin");
        TokenizerModel model = new TokenizerModel(is);
        Tokenizer tokenizer = new TokenizerME(model);
        String[] token = tokenizer.tokenize(essay);

        return Arrays.stream(token).collect(toImmutableList());
    }
}