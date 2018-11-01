package com.example.personlist.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.personlist.model.AddressInfo;
import com.example.personlist.model.PersonInfo;

public class CustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return PersonInfo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		PersonInfo info = (PersonInfo) target;
		
		// infoのLastName値が　3 ~ 12　確認
		if(info.getLastName().length() < 3 || info.getLastName().length() > 12){  
			System.out.println("文字は 3　~ 12.");
            errors.rejectValue("LastName", "lengthsize");
        }	
		
	}
}
