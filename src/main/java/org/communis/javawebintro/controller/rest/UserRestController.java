package org.communis.javawebintro.controller.rest;

import org.communis.javawebintro.dto.UserPasswordWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.exception.InvalidDataException;
import org.communis.javawebintro.exception.NotFoundException;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "admin/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(@Valid UserWrapper userWrapper, BindingResult bindingResult, HttpServletResponse response)
            throws InvalidDataException, IOException, ServerException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        response.sendRedirect("/admin/users/");
        userService.add(userWrapper);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public void editPersonal(@Valid UserWrapper userWrapper, BindingResult bindingResult, HttpServletResponse response)
            throws InvalidDataException, IOException, NotFoundException, ServerException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        response.sendRedirect("/admin/users/");
        userService.edit(userWrapper);
    }

    @RequestMapping(value = "/edit/password", method = RequestMethod.POST)
    public void changePassword(@Valid UserPasswordWrapper passwordWrapper, BindingResult bindingResult,
                               HttpServletResponse response)
            throws InvalidDataException, IOException, NotFoundException, ServerException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        response.sendRedirect("/admin/users/");
        userService.changePassword(passwordWrapper);
    }

    @RequestMapping(value = "/{id}/block", method = RequestMethod.POST)
    public void block(@PathVariable("id") Long id)
            throws InvalidDataException, ServerException, NotFoundException {
        userService.block(id);
    }

    @RequestMapping(value = "/{id}/unblock", method = RequestMethod.POST)
    public void unblock(@PathVariable("id") Long id) throws NotFoundException, ServerException {
        userService.unblock(id);
    }
}
