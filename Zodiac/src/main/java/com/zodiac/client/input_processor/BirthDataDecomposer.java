package com.zodiac.client.input_processor;

import com.zodiac.client.enums.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Designed to extract constituent elements from input and prepare data for Date creation. Validates for critical errors.
 */

public class BirthDataDecomposer {
    Logger log = LoggerFactory.getLogger(BirthDataDecomposer.class);
    private String year;
    private String month;
    private String day;
    private static final String DELIMITER = "-";
    private static final int EXPECTED_INPUT_LENGTH = 10;
    private String trimmedUserInput = "";

    public List<String> layOutBirthData(String birthData, List<String> errors) {
        removeExtraSymbols(birthData);
        if (trimmedUserInput != "" && trimmedUserInput.length() == EXPECTED_INPUT_LENGTH) {
            String[] splittedInput = trimmedUserInput.split(DELIMITER);
            this.day = splittedInput[0];
            this.month = splittedInput[1];
            this.year = splittedInput[2];
            log.info("user input parsed successfully. day {}, month {}, year {} ",
                    this.day, this.month, this.year);
        } else {
            log.error("wrong user input. date format cannot be parsed. date: {} ", birthData);
            errors.add(ErrorMessages.WRONG_INPUT_LENGTH.getMessage());
        }
        return errors;
    }

    public String removeExtraSymbols(String userInput) {
        userInput = userInput.replaceAll("[^0-9-]", "").trim();
        if (!userInput.equals("")) {
            trimmedUserInput = userInput;
        }
        return trimmedUserInput;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }
}

