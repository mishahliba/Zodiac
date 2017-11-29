package com.zodiac.client.input_processor;

import com.zodiac.client.enums.ErrorMessages;
import com.zodiac.client.exceptions.IncorrectInputException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.zodiac.client.model.ValidInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validates user input for any possible non-critical errors. Throws exception immediately if some critical error happens
 * and further validation isn`t possible. Otherwise pass input through validation chain and collects error message.
 */

public class InputValidator {

    Logger log = LoggerFactory.getLogger(InputValidator.class);
    private static final Integer OLDEST_PERSON_BIRTHDAY_YEAR = 1900;
    private BirthDataDecomposer decomposer;
    private List<String> errorCollector = new ArrayList<>();

    public ValidInput getValidInput(){
        int day = Integer.valueOf(decomposer.getDay());
        int month = Integer.valueOf(decomposer.getMonth());
        return new ValidInput(day, month);
    }

    public InputValidator(String userInput) {
        decomposer = new BirthDataDecomposer();
        decomposer.layOutBirthData(userInput, errorCollector);
        log.debug("decomposed birth data: day {}, month {}, year {}",
                decomposer.getDay(), decomposer.getMonth(), decomposer.getYear());
    }

    public boolean inputCanBeProcessed() throws IncorrectInputException {
        if (errorCollector.isEmpty()) {
            validateCorrectMonth()
                    .validateCorrectDay()
                    .validateOldness()
                    .validateYouth();
            if (!errorCollector.isEmpty())
                throw new IncorrectInputException(String.join("\n", errorCollector));
        } else {
            throw new IncorrectInputException(errorCollector.get(0));
        }
        return true;
    }

    private InputValidator validateOldness() {
        Integer year = Integer.valueOf(decomposer.getYear());
        if (year < OLDEST_PERSON_BIRTHDAY_YEAR) {
            errorCollector.add(ErrorMessages.IMPOSSIBLY_LONG_LIVING_PERSON.getMessage());
            log.debug("user year input incorrect: {} ", year);
        }
        return this;
    }

    private InputValidator validateYouth() {
        LocalDate today = LocalDate.now();
        int userInputYear = Integer.valueOf(decomposer.getYear());
        int userInputMonth = Integer.valueOf(decomposer.getMonth());
        int userInputDay = Integer.valueOf(decomposer.getDay());
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        if ((year < userInputYear) ||
                (year == userInputYear && month < userInputMonth) ||
                (year == userInputYear && month == userInputMonth && day < userInputDay)) {
            errorCollector.add(ErrorMessages.BORN_IN_FUTURE.getMessage());
            log.debug("user input incorrect. born tomorrow or later. year {}, month {}, day {} ",
                    userInputYear, userInputMonth, userInputDay);
        }
        return this;
    }

    private InputValidator validateCorrectMonth() {
        int monthExists = Integer.valueOf(decomposer.getMonth());
        if (monthExists < 1 || monthExists > 12) {
            errorCollector.add(ErrorMessages.WRONG_MONTH.getMessage());
            log.warn("wrong user input. month not lay between 1 and 12. actual: {}", decomposer.getMonth());
        }
        return this;
    }

    private InputValidator validateCorrectDay() {
        int day = Integer.valueOf(decomposer.getDay());
        boolean dayExists = day <= 31 && day > 0;
        if (!dayExists) {
            log.warn("wrong user input. impossible day of month: {} ", day);
            errorCollector.add(ErrorMessages.DAY_NOT_EXISTS.getMessage());
            return this;
        }
        int month = Integer.valueOf(decomposer.getMonth());
        boolean lastDayOfMonthIncorrect = validateLastDayOfMonth(month, day);
        boolean leapIncorrect = validateLeapYear(month, day);
        if (lastDayOfMonthIncorrect || leapIncorrect) {
            errorCollector.add(ErrorMessages.WRONG_DAY_OR_MONTH.getMessage());
            log.warn("wrong user input. ");
        }
        return this;
    }

    private boolean validateLeapYear(int monthBirth, int dayBirth) {
        if ((monthBirth == 2 &&
                (dayBirth >= 30 || (dayBirth == 29 && Integer.valueOf(decomposer.getYear()) % 4 != 0)))) {
            log.warn("wrong user input. impossible date in February. date: {} ", dayBirth);
            return true;
        }
        return false;
    }

    private boolean validateLastDayOfMonth(int monthBirth, int dayBirth) {
        boolean maxMonthDay = dayBirth == 31;
        boolean monthDividesBy2 = monthBirth % 2 == 0;
        if (maxMonthDay && ((monthBirth < 9 && monthDividesBy2) || (monthBirth >= 9 && !monthDividesBy2))) {
            log.warn("wrong user input. day and month are incompatible. day {}, month {} ", dayBirth, monthBirth);
            return true;
        }
        return false;
    }
}
