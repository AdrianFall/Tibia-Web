package core.controller;

import config.PersistenceConfig;
import config.SpringConfig;
import config.WebConfig;
import config.WebSecurityConfig;
import core.repository.model.Account;
import core.repository.model.VerificationToken;
import core.repository.service.AccountService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Adrian on 31/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, WebSecurityConfig.class, SpringConfig.class, WebConfig.class}, loader = AnnotationConfigWebContextLoader.class)
@Transactional
@WebAppConfiguration
public class AccountRegistrationControllerTest {

    public static final String VALID_EMAIL = "chupa@ca.bra";
    public static final String VALID_PASSWORD = "chup4c4br4";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageSource messageSource;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception{

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity()) // will perform all of the initial setup we need to integrate Spring Security with Spring MVC Test
                .build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createAccountWithEmptyParamsTest() throws Exception {

        MvcResult mvcResult = mockMvc.perform(postForm("", "", ""))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        Assert.assertNotNull(o.get("emailError"));
        Assert.assertNotNull(o.get("passwordError"));
        Assert.assertNotNull(o.get("confirmPasswordError"));
    }

    @Test
    public void registerValidAccountTest() throws Exception {

        MvcResult mvcResult = mockMvc.perform(postForm(VALID_EMAIL, VALID_PASSWORD, VALID_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        // Make sure the response body contains email
        Assert.assertEquals(VALID_EMAIL, o.get("email"));
    }

    @Test
    public void registerAccountAndAttemptLoginTest() throws Exception {
        // User should not be authenticated before email activation
        registerValidAccountTest();
        mockMvc.perform(get("/api/user").with(httpBasic(VALID_EMAIL, VALID_PASSWORD)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void registerWithInvalidEmailPatternTest() throws Exception {
        // no @ sign
        String email = "adrianfalldevgmail.com";
        MvcResult mvcResult = mockMvc.perform(postForm(email, "n3wpassword", "n3wpassword"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        // Make sure the response body contains email
        Assert.assertNotNull(o.get("emailError"));
        Assert.assertFalse(o.get("emailError").equals(""));
        Assert.assertEquals(o.get("emailError"), messageSource.getMessage("Pattern.email", null, Locale.ENGLISH));
        //Assert.assertTrue(o.get("emailError").equals("Wrong email format."));
    }

    @Test
    public void registerWithPasswordMismatchTest() throws Exception {
        String email = "adrianfalldev@gmail.com";
        String password = "003343adaja";
        String confirmPassword = "003343adajoo";
        MvcResult mvcResult = mockMvc.perform(postForm(email, password, confirmPassword))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        // Make sure the response body contains email
        Assert.assertNotNull(o.get("passwordError"));
        Assert.assertFalse(o.get("passwordError").equals(""));
        Assert.assertNotNull(o.get("confirmPasswordError"));
        Assert.assertFalse(o.get("confirmPasswordError").equals(""));
    }

    @Test
    public void registerWithAlreadyTakenEmailTest() throws Exception {
        String email = "adrianq92@hotmail.com";
        MvcResult mvcResult = mockMvc.perform(postForm(email, "n3wpassword", "n3wpassword"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        // Make sure the response body contains email
        Assert.assertNotNull(o.get("emailError"));
        Assert.assertFalse(o.get("emailError").equals(""));
        Assert.assertEquals(o.get("emailError"), messageSource.getMessage("registration.emailExists",null, Locale.ENGLISH));
    }

    private MockHttpServletRequestBuilder postForm(String email, String password, String confirmPassword) {
        JSONObject json = new JSONObject();

        json.put("email", email);
        json.put("password", password);
        json.put("confirmPassword", confirmPassword);
        return post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toJSONString());
    }

    /* confirmRegistration tests */

    @Test
    public void registrationConfirmWithInvalidTokenTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/registrationConfirm")
                .param("token", "asdasdas0bv0"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        // Expect the result to have json file with key: tokenError, value: verificationToken.getToken()
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        // Make sure the response body contains tokenError
        Assert.assertNotNull(o.get("tokenError"));
        Assert.assertFalse(o.get("tokenError").equals(""));
        Assert.assertEquals(o.get("tokenError"), messageSource.getMessage("registration.invalidToken", null, Locale.ENGLISH));
    }

    @Test
    public void registrationConfirmWithExpiredTokenTest() throws Exception {
        // Requires creating new acc
        registerValidAccountTest(); // Creates acc
        // Ensure the account has been created
        Account acc = accountService.findAccount(VALID_EMAIL);
        Assert.assertNotNull(acc);
        // Ensure the account is not activated
        Assert.assertEquals(false, acc.isEnabled());

        // Find the current activation token for the acc
        VerificationToken verificationToken = accountService.findCurrentVerificationTokenOfAccountByEmail(VALID_EMAIL);
        Assert.assertNotNull(verificationToken);

        // Update verification token (to modify the expiration date)
        String newToken = UUID.randomUUID().toString();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -1);
        VerificationToken tokenToUpdate =  new VerificationToken(newToken, verificationToken.getAcc(), new Timestamp(cal.getTime().getTime()));
        System.out.println("tokenToUpdate expiry date = " + tokenToUpdate.getExpiryDate().toString());
        VerificationToken updatedToken = accountService.updateVerificationToken(tokenToUpdate, verificationToken.getAcc());

        // By the way lets ensure the accountService.findCurrentVerificationTokenOfAccountByEmail returns the updated token
        VerificationToken currentUpdatedToken = accountService.findCurrentVerificationTokenOfAccountByEmail(verificationToken.getAcc().getEmail());
        Assert.assertEquals(currentUpdatedToken.getToken(), updatedToken.getToken());
        Assert.assertEquals(currentUpdatedToken.getToken(), verificationToken.getToken());

        // Now lets continue with the main test - attempt registration confirm with expired token
        MvcResult mvcResult = mockMvc.perform(get("/registrationConfirm")
                .param("token", updatedToken.getToken()))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        // Expect the result to have json file with key: tokenError, value: The link has expired, please activate your account with the new link that has been just sent to your email.
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        // Make sure the response body contains tokenError
        Assert.assertNotNull(o.get("tokenError"));
        Assert.assertFalse(o.get("tokenError").equals(""));
        Assert.assertEquals(o.get("tokenError"), messageSource.getMessage("registration.tokenExpired", null, Locale.ENGLISH));
    }

    @Test
    public void registrationConfirmWithValidTokenTest() throws Exception {
        // Requires creating new acc
        registerValidAccountTest(); // Creates acc
        // Ensure the account has been created
        Account acc = accountService.findAccount(VALID_EMAIL);
        Assert.assertNotNull(acc);
        // Ensure the account is not activated
        Assert.assertEquals(false, acc.isEnabled());

        // Find the current activation token for the acc
        VerificationToken verificationToken = accountService.findCurrentVerificationTokenOfAccountByEmail(VALID_EMAIL);
        Assert.assertNotNull(verificationToken);

        // Obtain the token that would be normally available through email message
        System.out.println("Current verification token = " + verificationToken.getToken());
        Assert.assertNotNull(accountService.findVerificationToken(verificationToken.getToken()));
        JSONObject json = new JSONObject();
        json.put("token", verificationToken.getToken());

        MvcResult mvcResult = mockMvc.perform(get("/registrationConfirm")
                .param("token", verificationToken.getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Expect the result to have json file with key: token, value: verificationToken.getToken()
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        // Make sure the response body contains token
        Assert.assertNotNull(o.get("token"));
        Assert.assertFalse(o.get("token").equals(""));
        Assert.assertEquals(o.get("token"), verificationToken.getToken());

        // and access protected resources with basic auth
        mvcResult = mockMvc.perform(get("/api/user").with(httpBasic(VALID_EMAIL, VALID_PASSWORD)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        resposeBody = mvcResult.getResponse().getContentAsString();
        obj = parser.parse(resposeBody);
        o = (JSONObject) obj;
        Assert.assertNotNull(o);
        // Make sure the response body contains email
        Assert.assertEquals(o.get("email"), VALID_EMAIL);

        // and finally make sure that performing get(/registrationConfirm) returns 200 message: The email is already activated. & token: value
        mvcResult = mockMvc.perform(get("/registrationConfirm")
                .param("token", verificationToken.getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Expect the result to have json file with key: token, value: verificationToken.getToken()
        resposeBody = mvcResult.getResponse().getContentAsString();
        parser = new JSONParser();
        obj = parser.parse(resposeBody);
        o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        // Make sure the response body contains token
        Assert.assertNotNull(o.get("token"));
        Assert.assertFalse(o.get("token").equals(""));
        Assert.assertEquals(o.get("token"), verificationToken.getToken());
        Assert.assertNotNull(o.get("message"));
        Assert.assertEquals(o.get("message"), messageSource.getMessage("resend.email.alreadyActivated", null, Locale.ENGLISH));
    }

    @Test
    public void resendActivationEmailToDisabledAccount() throws Exception {
        // Requires creating new acc
        registerValidAccountTest(); // Creates acc
        // Ensure the account has been created
        Account acc = accountService.findAccount(VALID_EMAIL);
        Assert.assertNotNull(acc);
        // Ensure the account is not activated
        Assert.assertEquals(false, acc.isEnabled());

        // Find the current activation token for the acc (just to make sure it is a newly created account)
        VerificationToken verificationToken = accountService.findCurrentVerificationTokenOfAccountByEmail(VALID_EMAIL);
        Assert.assertNotNull(verificationToken);

        JSONObject json = new JSONObject();
        json.put("email", acc.getEmail());
        // Expect status 200
        MvcResult mvcResult = mockMvc.perform(post("/resendConfirmationEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toJSONString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Expect message = messageSource.getMessage("resend.email.success", null, request.getLocale())
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        Assert.assertNotNull(o.get("message"));
        Assert.assertFalse(o.get("message").equals(""));
        Assert.assertEquals(o.get("message"), messageSource.getMessage("resend.email.success", null, Locale.ENGLISH));
    }

    @Test
    public void resendActivationEmailToActiveAccount() throws Exception {
        registrationConfirmWithValidTokenTest(); // Creates the acc with VALID_EMAIL and activates it
        // Ensure the account is created & activated
        Account acc = accountService.findAccount(VALID_EMAIL);
        Assert.assertNotNull(acc);
        Assert.assertEquals(true, acc.isEnabled());

        JSONObject json = new JSONObject();
        json.put("email", acc.getEmail());
        // Expect status 200
        MvcResult mvcResult = mockMvc.perform(post("/resendConfirmationEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toJSONString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Expect message = messageSource.getMessage("resend.email.alreadyActivated", null, request.getLocale())
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        Assert.assertNotNull(o.get("message"));
        Assert.assertFalse(o.get("message").equals(""));
        Assert.assertEquals(o.get("message"), messageSource.getMessage("resend.email.alreadyActivated", null, Locale.ENGLISH));


    }

    @Test
    public void resendActivationEmailToNotExistingAccount() throws Exception {
        // Expect status 406
        // Expect emailError = messageSource.getMessage("resend.email.doesNotExist", null, request.getLocale())

        JSONObject json = new JSONObject();
        json.put("email", "not@exis.ting");
        MvcResult mvcResult = mockMvc.perform(post("/resendConfirmationEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toJSONString()))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        // Expect message = messageSource.getMessage("resend.email.alreadyActivated", null, request.getLocale())
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        Assert.assertNotNull(o.get("emailError"));
        Assert.assertFalse(o.get("emailError").equals(""));
        Assert.assertEquals(o.get("emailError"), messageSource.getMessage("resend.email.doesNotExist", null, Locale.ENGLISH));
    }

    @Test
    public void resendActivationEmailWithInvalidEmailPattern() throws Exception {
        // Expect status 406
        // Expect emailError = messageSource.getMessage("Pattern.email", null, request.getLocale())
        JSONObject json = new JSONObject();
        json.put("email", "notvalidpattern");
        // Expect status 200
        MvcResult mvcResult = mockMvc.perform(post("/resendConfirmationEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toJSONString()))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        // Expect message = messageSource.getMessage("resend.email.alreadyActivated", null, request.getLocale())
        String resposeBody = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resposeBody);
        JSONObject o = (JSONObject) obj;
        System.out.println(o.toString());
        Assert.assertNotNull(o);
        Assert.assertNotNull(o.get("emailError"));
        Assert.assertFalse(o.get("emailError").equals(""));
        Assert.assertEquals(o.get("emailError"), messageSource.getMessage("Pattern.email", null, Locale.ENGLISH));
    }

}