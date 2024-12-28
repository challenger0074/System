package cn;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginForm implements Serializable {
    private String username;
    private String password;
    public LoginForm(){}

    public String toString(){
        return "usernameInfo{"+"username='"+username+'\''+",password="+password+'}';
    }
}
