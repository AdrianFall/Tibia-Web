package core.repository;

import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;

import java.util.List;

/**
 * Created by Adrian on 24/04/2016.
 */
public interface CrawlerRepo {

    TibiaPlayer findTibiaPlayer(String playerName, String serverName);

    TibiaPlayer addToHuntedList(Long playerId, Long accountId, String serverName);

    boolean removeFromOlderaHuntedList(Long playerId, Long accountId);
    boolean removeFromThroniaHuntedList(Long playerId, Long accountId);


    List<TibiaPlayer> getOnlinePlayers(String serverName);

    List<TibiaPlayer> getHuntedList(Long accountId, String serverName);

    TibiaPlayer findHuntedPlayer(Long playerId, Long accountId, String serverName);
}
