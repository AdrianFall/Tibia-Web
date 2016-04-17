package core.controller.rest;

import core.repository.service.CrawlerService;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Adrian on 17/04/2016.
 */
@RestController
public class CrawlerController {

    @Autowired
    CrawlerService crawlerService;

    @RequestMapping(value = "/oldera/onlinePlayers", method = RequestMethod.GET)
    public String olderaOnlinePlayers() {
        return JSONArray.toJSONString(crawlerService.getOnlineOlderaPlayers());

    }
}
