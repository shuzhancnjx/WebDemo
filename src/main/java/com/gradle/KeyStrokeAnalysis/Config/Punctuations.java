package com.gradle.KeyStrokeAnalysis.Config;

import com.google.common.collect.ImmutableSet;

public class Punctuations {
    static public ImmutableSet<String> punctuations = ImmutableSet.of("?", "!", ".", ",", ":", ";");
    static public ImmutableSet<String> sentenceSeparators = ImmutableSet.of("?", ".", "!");

}
