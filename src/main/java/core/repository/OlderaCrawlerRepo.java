package core.repository;

import core.repository.model.crawler.servers.oldera.OlderaPlayer;

import java.util.List;

/**
 * Created by Adrian on 17/04/2016.
 */
public interface OlderaCrawlerRepo {
    public List<OlderaPlayer> getOnlinePlayers();
}
