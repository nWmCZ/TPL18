package eu.sn.ack;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Acknowledgement_MarketDocument")
@XmlAccessorType(XmlAccessType.FIELD)
public class ACKJAXB {
    private String mRID;
    private String createdDateTime;
    @XmlElement(name = "sender_MarketParticipant.mRID")
    private Sender sender;
    @XmlElement(name = "sender_MarketParticipant.marketRole.type")
    private String senderType;
    @XmlElement(name = "receiver_MarketParticipant.mRID")
    private Receiver receiver;
    @XmlElement(name = "receiver_MarketParticipant.marketRole.type")
    private String receiverType;
    @XmlElement(name = "received_MarketDocument.mRID")
    private String receivedMRID;
    @XmlElement(name = "received_MarketDocument.revisionNumber")
    private int receivedVersion;
    @XmlElement(name = "received_MarketDocument.createdDateTime")
    private String receivedCreatedDateTime;
    @XmlElement(name = "Reason")
    private Reason reason;

    public ACKJAXB() {
        this.reason = new Reason();
        this.sender = new Sender();
        this.receiver = new Receiver();
    }

    public String getmRID() {
        return mRID;
    }

    public void setmRID(String mRID) {
        this.mRID = mRID;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getSender() {
        return this.sender.getSender();
    }

    public void setSender(String sender) {
        this.sender.setSender(sender);
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getReceiver() {
        return this.receiver.getReceiver();
    }

    public void setReceiver(String receiver) {
        this.receiver.setReceiver(receiver);
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceivedMRID() {
        return receivedMRID;
    }

    public void setReceivedMRID(String receivedMRID) {
        this.receivedMRID = receivedMRID;
    }

    public int getReceivedVersion() {
        return receivedVersion;
    }

    public void setReceivedVersion(int receivedVersion) {
        this.receivedVersion = receivedVersion;
    }

    public String getReceivedCreatedDateTime() {
        return receivedCreatedDateTime;
    }

    public void setReceivedCreatedDateTime(String receivedCreatedDateTime) {
        this.receivedCreatedDateTime = receivedCreatedDateTime;
    }

    @Override
    public String toString() {
        return "ACKJAXB{" +
                "mRID='" + mRID + '\'' +
                ", createdDateTime='" + createdDateTime + '\'' +
                ", sender='" + sender + '\'' +
                ", senderType='" + senderType + '\'' +
                ", receiver='" + receiver + '\'' +
                ", receiverType='" + receiverType + '\'' +
                ", receivedMRID='" + receivedMRID + '\'' +
                ", receivedVersion='" + receivedVersion + '\'' +
                ", receivedCreatedDateTime='" + receivedCreatedDateTime + '\'' +
                ", reason=" + reason +
                '}';
    }

    public void setReasonCode(String code) {
        this.reason.setCode(code);
    }

    public void setReasonText(String text) {
        this.reason.setText(text);
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class Sender {
    @XmlValue
    private String sender;
    @XmlAttribute
    private String codingScheme = "A01";

    @Override
    public String toString() {
        return "Sender{" +
                "sender='" + sender + '\'' +
                ", codingScheme='" + codingScheme + '\'' +
                '}';
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCodingScheme() {
        return codingScheme;
    }

    public void setCodingScheme(String codingScheme) {
        this.codingScheme = codingScheme;
    }
}
@XmlAccessorType(XmlAccessType.FIELD)
class Receiver {
    @XmlValue
    private String receiver;
    @XmlAttribute
    private String codingScheme = "A01";

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCodingScheme() {
        return codingScheme;
    }

    public void setCodingScheme(String codingScheme) {
        this.codingScheme = codingScheme;
    }

    @Override
    public String toString() {
        return "Receiver{" +
                "receiver='" + receiver + '\'' +
                ", codingScheme='" + codingScheme + '\'' +
                '}';
    }
}

class Reason {
    private String code;
    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Reason{" +
                "code='" + code + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
