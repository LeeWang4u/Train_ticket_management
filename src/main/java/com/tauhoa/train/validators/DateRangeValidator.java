package com.tauhoa.train.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    private String startField;
    private String endField;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.startField = constraintAnnotation.start();
        this.endField = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field startDateField = value.getClass().getDeclaredField(startField);
            Field endDateField = value.getClass().getDeclaredField(endField);

            startDateField.setAccessible(true);
            endDateField.setAccessible(true);

            String startDateStr = (String) startDateField.get(value);
            String endDateStr = (String) endDateField.get(value);

            if (startDateStr == null || endDateStr == null) {
                return true; // skip validation if one is null, leave to @NotNull
            }

            LocalDate start = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate end = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            return !end.isBefore(start);
        } catch (Exception e) {
            return false;
        }
    }
}
