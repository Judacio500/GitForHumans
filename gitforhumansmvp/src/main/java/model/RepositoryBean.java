package model;

import java.io.Serializable;
import java.util.UUID;
import java.time.OffsetDateTime;

public class RepositoryBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private UUID idRepository;
    private String name;
    private String description;
    private String git_path;
    private UUID idCreator; 
    private OffsetDateTime creationDate;

    public RepositoryBean()
    {

    }

    public RepositoryBean(UUID idRepository)
    {
        this.idRepository = idRepository;
    }

    public RepositoryBean(String name, String description, UUID idCreator, String git_path)
    {
        this.name = name;
        this.description = description;
        this.idCreator = idCreator;
        this.git_path = git_path;
    }

    public RepositoryBean(UUID idRepository, String name, String description, UUID idCreator, OffsetDateTime creationDate, String git_path) 
    {
        this.idRepository = idRepository;
        this.name = name;
        this.description = description;
        this.idCreator = idCreator;
        this.creationDate = creationDate;
        this.git_path = git_path;
    }

    public UUID getIdRepository()
    {
        return idRepository;
    }

    public void setIdRepository(UUID idRepository)
    {
        this.idRepository = idRepository;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public UUID getIdCreator()
    {
        return idCreator;
    }

    public void setIdCreator(UUID idCreator)
    {
        this.idCreator = idCreator;
    }

    public OffsetDateTime getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getGit_path() {
        return git_path;
    }

    public void setGit_path(String git_path) {
        this.git_path = git_path;
    }
    
}