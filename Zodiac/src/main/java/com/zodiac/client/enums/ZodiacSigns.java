package com.zodiac.client.enums;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Zodiac enum. Constructor takes range in which every zodiac sign lies and creates Date based on provided info.
 */
public enum ZodiacSigns {
    AQUARIUS(1, 20, 2, 18, "Водолей"),
    PISCES(2, 19, 3, 20, "Рыбы"),
    ARIES(3, 21, 4, 19, "Овен"),
    TAURUS(3, 20, 5, 20, "Телец"),
    GEMINI(5, 21, 6, 20, "Близнецы"),
    CANCER(6, 21, 7, 22, "Рак"),
    LEO(7, 23, 8, 22, "Лев"),
    VIRGO(8, 23, 9, 22, "Дева"),
    LIBRA(9, 23, 10, 22, "Весы"),
    SCORPIO(10, 23, 11, 21, "Скорпион"),
    SAGITTARIUS(11, 22, 12, 21, "Стрелец"),
    CAPRICORN(12, 22, 1, 19, "Козерог");

    private int lowMonth;
    private int lowDay;
    private int highMonth;
    private int highDay;
    private String translatedSign;
    private LocalDate startZodiacDate;
    private LocalDate endZodiacDate;

    public String getTranslatedSign() {
        return translatedSign;
    }

    ZodiacSigns(int lowMonth, int lowDay, int highMonth, int highDay, String translatedSign) {
        this.lowMonth = lowMonth;
        this.lowDay = lowDay;
        this.highMonth = highMonth;
        this.highDay = highDay;
        this.translatedSign = translatedSign;
        this.startZodiacDate = LocalDate.of(1970, lowMonth, lowDay);
        this.endZodiacDate = LocalDate.of(1970, highMonth, highDay);
    }

    public static ZodiacSigns getZodiac(int month, int day) {
        LocalDate dayAndMonthUserBirth = LocalDate.of(1970, month, day);
        ZodiacSigns userSign = Arrays.asList(ZodiacSigns.values()).stream().filter(item->
            (dayAndMonthUserBirth.compareTo(item.startZodiacDate) >= 0)
                    && (dayAndMonthUserBirth.compareTo(item.endZodiacDate) <= 0))
         .findFirst().get();
        return userSign;
        }
    }