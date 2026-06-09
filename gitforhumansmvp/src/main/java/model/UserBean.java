package model;

import java.io.Serializable;
import java.util.UUID;
import java.time.OffsetDateTime;

public class UserBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private UUID idUser;
    private String name;
    private String email;
    private String password;
    private OffsetDateTime registerDate;
    private String avatarUrl;
    private Boolean is_active;

    public UserBean()
    {

    }

    public UserBean(UUID idUser)
    {
        this.idUser = idUser;
    }

    public UserBean(String name, String email, String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserBean(UUID idUser, String name, String email, String password, OffsetDateTime registerDate, String avatarUrl, Boolean is_active) 
    {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.password = password;
        this.registerDate = registerDate;
        this.avatarUrl = avatarUrl;
        this.is_active = is_active;
    }

    public UUID getIdUser()
     {
        return idUser;
    }

    public String getName() 
    {
        return name;
    }

    public String getEmail() 
    {
        return email;
    }

    public String getPassword() 
    {
        return password;
    }

    public OffsetDateTime getRegisterDate() 
    {
        return registerDate;
    }

    public void setIdUser(UUID idUser)
    {
        this.idUser = idUser;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public void setRegisterDate(OffsetDateTime registerDate) 
    {
        this.registerDate = registerDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

}
