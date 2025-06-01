package sn.ism.gestion.utils.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class ListNotEmptyValidator implements ConstraintValidator<ListNotEmpty, List<?>> {

    @Override
    public void initialize(ListNotEmpty constraintAnnotation) {
        // initialisation si nécessaire
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        // Considère comme valide si la liste est non nulle et non vide
        return value != null && !value.isEmpty();
    }
}
