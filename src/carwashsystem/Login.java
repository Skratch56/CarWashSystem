
package carwashsystem;

import carwashsystem.carWashExeception.UnknownException;

/**
 *
 * @author Joseph
 */
public class Login {
  private String username,password, type;

    public Login(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }
   public boolean isLogin (Login objLog) throws UnknownException{
       return LoginModel.isLogin(this);
   }
   
   public String returnEmp (Login objLog) throws UnknownException{
       return LoginModel.returnEmp(this);
   }
}
