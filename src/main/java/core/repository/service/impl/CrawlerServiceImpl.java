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
    public List<OlderaPlayer> getOnlineOlderaPlayers() {
        return crawlerRepo.getOlderaOnlinePlayers();
    }

    @Override
    public List<ThroniaPlayer> getOnlineThroniaPlayers() {
        return crawlerRepo.getThroniaOnlinePlayers();
    }

    @Override
    public OlderaPlayer addToOlderaHuntedList(String playerName, String accountEmail) throws PlayerExistsInHuntedListException, PlayerDoesNotExistException, AccountDoesNotExistException {
        // Find player in the database
        TibiaPlayer player = crawlerRepo.findTibiaPlayer(playerName, "Oldera");
        if (player == null) {
            throw new PlayerDoesNotExistException("Player does not exist.");
        }

        Account acc = accountRepo.findAccountByEmail(accountEmail);
        if (acc == null) {
            throw new AccountDoesNotExistException("Account does not exist");
        }

        if (crawlerRepo.findOlderaHuntedPlayer(player.getId(), acc.getId()) != null) {
            throw new PlayerExistsInHuntedListException("Player already exists");
        }
        return crawlerRepo.addToOlderaHuntedList(player.getId(), acc.getId());
    }

    @Override
    public ThroniaPlayer addToThroniaHuntedList(String playerName, String accountEmail) throws PlayerExistsInHuntedListException, PlayerDoesNotExistException, AccountDoesNotExistException {
        // Find player in the database
        TibiaPlayer player = crawlerRepo.findTibiaPlayer(playerName, "Thronia");
        if (player == null) {
            throw new PlayerDoesNotExistException("Player does not exist.");
        }

        Account acc = accountRepo.findAccountByEmail(accountEmail);

        if (acc == null) {
            throw new AccountDoesNotExistException("Account does not exist");
        }

        if (crawlerRepo.findThroniaHuntedPlayer(player.getId(), acc.getId()) != null) {
            throw new PlayerExistsInHuntedListException("Player already exists");
        }

        return crawlerRepo.addToThroniaHuntedList(player.getId(), acc.getId());
    }

    @Override
    public List<OlderaPlayer> getOlderaHuntedList(String accountEmail) throws AccountDoesNotExistException {

        Account acc = accountRepo.findAccountByEmail(accountEmail);

        if (acc == null) {
            throw new AccountDoesNotExistException("Account does not exist");
        }

        return crawlerRepo.getOlderaHuntedList(acc.getId(), "Oldera");

    }

    @Override
    public List<ThroniaPlayer> getThroniaHuntedList(String accountEmail) throws AccountDoesNotExistException {
        Account acc = accountRepo.findAccountByEmail(accountEmail);

        if (acc == null) {
            throw new AccountDoesNotExistException("Account does not exist");
        }

        return crawlerRepo.getThroniaHuntedList(acc.getId(), "Thronia");
    }
}
