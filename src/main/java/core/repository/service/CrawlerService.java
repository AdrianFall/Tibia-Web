package core.repository.service;

import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;
import core.repository.service.exception.AccountDoesNotExistException;
import core.repository.service.exception.PlayerDoesNotExistException;
import core.repository.service.exception.PlayerExistsInHuntedListException;

import java.util.List;

/**
 * Created by Adrian on 17/04/2016.
 */
public interface CrawlerService {
    public List<OlderaPlayer> getOnlineOlderaPlayers();
    public List<ThroniaPlayer> getOnlineThroniaPlayers();
    public OlderaPlayer  addToOlderaHuntedList(String playerName, String accountEmail) throws PlayerExistsInHuntedListException, PlayerDoesNotExistException, AccountDoesNotExistException;
    public ThroniaPlayer addToThroniaHuntedList(String playerName, String accountEmail) throws PlayerExistsInHuntedListException, PlayerDoesNotExistException, AccountDoesNotExistException;

    List<OlderaPlayer> getOlderaHuntedList(String accountEmail) throws AccountDoesNotExistException;

    List<ThroniaPlayer> getThroniaHuntedList(String accountEmail) throws AccountDoesNotExistException;

    List<TibiaPlayer> getOnlinePlayers(String serverName);

    List<TibiaPlayer> getHuntedList(String accountEmail, String serverName) throws AccountDoesNotExistException;
}
