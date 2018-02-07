package eu.sn.ecp;

import eu.sn.ack.ACKJAXB;
import eu.sn.configuration.Configuration;
import eu.sn.model.Ack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SuppressWarnings("unchecked")
@Service
public class ECPContentBuilder {

    private static final Logger log = LoggerFactory.getLogger(ECPContentBuilder.class);


    @Autowired
    Configuration configuration;

    public byte[] createECPContent(Ack ack) throws Exception {

        ACKJAXB ackjaxb = new ACKJAXB();

        // 2017-11-21T15:21:21Z
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");


        ackjaxb.setmRID(ack.getmRID());
        ackjaxb.setCreatedDateTime(format.format(ack.getCreatedDateTime()));
        ackjaxb.setReceiver(ack.getMessage().getSenderMarketParticipant());
        ackjaxb.setReceiverType(ack.getMessage().getSenderMarketParticipantType());
        ackjaxb.setSender(ack.getMessage().getReceiverMarketParticipant());
        ackjaxb.setSenderType(ack.getMessage().getReceiverMarketParticipantType());
        ackjaxb.setReceivedMRID(ack.getMessage().getDocumentId());
        ackjaxb.setReceivedVersion(ack.getMessage().getVersion());
        ackjaxb.setReceivedCreatedDateTime(format.format(ack.getMessage().getCreatedDateTime()));
        ackjaxb.setReasonCode(ack.getReasonCode().toString());

        if (ack.getReasonText() != null) {
            ackjaxb.setReasonText(ack.getReasonText());
        }

        Writer out = new StringWriter();

        try {
            File file = new File(configuration.getOutAckDir());
            JAXBContext jaxbContext = JAXBContext.newInstance(ACKJAXB.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(ackjaxb, out);
            log.debug(out.toString());
            // print to console: jaxbMarshaller.marshal(ackjaxb, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return out.toString().getBytes("UTF-8");
    }

}
