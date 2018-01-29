package com.gradle.ML.WordCorrect;

import com.sun.tools.javac.jvm.ClassFile;
import com.sun.xml.internal.ws.util.VersionUtil;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.search.spell.StringDistance;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WordCorrect {
    static String wordsDictionary= "/Users/zhanshu/workspace/WebDemo/dict/web2";
    private SpellChecker spellChecker;

    public WordCorrect() throws IOException{
        Directory spellIndexDir = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, null);
        this.spellChecker = new SpellChecker(spellIndexDir,new LevensteinDistance());
        this.spellChecker.indexDictionary(new PlainTextDictionary(
                        new File(wordsDictionary)),
                config, false);
    }

    public Boolean rightWord(String word) throws IOException {
        return this.spellChecker.exist(word);
    }
}
