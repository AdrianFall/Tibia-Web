package core.repository;

import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;

import java.util.List;

/**
 * Created by Adrian on 24/04/2016.
 */
public interface CrawlerRepo {
    List<OlderaPlayer> getOlderaOnlinePlayers();
    List<ThroniaPlayer> getThroniaOnlinePlayers();

    TibiaPlayer findTibiaPlayer(String playerName, String serverName);

    OlderaPlayer findOlderaHuntedPlayer(Long playerId, Long accountId);
    ThroniaPlayer findThroniaHuntedPlayer(Long playerId, Long accountId);

    OlderaPlayer addToOlderaHuntedList(Long playerId, Long accountId);
    ThroniaPlayer addToThroniaHuntedList(Long playerId, Long accountId);

    boolean removeFromOlderaHuntedList(Long playerId, Long accountId);
    boolean removeFromThroniaHuntedList(Long playerId, Long accountId);

    List<OlderaPlayer> getOlderaHuntedList(Long accountId, String serverName);
    List<ThroniaPlayer> getThroniaHuntedList(Long accountId, String serverName);

    List<TibiaPlayer> getOnlinePlayers(String serverName);

    List<TibiaPlayer> getHuntedList(Long accountId, String serverName);

    TibiaPlayer findHuntedPlayer(Long playerId, Long accountId, String serverName);
}
