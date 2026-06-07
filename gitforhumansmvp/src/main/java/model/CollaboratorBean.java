package model;

import java.io.Serializable;
import java.util.UUID;
import java.time.OffsetDateTime;

public class CollaboratorBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private UUID idUser;
    private UUID idRepository;
    private String permissionLevel;
    private OffsetDateTime assignmentDate;

    public CollaboratorBean()
    {

    }

    public CollaboratorBean(UUID idUser, UUID idRepository)
    {
        this.idUser = idUser;
        this.idRepository = idRepository;
    }

    public CollaboratorBean(UUID idUser, UUID idRepository, String permissionLevel)
    {
        this.idUser = idUser;
        this.idRepository = idRepository;
        this.permissionLevel = permissionLevel;
    }

    public CollaboratorBean(UUID idUser, UUID idRepository, String permissionLevel, OffsetDateTime assignmentDate)
    {
        this.idUser = idUser;
        this.idRepository = idRepository;
        this.permissionLevel = permissionLevel;
        this.assignmentDate = assignmentDate;
    }

    public UUID getIdUser()
    {
        return idUser;
    }

    public void setIdUser(UUID idUser)
    {
        this.idUser = idUser;
    }

    public UUID getIdRepository()
    {
        return idRepository;
    }

    public void setIdRepository(UUID idRepository)
    {
        this.idRepository = idRepository;
    }

    public String getPermissionLevel()
    {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel)
    {
        this.permissionLevel = permissionLevel;
    }

    public OffsetDateTime getAssignmentDate()
    {
        return assignmentDate;
    }

    public void setAssignmentDate(OffsetDateTime assignmentDate)
    {
        this.assignmentDate = assignmentDate;
    }
}