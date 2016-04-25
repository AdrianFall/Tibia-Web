package core.repository;

import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;

import java.util.List;

/**
 * Created by Adrian on 24/04/2016.
 */
public interface CrawlerRepo {
    public List<OlderaPlayer> getOlderaOnlinePlayers();
    public List<ThroniaPlayer> getThroniaOnlinePlayers();

    public TibiaPlayer findTibiaPlayer(String playerName, String serverName);

    public OlderaPlayer findOlderaHuntedPlayer(Long playerId, Long accountId);
    public ThroniaPlayer findThroniaHuntedPlayer(Long playerId, Long accountId);

    public OlderaPlayer addToOlderaHuntedList(Long playerId, Long accountId);
    public ThroniaPlayer addToThroniaHuntedList(Long playerId, Long accountId);

    public boolean removeFromOlderaHuntedList(Long playerId, Long accountId);
    public boolean removeFromThroniaHuntedList(Long playerId, Long accountId);

    List<OlderaPlayer> getOlderaHuntedList(Long accountId, String serverName);
    List<ThroniaPlayer> getThroniaHuntedList(Long accountId, String serverName);
}
