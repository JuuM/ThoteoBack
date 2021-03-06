package fr.etna.thoteoback.controller.authentication.model;

/**
 * Created by juju_ on 02/08/2016.
 */
public class AuthenticationForm
{
    private String email = null;

    private String password = null;

    private String key = null;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String toString()
    {

        return "AuthenticationForm{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
