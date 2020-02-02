package eu.sn.parse;

import eu.sn.model.Message;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class XMLParser {
    public Message parseXML(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        String mRID = "";
        String version = "";
        if (null != doc.getDocumentElement().getElementsByTagNameNS("*","mRID").item(0)) {
            mRID = doc.getDocumentElement().getElementsByTagNameNS("*","mRID").item(0).getTextContent();
        }
        if (null != doc.getDocumentElement().getElementsByTagNameNS("*","revisionNumber").item(0)) {
            version = doc.getDocumentElement().getElementsByTagNameNS("*","revisionNumber").item(0).getTextContent();
        }
        String createdDateTime = doc.getDocumentElement().getElementsByTagNameNS("*", "createdDateTime").item(0).getTextContent();
        String senderMarketParticipant = doc.getDocumentElement().getElementsByTagNameNS("*","sender_MarketParticipant.mRID").item(0).getTextContent();
        String senderMarketParticipantType = doc.getDocumentElement().getElementsByTagNameNS("*","sender_MarketParticipant.marketRole.type").item(0).getTextContent();
        String receiverMarketParticipant = doc.getDocumentElement().getElementsByTagNameNS("*","receiver_MarketParticipant.mRID").item(0).getTextContent();
        String receiverMarketParticipantType = "";
        if (null != doc.getDocumentElement().getElementsByTagNameNS("*","receiver_MarketParticipant.marketRole.type").item(0)) {
            receiverMarketParticipantType = doc.getDocumentElement().getElementsByTagNameNS("*","receiver_MarketParticipant.marketRole.type").item(0).getTextContent();
        }
        Message message = new Message();
        if (mRID.length() == 0) {
            message.setDocumentId(UUID.randomUUID().toString().replace("-", ""));
        } else {
            message.setDocumentId(mRID);
        }

        if (version.length() != 0) {
            message.setVersion(Integer.valueOf(version));
        }

        // 2017-11-21T15:21:21Z
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(createdDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        message.setCreatedDateTime(date);
        message.setSenderMarketParticipant(senderMarketParticipant);
        message.setSenderMarketParticipantType(senderMarketParticipantType);
        message.setReceiverMarketParticipant(receiverMarketParticipant);
        message.setReceiverMarketParticipantType(receiverMarketParticipantType);

        return message;
    }
}
