package vietquan37.com.example.projects.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueElementsListValidator implements ConstraintValidator<UniqueElements, List<?>> {

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        Set<Object> set = new HashSet<>(value);
        return set.size() == value.size();
    }
}
