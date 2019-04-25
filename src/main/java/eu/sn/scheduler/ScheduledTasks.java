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
import java.nio.file.attribute.PosixFilePermission;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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

                String originalSenderId = file.getName().split("_")[1];

                Message m = xmlParser.parseXML(file);

                Ack ack = createAck(m, ReasonCode.A01, null);

                byte[] bytes = ecpContentBuilder.createECPContent(ack);

                String finalFileName =
                        configuration.getSenderApplication() + "_"
                        + originalSenderId + "_"
                        + ack.getmRID() + "_" + UUID.randomUUID();

                Set<PosixFilePermission> perms = new HashSet<>();
                perms.add(PosixFilePermission.OWNER_READ);
                perms.add(PosixFilePermission.OWNER_WRITE);
                perms.add(PosixFilePermission.OWNER_EXECUTE);

                perms.add(PosixFilePermission.OTHERS_READ);
                perms.add(PosixFilePermission.OTHERS_WRITE);
                perms.add(PosixFilePermission.OTHERS_EXECUTE);

                perms.add(PosixFilePermission.GROUP_READ);
                perms.add(PosixFilePermission.GROUP_WRITE);
                perms.add(PosixFilePermission.GROUP_EXECUTE);

                // Put ACK to OUT_ACK
                Files.write(Paths.get(configuration.getOutAckDir() + finalFileName), bytes);
                Files.setPosixFilePermissions(Paths.get(configuration.getOutAckDir() + finalFileName), perms);

                // Put ACK to OUT dir for ECP fssf channel
                Files.write(Paths.get(configuration.getOutDir() + finalFileName), bytes);
                Files.setPosixFilePermissions(Paths.get(configuration.getOutDir() + finalFileName), perms);

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