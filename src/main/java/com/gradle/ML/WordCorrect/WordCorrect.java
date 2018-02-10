package com.gradle.ML.WordCorrect;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import java.io.File;
import java.io.IOException;

/**
 *   This class uses Lucene' spellchecker to check words' correctness;
 *   the words that are less than 3-words long are not checked (as Lucene does not index them)
 */

public class WordCorrect {
    static String wordsDictionary= "/Users/zhanshu/workspace/WebDemo/dict/words.txt";
    private SpellChecker spellChecker;

    public WordCorrect() {
        Directory spellIndexDir = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36));
        try{
            this.spellChecker = new SpellChecker(spellIndexDir);
            this.spellChecker.indexDictionary(
                    new PlainTextDictionary(new File(wordsDictionary)),
                    config,
                    false);
            this.spellChecker.setAccuracy(0.1f);
        } catch (IOException e){
            throw new IllegalArgumentException("IOE error");
        }

    }

    public Boolean isRightWord(String word){

        // todo, add a speical whitelist for special names, brand and new words

        try{
            return spellChecker.exist(word);
        }catch (IOException e){
            throw new IllegalArgumentException( "Spell checker closed");

        }
    }
}
