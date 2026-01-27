package de.zbmed.snoke.dl.doc2vec;

import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;


/**
 * MyFileLabelAwareIterator
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class MyFileLabelAwareIterator extends FileLabelAwareIterator {
    public String getFileName () {
        return this.files.get(this.position.get()).getName();
    }
}
