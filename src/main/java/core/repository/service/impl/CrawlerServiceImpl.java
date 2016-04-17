package core.repository.service.impl;

import core.repository.OlderaCrawlerRepo;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.service.CrawlerService;
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
    OlderaCrawlerRepo olderaCrawlerRepo;

    @Override
    public List<OlderaPlayer> getOnlineOlderaPlayers() {
        return olderaCrawlerRepo.getOnlinePlayers();
    }
}
