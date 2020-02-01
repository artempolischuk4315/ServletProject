package ua.polischuk.utility;

import ua.polischuk.model.entity.Category;

public class Validator {
    private static final String EMAIL = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    private static final String ENGLISH_WORDS = "^[a-zA-Z]+$";
    private static final String UKRAINIAN_WORDS = "[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ']+";
    private static final String NAME_OF_TEST = "^[a-zA-Z0-9]+$";
    private static final String UKR_NAME_OF_TEST = "[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ'0-9]+$";
    private static final String LEVEL_OF_DIFFICULTY = "^(?:[1-9]|0[1-9]|10)$";
    private static final String NUMBER_OF_QUESTIONS = "[0-9]{1,2}$";
    private static final String TIME_LIMIT = "[0-9]{1,3}$";

    public static boolean checkEmail(String email) {
        return email.matches(EMAIL);
    }
    public static boolean checkEnglishWords(String word) {
        return word.matches(ENGLISH_WORDS);
    }
    public static boolean checkUkrainianWords(String word) {
        return word.matches(UKRAINIAN_WORDS);
    }
    public static boolean checkEnglishNameOfTest(String name){return name.matches(NAME_OF_TEST);}
    public static boolean checkUkrNameOfTest(String name){return name.matches(UKR_NAME_OF_TEST);}
    public static boolean checkLevelOfDifficulty(int level){return String.valueOf(level).matches(LEVEL_OF_DIFFICULTY);}
    public static boolean checkNumberOfQuestions(int number){return String.valueOf(number).matches(NUMBER_OF_QUESTIONS);}
    public static boolean checkTimeLimit(int timeLimit){return String.valueOf(timeLimit).matches(TIME_LIMIT);}

    public boolean validateUser(String name, String nameUa, String lastName, String lastNameUa, String email){
        return checkEmail(email) && checkEnglishWords(name) && checkEnglishWords(lastName) &&
                checkUkrainianWords(nameUa) && checkUkrainianWords(lastNameUa);
    }


    public boolean validateTest(String name, String nameUa, Category category,
                                int difficulty, int numberOfQuestions, int timeLimit){

        return checkEnglishNameOfTest(name) && checkUkrNameOfTest(nameUa) && category != null &&
                checkLevelOfDifficulty(difficulty) && checkNumberOfQuestions(numberOfQuestions) && checkTimeLimit(timeLimit);
    }

}
