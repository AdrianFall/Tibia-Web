package core.repository.service.impl;

import core.repository.AccountRepo;
import core.repository.CrawlerRepo;
import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;
import core.repository.model.web.Account;
import core.repository.service.CrawlerService;
import core.repository.service.exception.AccountDoesNotExistException;
import core.repository.service.exception.PlayerDoesNotExistException;
import core.repository.service.exception.PlayerExistsInHuntedListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Adrian on 17/04/2016.
 */
@Service
@Transactional
public class CrawlerServiceImpl implements CrawlerService {

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

        if (crawlerRepo.findHuntedPlayer(player.getId(), acc.getId(), serverName) != null) {
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

}
