package core.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;
import core.repository.model.dto.HuntedPlayerDTO;
import core.repository.model.dto.wrapper.HuntedPlayerDTOWrapper;
import core.repository.model.web.Account;
import core.repository.model.web.form.HuntedPlayerForm;
import core.repository.service.AccountService;
import core.repository.service.CrawlerService;
import core.repository.service.exception.AccountDoesNotExistException;
import core.repository.service.exception.PlayerDoesNotExistException;
import core.repository.service.exception.PlayerExistsInHuntedListException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 17/04/2016.
 */
@RestController
public class CrawlerController {

    @Autowired
    CrawlerService crawlerService;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/{server}/onlinePlayers", method = RequestMethod.GET)
    public @ResponseBody String getOnlinePlayers(@PathVariable("server") String serverName) {

        JSONArray jsonOnlinePlayers = new JSONArray();

        List<TibiaPlayer> onlinePlayers = crawlerService.getOnlinePlayers(serverName);

//        List<OlderaPlayer> onlinePlayers = crawlerService.getOnlineOlderaPlayers();
        onlinePlayers.forEach(e -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", e.getName());
            jsonObject.put("level", e.getLevel());
            jsonObject.put("vocation", e.getVocation());
            jsonOnlinePlayers.add(jsonObject);
        });
        return jsonOnlinePlayers.toJSONString();

    }

    @RequestMapping(value = "/{server}/removeHuntedPlayers", method = RequestMethod.POST)
    public ResponseEntity<String> removeHuntedPlayers(Principal user, @RequestBody HuntedPlayerDTOWrapper wrapper/*@RequestBody List<HuntedPlayerDTO> huntedPlayersDTOs*/) {

        JSONObject responseJson = new JSONObject();

        /*String[] huntedPlayersIdsArr = huntedPlayerIds.split(",");*/


        Account account = accountService.findAccount(user.getName());

        if (account == null) {
            responseJson.put("error", "Forbidden");
            responseJson.put("status", 401);
            return ResponseEntity.status(401).body(responseJson.toJSONString());
        }

        /* Insert the account id to all the hunted player dtos */
        List<HuntedPlayerDTO> huntedPlayerDTOs = new ArrayList<>();
        wrapper.getHuntedPlayerDTOList().forEach(e -> {
            HuntedPlayerDTO huntedPlayerDTO = new HuntedPlayerDTO(account.getId(), e.getHuntedPlayerName(), e.getServerName());
            huntedPlayerDTOs.add(huntedPlayerDTO);
        });

        int numberOfDeleted = crawlerService.removeHuntedPlayers(huntedPlayerDTOs);

        if (numberOfDeleted > 0) {
            responseJson.put("numberOfDeleted", numberOfDeleted);
            responseJson.put("status", 200);
            return ResponseEntity.ok().body(responseJson.toJSONString());
        } else {
            responseJson.put("error", "Couldn't delete any hunted player");
            responseJson.put("status", 400);
            return ResponseEntity.status(400).body(responseJson.toJSONString());
        }

        /*responseJson.put("error", "Unknown Error, please try again soon");
        responseJson.put("status", 500);
        return ResponseEntity.status(500).body(responseJson.toJSONString());*/
    }


    @RequestMapping(value = "/{server}/getHuntedList", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getHuntedList(@PathVariable("server") String serverName, Principal user, HttpServletRequest request) {
        JSONArray jsonHuntedList = new JSONArray();

        try {
            List<TibiaPlayer> huntedList = crawlerService.getHuntedList(user.getName(), serverName);
            huntedList.forEach(e -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", e.getName());
                jsonObject.put("level", e.getLevel());
                jsonObject.put("vocation", e.getVocation());
                jsonObject.put("id", e.getId());
                jsonObject.put("isOnline", e.isOnline());
                jsonHuntedList.add(jsonObject);
            });

            return ResponseEntity.ok().body(jsonHuntedList.toJSONString());
        } catch (AccountDoesNotExistException e) {
            JSONObject responseJson = new JSONObject();
            responseJson.put("error", e.getMessage());
            return ResponseEntity.status(401).body(responseJson.toJSONString());
        }
        /*return ResponseEntity.ok().body("");*/
    }

    @RequestMapping(value = "/{server}/addToHuntedList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addToHuntedList(@PathVariable("server") String serverName, Principal user, @Valid @RequestBody HuntedPlayerForm huntedPlayerForm, BindingResult bResult, HttpServletRequest request, WebRequest webRequest) {

        JSONObject responseJson = new JSONObject();

        try {
            TibiaPlayer addedPlayer = crawlerService.addToHuntedList(huntedPlayerForm.getPlayerName(), user.getName(), serverName);
            if (addedPlayer != null) {
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = null;
                try {
                    jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(addedPlayer);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return ResponseEntity.ok(jsonString);
            }
        } catch (PlayerExistsInHuntedListException e) {
            responseJson.put("error", e.getMessage());
            return ResponseEntity.status(400).body(responseJson.toJSONString());
        } catch (PlayerDoesNotExistException e) {
            responseJson.put("error", e.getMessage());
            return ResponseEntity.status(400).body(responseJson.toJSONString());
        } catch (AccountDoesNotExistException e) {
            responseJson.put("error", e.getMessage());
            return ResponseEntity.status(401).body(responseJson.toJSONString());
        }

        responseJson.put("error", "Unknown Error, please try again soon");
        responseJson.put("status", 500);
        return ResponseEntity.status(500).body(responseJson.toJSONString());
    }
}
