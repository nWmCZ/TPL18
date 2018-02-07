package eu.sn.scheduler;

import eu.sn.ack.ReasonCode;
import eu.sn.configuration.Configuration;
import eu.sn.ecp.ECPContentBuilder;
import eu.sn.model.Ack;
import eu.sn.model.Message;
import eu.sn.parse.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

import static eu.sn.ack.AckBuilder.createAck;

@Component
public class ScheduledTasks {

    @Autowired
    XMLParser xmlParser;

    @Autowired
    ECPContentBuilder ecpContentBuilder;

    @Autowired
    Configuration configuration;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(fixedRate = 5000)
    public void processFilesInFolder() throws Exception {
        File inputDir = new File(configuration.getInDir());
        File[] files = inputDir.listFiles();

        log.debug("Files count {} in folder {}", files.length, configuration.getInDir());
        if (files.length > 0) {
            for (File file: files) {

                Message m = xmlParser.parseXML(file);

                Ack ack = createAck(m, ReasonCode.A01, null);

                byte[] bytes = ecpContentBuilder.createECPContent(ack);

                // Put ACK to OUT_ACK

                Files.write(Paths.get(configuration.getOutAckDir() + ack.getmRID()), bytes);

                // Put ACK to OUT dir for ECP fssf channel
                Files.write(Paths.get(configuration.getOutDir()
                        + configuration.getSenderId() + "_"
                        + configuration.getReceiverId() + "_"
                        + ack.getmRID() + "_" + UUID.randomUUID()), bytes);

                log.debug("Copying file {}", file.getName());
                String movedFile = configuration.getInDirProcessed() + file.getName() + "_PROCESSED_" + new Date();
                Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(movedFile), StandardCopyOption.REPLACE_EXISTING);
                log.debug("File {} was moved to {}", file.getName(), movedFile);

                file.delete();
                log.debug("File {} was deleted", file.getName());
            }
        }
    }
}