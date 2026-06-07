package model;

import java.io.Serializable;
import java.util.UUID;
import java.time.OffsetDateTime;

public class MetadataBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private UUID idFile;
    private UUID idRepository;
    private String fileName;
    private String azureUrl;
    private String changeType;
    // DECISIÓN: Usamos el tipo primitivo 'int' en lugar de la clase envoltorio 'Integer'.
    // Esto es porque en tu script SQL definiste "DEFAULT 1" y "NOT NULL". 
    // Al usar 'int', evitamos que esta variable pueda ser NULL en la RAM, empatando con la BD.
    private int codeVersion; 
    private OffsetDateTime uploadDate;

    public MetadataBean()
    {

    }

    public MetadataBean(UUID idFile)
    {
        this.idFile = idFile;
    }

    public MetadataBean(UUID idRepository, String fileName, String azureUrl, String changeType, int codeVersion)
    {
        this.idRepository = idRepository;
        this.fileName = fileName;
        this.azureUrl = azureUrl;
        this.changeType = changeType;
        this.codeVersion = codeVersion;
    }

    public MetadataBean(UUID idFile, UUID idRepository, String fileName, String azureUrl, String changeType, int codeVersion, OffsetDateTime uploadDate)
    {
        this.idFile = idFile;
        this.idRepository = idRepository;
        this.fileName = fileName;
        this.azureUrl = azureUrl;
        this.changeType = changeType;
        this.codeVersion = codeVersion;
        this.uploadDate = uploadDate;
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

    public String getAzureUrl()
    {
        return azureUrl;
    }

    public void setAzureUrl(String azureUrl)
    {
        this.azureUrl = azureUrl;
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
}