package com.zodiac.client.enums;

/**
 * Enum describes all possible error that could be visible for user in case of wrong input.
 */
public enum ErrorMessages {

    WRONG_INPUT_LENGTH("wrong birth date format."),
    WRONG_MONTH("month must lay between 1 and 12."),
    WRONG_DAY_OR_MONTH("please, correct your input. day or month is wrong."),
    DAY_NOT_EXISTS("day must lay between 1 and 31."),
    IMPOSSIBLY_LONG_LIVING_PERSON("you could`t be born in that year."),
    BORN_IN_FUTURE("can`t be born in future. please, correct your input.");

    final String message;

    public String getMessage() {
        return message;
    }

    ErrorMessages(String message) {
        this.message = message;
    }
}
