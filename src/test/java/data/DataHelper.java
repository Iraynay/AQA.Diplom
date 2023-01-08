package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    private DataHelper() {
    }

    public static String getValidCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getValidCardStatus() {
        return "APPROVED";
    }

    public static String getInvalidCardStatus() {
        return "DECLINED";
    }

    public static String getInvalidCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getEmptyCardNumber() {
        return "                ";
    }

    public static String getRandomCardNumber() {
        return fakerEn.business().creditCardNumber();
    }

    public static String getValidMonth() {
        return "06";

    }

    public static String getInValidMonth() {
        return "13";
    }

    public static String getEmptyMonth() {
        return "  ";
    }

    public static String getValidYear() {
        //   return LocalDate.now().plusYears(years).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return LocalDate.now().plusYears(3).format(DateTimeFormatter.ofPattern("yy"));

    }

    public static String getInvalidYear() {
        return "19";
    }

    public static String getEmptyYear() {
        return " ";
    }

    public static String getCardHolder() {
        return "Ivan Ivanov";
    }

    public static String getRandomCardHolder() {
        return fakerRu.name().fullName();
    }

    public static String getEmptyCardHolder() {
        return " ";
    }

    public static String getValidCVC() {
        return "999";
    }

    public static String getInvalidCVC() {
        return "23";
    }

    public static String getEmptyCVC() {
        return "   ";
    }
}
