package model;

import java.io.Serializable;
import java.util.UUID;
import java.time.OffsetDateTime;

public class MetadataBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private UUID idFile;
    private UUID idRepository;
    private UUID idUserUpload;
    private String fileName;
    private String filePath;
    private String changeType;
    private int codeVersion; 
    private long byteSize;
    private OffsetDateTime uploadDate;

    public MetadataBean()
    {

    }

    public MetadataBean(UUID idFile)
    {
        this.idFile = idFile;
    }

    public MetadataBean(UUID idRepository, String fileName, String filePath, String changeType, int codeVersion)
    {
        this.idRepository = idRepository;
        this.fileName = fileName;
        this.filePath = filePath;
        this.changeType = changeType;
        this.codeVersion = codeVersion;
    }

    public MetadataBean(UUID idFile, UUID idRepository, String fileName, String filePath, String changeType, int codeVersion, OffsetDateTime uploadDate, UUID idUserUpload, long byteSize)
    {
        this.idFile = idFile;
        this.idRepository = idRepository;
        this.fileName = fileName;
        this.filePath = filePath;
        this.changeType = changeType;
        this.codeVersion = codeVersion;
        this.uploadDate = uploadDate;
        this.idUserUpload = idUserUpload;
        this.byteSize = byteSize;
    }

    public UUID getIdFile()
    {
        return idFile;
    }

    public void setIdFile(UUID idFile)
    {
        this.idFile = idFile;
    }

    public UUID getIdRepository()
    {
        return idRepository;
    }

    public void setIdRepository(UUID idRepository)
    {
        this.idRepository = idRepository;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getfilePath()
    {
        return filePath;
    }

    public void setfilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getChangeType()
    {
        return changeType;
    }

    public void setChangeType(String changeType)
    {
        this.changeType = changeType;
    }

    public int getCodeVersion()
    {
        return codeVersion;
    }

    public void setCodeVersion(int codeVersion)
    {
        this.codeVersion = codeVersion;
    }

    public OffsetDateTime getUploadDate()
    {
        return uploadDate;
    }

    public void setUploadDate(OffsetDateTime uploadDate)
    {
        this.uploadDate = uploadDate;
    }

    public UUID getIdUserUpload() {
        return idUserUpload;
    }

    public void setIdUserUpload(UUID idUserUpload) {
        this.idUserUpload = idUserUpload;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getByteSize() {
        return byteSize;
    }

    public void setByteSize(long byteSize) {
        this.byteSize = byteSize;
    }
}