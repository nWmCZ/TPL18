package eu.sn.ack;

import eu.sn.model.Ack;
import eu.sn.model.Message;

import java.util.Date;

public class AckBuilder {

    private AckBuilder() {

    }

    public static Ack createAck(Message message, ReasonCode reasonCode, String reasonText) {
        Ack ack = new Ack();
        StringBuilder mrid = new StringBuilder("ACK-");
        mrid.append(reasonCode.toString()).append("-").append(message.getDocumentId());
        ack.setmRID(mrid.toString());
        ack.setCreatedDateTime(new Date());
        ack.setMessage(message);
        ack.setReasonCode(reasonCode);
        ack.setReasonText(reasonText);

        return ack;
    }
}
