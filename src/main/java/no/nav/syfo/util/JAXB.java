package no.nav.syfo.util;

import no.kith.xmlstds.base64container.XMLBase64Container;
import no.kith.xmlstds.msghead._2006_05_24.XMLMsgHead;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

import static java.lang.Boolean.TRUE;
import static javax.xml.bind.JAXBContext.newInstance;
import static javax.xml.bind.Marshaller.*;

public final class JAXB {

    private static final JAXBContext HODEMELDING_CONTEXT;

    static {
        try {
            HODEMELDING_CONTEXT = newInstance(
                    XMLMsgHead.class,
                    no.kith.xmlstds.dialog._2006_10_11.XMLDialogmelding.class,
                    no.kith.xmlstds.dialog._2013_01_23.XMLDialogmelding.class,
                    XMLBase64Container.class
            );
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static String marshallHodemelding(Object element) {
        try {
            StringWriter writer = new StringWriter();
            Marshaller marshaller = HODEMELDING_CONTEXT.createMarshaller();
            marshaller.setProperty(JAXB_FORMATTED_OUTPUT, TRUE);
            marshaller.setProperty(JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(JAXB_FRAGMENT, true);
            marshaller.marshal(element, new StreamResult(writer));
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T unmarshalHodemelding(String melding) {
        try {
            return (T) HODEMELDING_CONTEXT.createUnmarshaller().unmarshal(new StringReader(melding));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
