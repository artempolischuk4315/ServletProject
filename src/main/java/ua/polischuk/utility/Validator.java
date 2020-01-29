package ua.polischuk.utility;

public class Validator {
    private static final String EMAIL = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2, 4}$";
    private static final String ENGLISH_WORDS = "^[a-zA-Z]+$";
    private static final String UKRAINIAN_WORDS = "[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ']+";

    public static boolean checkEmail(String email) {
        return email.matches(EMAIL);
    }
    public static boolean checkEnglishWords(String word) {
        return word.matches(ENGLISH_WORDS);
    }
    public static boolean checkUkrainianWords(String word) {
        return word.matches(UKRAINIAN_WORDS);
    }

    public boolean validateUser(String name, String nameUa, String lastName, String lastNameUa, String email){
       if(checkEmail(email) && checkEnglishWords(name)&&checkEnglishWords(lastName)&&
               checkUkrainianWords(nameUa)&&checkUkrainianWords(lastNameUa)){
           return true;
       }
        return false;
    }
}
