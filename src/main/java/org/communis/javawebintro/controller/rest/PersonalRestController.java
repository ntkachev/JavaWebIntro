package org.communis.javawebintro.controller.rest;

import org.communis.javawebintro.dto.UserPasswordWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.exception.InvalidDataException;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class PersonalRestController {

    private final PersonalService personalService;

    @Autowired
    public PersonalRestController(PersonalService personalService) {
        this.personalService = personalService;
    }

    @RequestMapping(value = "/my", method = RequestMethod.POST)
    public void editPersonal(@Valid UserWrapper userWrapper, BindingResult bindingResult,
                             HttpServletResponse response)
            throws InvalidDataException, IOException, ServerException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        response.sendRedirect("/my/");
        personalService.edit(userWrapper);
    }

    @RequestMapping(value = "/my/password", method = RequestMethod.POST)
    public void changePassword(@Valid UserPasswordWrapper passwordWrapper, BindingResult bindingResult,
                               HttpServletResponse response)
            throws ServerException, IOException, InvalidDataException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        response.sendRedirect("/my/");
        personalService.changePassword(passwordWrapper);
    }
}
