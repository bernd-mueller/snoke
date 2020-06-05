package de.zbmed.snoke.lod.mesh;

import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * DTDEntityResolver
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class DTDEntityResolver implements EntityResolver {


	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		if (systemId.contains("desc2015.dtd")) {
            return new InputSource(new FileReader("/media/muellerb/Daten/MeSH/desc2015.dtd"));
        } else {
            return null;
        }
	}
}