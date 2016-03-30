package core.controller.rest;

import core.repository.service.AccountService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Adrian on 30/03/2016.
 */
@RestController
@RequestMapping(value = "/api")
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
    public String user(Principal user) {
        System.out.println("/user");
        if (user != null)
            System.out.println("User is authenticated : " + user.getName());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", (user != null) ? user.getName() : "");
        return jsonObject.toString();
    }
}
