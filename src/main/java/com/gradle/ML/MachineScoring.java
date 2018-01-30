package com.gradle.ML;

import com.gradle.ML.EssayStatistics.Statistics;
import com.gradle.ML.OpenNLP.WordSentenceParser;
import com.gradle.ML.WordCorrect.WordCorrect;

import java.io.IOException;

public class MachineScoring {

    private Statistics statistics;

    public MachineScoring() {
        this.statistics = new Statistics( new WordSentenceParser(), new WordCorrect());
    }




}
