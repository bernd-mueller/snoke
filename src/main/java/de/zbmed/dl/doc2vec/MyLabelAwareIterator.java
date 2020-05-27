package de.zbmed.dl.doc2vec;

import de.zbmed.dl.json.BioASQDocument;
import org.deeplearning4j.text.documentiterator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

/**
 * MyLabelAwareIterator
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class MyLabelAwareIterator implements LabelAwareIterator{
    private static final Logger log = LoggerFactory.getLogger(MyLabelAwareIterator.class);
    List<BioASQDocument> docs;
    Iterator<BioASQDocument> it;
    BioASQDocument curdoc;
    LabelsSource ls = new LabelsSource();

    public List <BioASQDocument> getDocs() {
        return docs;
    }

    public void setDocs(List <BioASQDocument> docs) {
        log.info("Setting Set of BioASQDocuments with size " + docs.size());
        this.docs = docs;
        this.it = this.docs.iterator();
        Iterator <BioASQDocument> iter = docs.iterator();
        while (iter.hasNext()) {
            BioASQDocument b = iter.next();
            for (String s : b.getLabels()) {
                ls.storeLabel(s);
                log.info("Storing label " + s);
            }
        }
    }

    public Iterator<BioASQDocument> getIt() {
        log.info("Call of getIt()");
        return it;
    }

    public void setIt(Iterator<BioASQDocument> it) {
        log.info("Call of setIt()");
        this.it = it;
    }

    public BioASQDocument getCurdoc() {
        log.info("Call of getCurdoc() with curdoc being " + curdoc.getPmid());
        return curdoc;
    }

    public void setCurdoc(BioASQDocument curdoc) {
        log.info("Call of getCurdoc() with setCurdoc being " + curdoc.getPmid());
        this.curdoc = curdoc;
    }

    public boolean hasNext() {
        log.info("Call of hasNext () with " + it.hasNext());
        return (it.hasNext());
    }
    public boolean hasNextDocument() {
        log.info("Call of hasNextDocument () with " + it.hasNext());
        return (it.hasNext());
    }

    public LabelledDocument nextDocument() {
        curdoc = it.next();
        log.info("Call of nextDocument () with " + curdoc.getPmid());
        return curdoc;
    }
    public LabelledDocument next() {
        curdoc = it.next();
        log.info("Call of next () with " + curdoc.getPmid());
        return curdoc;
    }

    public void remove() {
        log.info("Call of remove ()");
        it.remove();
    }

    public void reset() {
        log.info("Call of reset ()");
        it = docs.iterator();
    }

    public LabelsSource getLabelsSource() {
        log.info("Call of getLabelsSource () with ls being " + ls.getLabels());
        return ls;
    }

    public void shutdown() {

    }

    public String getLabel () {
        log.info("Calling deprecated method getLabel()");
        return getLabels().toString();
    }
    public List<String> getLabels () {
        return curdoc.getLabels();
    }
}
