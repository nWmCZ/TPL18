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

@Service
public class XMLParser {
    public Message parseXML(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        String mRID = doc.getDocumentElement().getElementsByTagName("mRID").item(0).getTextContent();
        String version = doc.getDocumentElement().getElementsByTagName("revisionNumber").item(0).getTextContent();
        String createdDateTime = doc.getDocumentElement().getElementsByTagName("createdDateTime").item(0).getTextContent();
        String senderMarketParticipant = doc.getDocumentElement().getElementsByTagName("sender_MarketParticipant.mRID").item(0).getTextContent();
        String senderMarketParticipantType = doc.getDocumentElement().getElementsByTagName("sender_MarketParticipant.marketRole.type").item(0).getTextContent();
        String receiverMarketParticipant = doc.getDocumentElement().getElementsByTagName("receiver_MarketParticipant.mRID").item(0).getTextContent();
        String receiverMarketParticipantType = doc.getDocumentElement().getElementsByTagName("receiver_MarketParticipant.marketRole.type").item(0).getTextContent();

        Message message = new Message();
        message.setDocumentId(mRID);
        message.setVersion(Integer.valueOf(version));
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
