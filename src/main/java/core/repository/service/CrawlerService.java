package core.repository.service;

import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;
import core.repository.model.dto.HuntedPlayerDTO;
import core.repository.service.exception.AccountDoesNotExistException;
import core.repository.service.exception.PlayerDoesNotExistException;
import core.repository.service.exception.PlayerExistsInHuntedListException;

import java.util.List;

/**
 * Created by Adrian on 17/04/2016.
 */
public interface CrawlerService {
    TibiaPlayer addToHuntedList(String playerName, String accountEmail, String serverName) throws PlayerExistsInHuntedListException, PlayerDoesNotExistException, AccountDoesNotExistException;

    List<TibiaPlayer> getOnlinePlayers(String serverName);

    List<TibiaPlayer> getHuntedList(String accountEmail, String serverName) throws AccountDoesNotExistException;

    Integer removeHuntedPlayers(String[] huntedPlayerIdsArr);
    /* returns amount of deleted hunted players*/
    Integer removeHuntedPlayers(List<HuntedPlayerDTO> huntedPlayerDTOs);
}
