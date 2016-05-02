package core.repository;

import core.repository.model.crawler.TibiaHuntedPlayer;
import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.TibiaServer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;

import java.util.List;

/**
 * Created by Adrian on 24/04/2016.
 */
public interface CrawlerRepo {

    TibiaPlayer findTibiaPlayer(String playerName, String serverName);

    TibiaPlayer addToHuntedList(Long playerId, Long accountId, String serverName);


    List<TibiaServer> getServers();
    TibiaServer findTibiaServer(Long serverId);

    List<TibiaPlayer> getOnlinePlayers(String serverName);
    Integer getOnlinePlayersCount(String serverName);

    List<TibiaPlayer> getHuntedList(Long accountId, String serverName);
    TibiaPlayer findHuntedPlayer(Long playerId, Long accountId, String serverName);
    TibiaHuntedPlayer findHuntedPlayer(Long huntedPlayerId);
    void removeHuntedPlayer(TibiaHuntedPlayer huntedPlayer);
    TibiaHuntedPlayer findHuntedPlayer(Long accountId, String huntedPlayerName, String serverName);



}
