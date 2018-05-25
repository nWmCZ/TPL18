package eu.sn.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Configuration {
    @Value("${fssf.inDir}")
    private String inDir;
    @Value("${fssf.inDirProcessed}")
    private String inDirProcessed;
    @Value("${fssf.outDir}")
    private String outDir;
    @Value("${fssf.outAckDir}")
    private String outAckDir;
    @Value("${tpl.senderApplication}")
    private String senderApplication;

    public String getInDir() {
        return inDir;
    }

    public void setInDir(String inDir) {
        this.inDir = inDir;
    }

    public String getInDirProcessed() {
        return inDirProcessed;
    }

    public void setInDirProcessed(String inDirProcessed) {
        this.inDirProcessed = inDirProcessed;
    }

    public String getOutDir() {
        return outDir;
    }

    public void setOutDir(String outDir) {
        this.outDir = outDir;
    }

    public String getOutAckDir() {
        return outAckDir;
    }

    public void setOutAckDir(String outAckDir) {
        this.outAckDir = outAckDir;
    }

    public String getSenderApplication() {
        return senderApplication;
    }

    public void setSenderApplication(String senderApplication) {
        this.senderApplication = senderApplication;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "inDir='" + inDir + '\'' +
                ", inDirProcessed='" + inDirProcessed + '\'' +
                ", outDir='" + outDir + '\'' +
                ", outAckDir='" + outAckDir + '\'' +
                ", senderApplication='" + senderApplication + '\'' +
                '}';
    }
}
