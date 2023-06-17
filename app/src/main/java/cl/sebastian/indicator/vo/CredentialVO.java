package cl.sebastian.indicator.vo;

import cl.sebastian.indicator.data.model.Seba;

/**
 *
 * @author seba
 */
public class CredentialVO extends Seba {

    private String username = null;
    private String password = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
