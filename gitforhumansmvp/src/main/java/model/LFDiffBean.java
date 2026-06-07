package model;

import java.io.Serializable;
import java.util.UUID;
import java.time.OffsetDateTime;

public class LFDiffBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private UUID idDiff;
    private UUID idFile;
    private String bulletDescription;
    private OffsetDateTime commitDate;

    public LFDiffBean()
    {

    }

    public LFDiffBean(UUID idDiff)
    {
        this.idDiff = idDiff;
    }

    public LFDiffBean(UUID idFile, String bulletDescription)
    {
        this.idFile = idFile;
        this.bulletDescription = bulletDescription;
    }

    public LFDiffBean(UUID idDiff, UUID idFile, String bulletDescription, OffsetDateTime commitDate)
    {
        this.idDiff = idDiff;
        this.idFile = idFile;
        this.bulletDescription = bulletDescription;
        this.commitDate = commitDate;
    }

    public UUID getIdDiff()
    {
        return idDiff;
    }

    public void setIdDiff(UUID idDiff)
    {
        this.idDiff = idDiff;
    }

    public UUID getIdFile()
    {
        return idFile;
    }

    public void setIdFile(UUID idFile)
    {
        this.idFile = idFile;
    }

    public String getBulletDescription()
    {
        return bulletDescription;
    }

    public void setBulletDescription(String bulletDescription)
    {
        this.bulletDescription = bulletDescription;
    }

    public OffsetDateTime getCommitDate()
    {
        return commitDate;
    }

    public void setCommitDate(OffsetDateTime commitDate)
    {
        this.commitDate = commitDate;
    }
}