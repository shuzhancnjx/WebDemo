package com.gradle.ML.OpenNLP;

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


@AllArgsConstructor
public class WordSentenceParser {

    public List<String> sentenceDetetor(String essay) throws FileNotFoundException, IOException {

        InputStream inputStream = new FileInputStream("/Users/zhanshu/workspace/WebDemo/openNLPtrainingFile/en-sent.bin");

        SentenceModel sentenceModel = new SentenceModel(inputStream);
        SentenceDetectorME sentenceDetectorMe = new SentenceDetectorME(sentenceModel);

        String[] sentences = sentenceDetectorMe.sentDetect(essay);

        return Arrays.asList(sentences);
    }

    public List<String> tokenize(String essay) throws FileNotFoundException,IOException {

        InputStream is = new FileInputStream("/Users/zhanshu/workspace/WebDemo/openNLPtrainingFile/en-token.bin");

        TokenizerModel model = new TokenizerModel(is);

        Tokenizer tokenizer = new TokenizerME(model);

        String[] token = tokenizer.tokenize(essay);

        return Arrays.asList(token);
    }
}
