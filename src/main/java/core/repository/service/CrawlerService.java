package core.repository.service;

import core.repository.model.crawler.servers.oldera.OlderaPlayer;

import java.util.List;

/**
 * Created by Adrian on 17/04/2016.
 */
public interface CrawlerService {
    public List<OlderaPlayer> getOnlineOlderaPlayers();
}
