package com.kodilla.hibernate.manytomany.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Validation {
    private static final Logger LOGGER = LoggerFactory.getLogger(Validation.class);

    public String validate(String partLastName) throws ValidationException{
        if(partLastName.length() < 3) {
            LOGGER.error(ValidationException.VALIDATE_ERROR);
            throw new ValidationException(ValidationException.VALIDATE_ERROR);
        }
        return "%" + partLastName + "%";
    }
}
