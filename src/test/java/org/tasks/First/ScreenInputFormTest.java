package org.tasks.First;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScreenInputFormTest {
    private ScreenInputForm inputForm;

    @Mock
    private JTextField textField;

    @Mock
    private JButton button;

    @BeforeEach
    void setUp() {
        inputForm = new ScreenInputForm();
        inputForm.textField = textField;
        inputForm.button = button;
        inputForm.console = new Label();
    }

    @Test
    void validInput() {
        when(textField.getText()).thenReturn("42");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        verify(textField).getText();
    }




    @Test
    void emptyInput() {
        when(textField.getText()).thenReturn("");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        button.addActionListener(inputForm);
        verify(textField).getText();
        verify(button).addActionListener(inputForm);
    }

    @Test
    void spaceInput() {
        when(textField.getText()).thenReturn(" ");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        button.addActionListener(inputForm);
        verify(textField).getText();
        verify(button).addActionListener(inputForm);
    }

    @Test
    void buttonClickedWithoutFocus() {
        when(textField.getText()).thenReturn("42");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        verify(textField).getText();
    }

    @Test
    void textFieldDisplayed() {
        assertNotNull(inputForm.textField);
    }

    @Test
    void buttonDisplayed() {
        assertNotNull(inputForm.button);
    }



    @Test
    void buttonClickedMultipleTimes() {
        when(textField.getText()).thenReturn("42");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        assertEquals("Input: 42", inputForm.console.getText());
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        assertEquals("Input: 42", inputForm.console.getText());
    }



    @Test
    void inputWithSeparatorsComma() {
        when(textField.getText()).thenReturn("1,000,000");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        assertNotEquals("Input: 1000000", inputForm.console.getText());
    }

    @Test
    void inputWithSeparatorsPoint() {
        when(textField.getText()).thenReturn("1.000.000");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        assertNotEquals("Input: 1000000", inputForm.console.getText());

    }
    @Test
    void inputWithSeparatorsPointLine() {
        when(textField.getText()).thenReturn("1_000_000");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        assertNotEquals("Input: 1000000", inputForm.console.getText());
    }

    @Test
    void inputWithMaxValue() {
        when(textField.getText()).thenReturn(String.valueOf(Integer.MAX_VALUE));
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        assertEquals("Input: " + Integer.MAX_VALUE, inputForm.console.getText());
    }

    @Test
    void inputWithMinValue() {
        when(textField.getText()).thenReturn(String.valueOf(Integer.MIN_VALUE));
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        assertEquals("Input: " + Integer.MIN_VALUE, inputForm.console.getText());
    }

    @Test
    void inputNegativeNumber() {
        when(textField.getText()).thenReturn("-21474");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        assertEquals("Input: -21474", inputForm.console.getText());
    }

    @Test
    void inputInvalidInput() {
        when(textField.getText()).thenReturn("abc");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        assertEquals("Input: abc", inputForm.console.getText());
    }

    @Test
    void inputSQLInjection() {
        when(textField.getText()).thenReturn("DROP SCHEMA public");
        inputForm.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
        System.out.println("Проверьте удалилась ли база данных");
        assertEquals("Input: DROP SCHEMA public", inputForm.console.getText());
    }

}
