package com.bionic.fishtrading.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "confirmPasswordValidator")
public class ConfirmPasswordValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		String field = (String) value;
		String confirm = (String) component.getAttributes().get(
				"confirmPassword");

		if (field == null || confirm == null) {
			return; // Just ignore and let required="true" do its job.
		}

		if (!field.equals(confirm)) {
			throw new ValidatorException(new FacesMessage(
					"Passwords are not equal."));
		}
	}

}
