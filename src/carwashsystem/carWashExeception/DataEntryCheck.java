
package carwashsystem.carWashExeception;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.RadioButton;
import javax.crypto.Cipher;

/**
 *
 * @author
 */
public class DataEntryCheck {

    public static final String KEY_STRING = "abcdefghijklmnopqrstuvwxyz";
    private static Cipher encryptPass;
    private static Cipher decryptPass;

    //check if all fields are not empty
    public static boolean textFieldNotEmpty(String s) {
        return !s.isEmpty();

    }

    //check the length of the argument received for data integrity
    public static boolean minLength(String text, int len) {
        return text.length() >= len;
    }

    public static boolean maxLength(String text, int len) {
        return text.length() <= len;
    }

    //AGE
    public static boolean checkNegativeNumber(int data) {
        return data >= 0;
    }

    public static boolean checkNegativeDoubleNumber(double data) {
        return data >= 0;
    }

    // check if textfield only accepts number
    public static boolean onlyNumberTextField(String text) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() && matcher.group().equals(text);
    }

    // check if textfield only accepts text
    public static boolean textOnlyTextField(String text) {
        Pattern pattern = Pattern.compile("[a-zA-Z ]+");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() && matcher.group().equals(text);
    } 
    public static boolean textOnlyTextField2(String text) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() && matcher.group().equals(text);
    }

    //check if the email received matches the below pattern
    ///[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+ 
    public static boolean emailTextField(String text) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() && matcher.group().equals(text);
    }

    //check if the password  received matches the below pattern
    //((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$%#]),{8,16})
    public static boolean passwordTextField(String text) {
        Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$%#]),{8,32})");
        Matcher matcher = pattern.matcher(text);

        return matcher.matches(); //23abBA@#
    }

    public static boolean radioButtonValidation(RadioButton radBtn) {

        return radBtn.isSelected();
    }

    //mobile validation pattern (0|27)?[6-8][0-9]{9}
    public static boolean phoneTextField1(String text) {
        Pattern pattern = Pattern.compile("(0|07)?[6-8][0-9]{9}");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() && matcher.group().equals(text);
    }

    public static boolean phoneTextField2(String text) {
        return text.charAt(0) == '0' && text.charAt(1) == '7' && text.charAt(2) == '3' || text.charAt(2) == '4' || text.charAt(0) == '0' && text.charAt(1) == '8' && text.charAt(2) == '3' || text.charAt(2) == '4' && text.length() >= 10 && text.length() <= 13 && text.matches("[0-9]+");
    }

    public static String encryptPassWithMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(pass.getBytes());
            BigInteger num = new BigInteger(1, messageDigest);
            String hashText = num.toString(16);

            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);

        }
    }

    public static boolean isValidPassword(String pass) {
        if (pass.length() > 7 && pass.length() <= 100) {

            return checkPasswordPattern(pass);
        } else {
            return false;
        }
    }

    //pattern
    // contain 8- 32 characters
    // contain at least one lowercase
    //contain at least one uppercase
    //contain at least one digit
    public static boolean checkPasswordPattern(String password) {
        boolean hasDigit = false;
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        char character;

        for (int x = 0; x < password.length(); x++) {
            character = password.charAt(x);

            if (Character.isDigit(character)) {
                hasDigit = true;
            } else if (Character.isLowerCase(character)) {
                hasLowerCase = true;
            } else if (Character.isUpperCase(character)) {
                hasUpperCase = true;
            }
            if (hasDigit && hasLowerCase && hasUpperCase) {
                return true;
            }
        }
        return false;
    }        
}
