package core.repository.service.impl;

import core.repository.AccountRepo;
import core.repository.CrawlerRepo;
import core.repository.model.crawler.TibiaHuntedPlayer;
import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.TibiaServer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;
import core.repository.model.dto.HuntedPlayerDTO;
import core.repository.model.dto.ServerDTO;
import core.repository.model.web.Account;
import core.repository.service.CrawlerService;
import core.repository.service.exception.AccountDoesNotExistException;
import core.repository.service.exception.PlayerDoesNotExistException;
import core.repository.service.exception.PlayerExistsInHuntedListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Adrian on 17/04/2016.
 */
@Service
@Transactional
public class CrawlerServiceImpl implements CrawlerService {

    static Logger logger = Logger.getLogger(CrawlerServiceImpl.class.getName());

    @Autowired
    CrawlerRepo crawlerRepo;

    @Autowired
    AccountRepo accountRepo;

    @Override
    public List<TibiaPlayer> getOnlinePlayers(String serverName) {
        return crawlerRepo.getOnlinePlayers(serverName.substring(0, 1).toUpperCase() + serverName.substring(1));
    }

    @Override
    public TibiaPlayer addToHuntedList(String playerName, String accountEmail, String serverName) throws PlayerExistsInHuntedListException, PlayerDoesNotExistException, AccountDoesNotExistException {
        // Find player in the database
        TibiaPlayer player = crawlerRepo.findTibiaPlayer(playerName, serverName.substring(0, 1).toUpperCase() + serverName.substring(1));
        if (player == null) {
            throw new PlayerDoesNotExistException("Player does not exist.");
        }

        Account acc = accountRepo.findAccountByEmail(accountEmail);
        if (acc == null) {
            throw new AccountDoesNotExistException("Account does not exist");
        }

        if (crawlerRepo.findHuntedPlayer(player.getId(), acc.getId(), serverName.substring(0, 1).toUpperCase() + serverName.substring(1)) != null) {
            throw new PlayerExistsInHuntedListException("Player already exists");
        }
        return crawlerRepo.addToHuntedList(player.getId(), acc.getId(), serverName.substring(0, 1).toUpperCase() + serverName.substring(1));
    }


    @Override
    public List<TibiaPlayer> getHuntedList(String accountEmail, String serverName) throws AccountDoesNotExistException {
        Account acc = accountRepo.findAccountByEmail(accountEmail);

        if (acc == null) {
            throw new AccountDoesNotExistException("Account does not exist");
        }

        return crawlerRepo.getHuntedList(acc.getId(), serverName.substring(0, 1).toUpperCase() + serverName.substring(1));
    }

    /* Returns the amount of players deleted from db */
    @Override
    public Integer removeHuntedPlayers(String[] huntedPlayerIdsArr) {
        Long[] huntedPlayersArr = new Long[huntedPlayerIdsArr.length];

        for (int i = 0; i < huntedPlayerIdsArr.length; i++) {
            huntedPlayersArr[i] = Long.getLong(huntedPlayerIdsArr[i]);
        }

        // Iterate over array and remove one by one
        int numberOfRemovedPlayers = 0;
        for (Long id : huntedPlayersArr) {

            TibiaHuntedPlayer huntedPlayer = crawlerRepo.findHuntedPlayer(id);

            if (huntedPlayer != null) {
                crawlerRepo.removeHuntedPlayer(huntedPlayer);
                numberOfRemovedPlayers++;
            }
        }
        return numberOfRemovedPlayers;
    }

    @Override
    public Integer removeHuntedPlayers(List<HuntedPlayerDTO> huntedPlayerDTOs) {

        int countRemovedPlayers = 0;

        for (HuntedPlayerDTO huntedPlayerDTO : huntedPlayerDTOs) {
            logger.info("Finding hunted player by accId: " + huntedPlayerDTO.getAccountId() + " huntedPlayerName: " + huntedPlayerDTO.getHuntedPlayerName() + " serverName " + huntedPlayerDTO.getServerName());
            TibiaHuntedPlayer huntedPlayer = crawlerRepo.findHuntedPlayer(huntedPlayerDTO.getAccountId(), huntedPlayerDTO.getHuntedPlayerName(), huntedPlayerDTO.getServerName());

            if (huntedPlayer != null) {
                crawlerRepo.removeHuntedPlayer(huntedPlayer);
                countRemovedPlayers++;
                System.out.println(countRemovedPlayers);
            }
        }

        return countRemovedPlayers;
    }

    @Override
    public List<ServerDTO> getServerDTOs() {

        List<TibiaServer> tibiaServers = crawlerRepo.getServers();
        logger.info("getServerDTOs -> obtained " + tibiaServers.size() + " servers");


        List<ServerDTO> serverDTOs = new ArrayList<>();

        for (TibiaServer server : tibiaServers) {
            // Get number of players online
            Integer onlinePlayerCount = crawlerRepo.getOnlinePlayersCount(server.getName());
            int numberOfPlayersOnline = (onlinePlayerCount != null) ? onlinePlayerCount : 0;
            ServerDTO serverDTO = new ServerDTO(server.isOnline(), server.getName(), numberOfPlayersOnline);
            serverDTOs.add(serverDTO);
        }

        logger.info("getServerDTOs -> returning " + serverDTOs.size() + " serverDTOs");
        return serverDTOs;
    }

}
