package com.zodiac.client.exceptions;

/**
 * Exception is thrown if user input cannot be parsed or contains some logical mistakes
 */

public class IncorrectInputException extends Throwable {
    public IncorrectInputException(String message) {
        super(message);
    }
}
