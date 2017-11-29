package com.zodiac.client;

import com.zodiac.client.enums.ZodiacSigns;
import com.zodiac.client.exceptions.IncorrectInputException;
import com.zodiac.client.input_processor.InputValidator;
import com.zodiac.client.model.ValidInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Scanner;

public class Client {

    static Logger log = LoggerFactory.getLogger(Client.class);
    private static InputValidator userInputValidator;

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        String line;
        do{
        line = s.nextLine();
            log.info("got user input: {} ", line);
            System.out.println("press '0' to exit");
            userInputValidator = new InputValidator(line);
            try {
                boolean inputCorrect = userInputValidator.inputCanBeProcessed();
                if(inputCorrect){
                    ValidInput zodiacInput = userInputValidator.getValidInput();
                    ZodiacSigns userZodiacSign = ZodiacSigns.getZodiac(zodiacInput.getMonth(), zodiacInput.getDay());
                    System.out.println("Thanks, you zodiac is " + userZodiacSign + "(" +
                            userZodiacSign.getTranslatedSign() + ")");
                }
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            }
        }while (!line.equals("0"));
    }
}
