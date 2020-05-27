package de.zbmed.dl.doc2vec;

import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;

/**
 * MyFileLabelAwareIterator
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class MyFileLabelAwareIterator extends FileLabelAwareIterator implements LabelAwareIterator {
    public String getFileName () {
        return this.files.get(this.position.get()).getName();
    }
}
