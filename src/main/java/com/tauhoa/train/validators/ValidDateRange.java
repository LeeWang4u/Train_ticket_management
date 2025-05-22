package com.tauhoa.train.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface ValidDateRange {
    String message() default "Invalid date range";

    String start();
    String end();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
