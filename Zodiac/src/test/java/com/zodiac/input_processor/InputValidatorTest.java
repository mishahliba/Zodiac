package com.zodiac.input_processor;

import com.zodiac.client.exceptions.IncorrectInputException;
import com.zodiac.client.input_processor.InputValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class InputValidatorTest {

    InputValidator validator;

    @Test(expected = IncorrectInputException.class)
    public void impossibleYearTest() throws IncorrectInputException {
        String userInput = "21-11-19";
        validator = new InputValidator(userInput);
        validator.inputCanBeProcessed();
    }

    @Test
    public void extraSymbolsPresetTest() throws IncorrectInputException {
        validator = new InputValidator("..lsdf21-11-1992ds");
        assertTrue(validator.inputCanBeProcessed());

    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void yearWrongPositionTest() throws IncorrectInputException {
        exception.expect(IncorrectInputException.class);
        exception.expectMessage("month must lay between 1 and 12.");
        String userInput = "11-1992-12";
        validator = new InputValidator(userInput);
            validator.inputCanBeProcessed();
    }

    @Test
    public void compositeExceptionTest() throws IncorrectInputException {
        exception.expect(IncorrectInputException.class);
        exception.expectMessage(" month must lay between 1 and 12.\n" +
                "day must lay between 1 and 31.\n" +
                "you could`t be born in that year.");
        String userInput = "32-14-1899";
        validator = new InputValidator(userInput);
        validator.inputCanBeProcessed();
    }


}
