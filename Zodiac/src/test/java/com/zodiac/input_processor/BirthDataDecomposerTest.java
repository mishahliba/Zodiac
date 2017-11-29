package com.zodiac.input_processor;


import com.zodiac.client.input_processor.BirthDataDecomposer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;



public class BirthDataDecomposerTest {

    BirthDataDecomposer dataDecomposer = new BirthDataDecomposer();

    @Test
    public void removeExtraSymbolsTest(){
        String clearedInput = dataDecomposer.removeExtraSymbols("ljzxfdsj..]]]21-11-1992/..");
        assertEquals("21-11-1992", clearedInput);
    }

    @Test
    public void errorMessageWhenExtraDigitsPresetTest(){
        String wrongInputExtraDigits="21-11-1992-4243";
        List<String> error = dataDecomposer.layOutBirthData(wrongInputExtraDigits, new ArrayList<>());
        assertEquals("wrong birth date format.", error.get(0));
    }

    @Test
    public void errorCountWhenExtraDigitsPresetTest(){
        String wrongInputExtraDigits="21-11-1992-4243";
        List<String> error = dataDecomposer.layOutBirthData(wrongInputExtraDigits, new ArrayList<>());
        assertEquals(1, error.size());
    }
}
