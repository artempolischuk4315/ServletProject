package ua.polischuk.utility;

public class PasswordValidator {
    private static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,}$";

    public static boolean checkPassword(String pass) {
        return pass.matches(PASSWORD);
    }

}
