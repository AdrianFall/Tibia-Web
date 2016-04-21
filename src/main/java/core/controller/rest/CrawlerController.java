package core.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.service.CrawlerService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Adrian on 17/04/2016.
 */
@RestController
public class CrawlerController {

    @Autowired
    CrawlerService crawlerService;

    @RequestMapping(value = "/oldera/onlinePlayers", method = RequestMethod.GET)
    public @ResponseBody String olderaOnlinePlayers() {

        JSONArray jsonOnlinePlayers = new JSONArray();

        List<OlderaPlayer> onlineOlderaplayers = crawlerService.getOnlineOlderaPlayers();
        onlineOlderaplayers.forEach(e -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", e.getName());
            jsonObject.put("level", e.getLevel());
            jsonObject.put("vocation", e.getVocation());
            jsonOnlinePlayers.add(jsonObject);
        });





        /*
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(crawlerService.getOnlineOlderaPlayers());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/

        return jsonOnlinePlayers.toJSONString();

    }
}
