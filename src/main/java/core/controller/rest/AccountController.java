package core.controller.rest;

import core.repository.model.Account;
import core.repository.model.form.RegistrationForm;
import core.repository.service.AccountService;
import core.repository.service.EmailService;
import core.repository.service.event.OnRegistrationCompleteEvent;
import core.repository.service.exception.EmailExistsException;
import core.repository.service.exception.EmailNotSentException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by Adrian on 30/03/2016.
 */
@RestController
public class AccountController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    EmailService emailService;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/api/user", method = RequestMethod.GET, produces = "application/json")
    public String user(Principal user) {
        System.out.println("/user");
        if (user != null)
            System.out.println("User is authenticated : " + user.getName());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", (user != null) ? user.getName() : "");
        return jsonObject.toString();
    }
}
