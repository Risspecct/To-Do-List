package users.rishik.toDoList.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import users.rishik.toDoList.validators.NullOrNotBlankValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrNotBlank {
    String message() default "must be null or a non-blank string";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

