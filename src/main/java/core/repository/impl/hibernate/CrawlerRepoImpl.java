package core.repository.impl.hibernate;

import core.repository.AccountRepo;
import core.repository.CrawlerRepo;
import core.repository.model.crawler.TibiaHuntedPlayer;
import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;
import core.repository.model.web.Account;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 24/04/2016.
 */
@Repository
@Transactional
public class CrawlerRepoImpl implements CrawlerRepo {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    AccountRepo accountRepo;

    @Override
    public List<TibiaPlayer> getOnlinePlayers(String serverName) {
        ProjectionList p = Projections.projectionList();
        p.add(Projections.property("name"));
        p.add(Projections.property("level"));
        p.add(Projections.property("vocation"));
        // p.add(Projections.property("id"));
        //p.add(Projections.property("server_name"));
        //p.add(Projections.property("server_name"));

        List<Object[]> onlinePlayersArray = (List<Object[]>) sessionFactory.getCurrentSession()
                .createCriteria(TibiaPlayer.class)
                .add(Restrictions.eq("isOnline", true))
                .add(Restrictions.eq("server.name", serverName))
                .setProjection(Projections.distinct(p))
                .list();

        List<TibiaPlayer> onlinePlayers = new ArrayList<>();

        onlinePlayersArray.forEach(e -> {
            TibiaPlayer player = new TibiaPlayer();
            player.setName((String) e[0]);
            player.setLevel((Integer) e[1]);
            player.setVocation((String) e[2]);
            onlinePlayers.add(player);
        });


        return onlinePlayers;
    }



    @Override
    public List<OlderaPlayer> getOlderaOnlinePlayers() {
        ProjectionList p = Projections.projectionList();
        p.add(Projections.property("name"));
        p.add(Projections.property("level"));
        p.add(Projections.property("vocation"));
        // p.add(Projections.property("id"));
        //p.add(Projections.property("server_name"));
        //p.add(Projections.property("server_name"));

        List<Object[]> onlinePlayersArray = (List<Object[]>) sessionFactory.getCurrentSession()
                .createCriteria(OlderaPlayer.class)
                .add(Restrictions.eq("isOnline", true))
                        //.add(Restrictions.eq("server", "Oldera"))
                .setProjection(Projections.distinct(p))
                .list();

        List<OlderaPlayer> onlinePlayers = new ArrayList<>();

        onlinePlayersArray.forEach(e -> {
            OlderaPlayer olderaPlayer = new OlderaPlayer();
            olderaPlayer.setName((String) e[0]);
            olderaPlayer.setLevel((Integer) e[1]);
            olderaPlayer.setVocation((String) e[2]);
            onlinePlayers.add(olderaPlayer);
        });


        return onlinePlayers;
    }

    @Override
    public List<ThroniaPlayer> getThroniaOnlinePlayers() {
        ProjectionList p = Projections.projectionList();
        p.add(Projections.property("name"));
        p.add(Projections.property("level"));
        p.add(Projections.property("vocation"));
        // p.add(Projections.property("id"));
        //p.add(Projections.property("server_name"));

        List<Object[]> onlinePlayersArray = (List<Object[]>) sessionFactory.getCurrentSession()
                .createCriteria(ThroniaPlayer.class)
                .add(Restrictions.eq("isOnline", true))
                        //.add(Restrictions.eq("server", "Oldera"))
                .setProjection(Projections.distinct(p))
                .list();

        List<ThroniaPlayer> onlinePlayers = new ArrayList<>();

        onlinePlayersArray.forEach(e -> {
            ThroniaPlayer olderaPlayer = new ThroniaPlayer();
            olderaPlayer.setName((String) e[0]);
            olderaPlayer.setLevel((Integer) e[1]);
            olderaPlayer.setVocation((String) e[2]);
            onlinePlayers.add(olderaPlayer);
        });


        return onlinePlayers;
    }

    @Override
    public TibiaPlayer findTibiaPlayer(String playerName, String serverName) {

        TibiaPlayer tibiaPlayer = (TibiaPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaPlayer.class)
                .add(Restrictions.eq("server.name", serverName))
                .add(Restrictions.eq("name", playerName))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        return tibiaPlayer;
    }

    @Override
    public TibiaPlayer findHuntedPlayer(Long playerId, Long accountId, String serverName) {
        TibiaHuntedPlayer huntedPlayer = (TibiaHuntedPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", serverName))
                .add(Restrictions.eq("tibiaPlayerId", playerId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        if (huntedPlayer != null) {
            TibiaPlayer tibiaPlayer = (TibiaPlayer) sessionFactory.getCurrentSession()
                    .createCriteria(TibiaPlayer.class)
                    .add(Restrictions.eq("id", playerId))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .uniqueResult();
            return tibiaPlayer;
        }


        return null;
    }

    @Override
    public OlderaPlayer findOlderaHuntedPlayer(Long playerId, Long accountId) {

        TibiaHuntedPlayer olderaHuntedPlayer = (TibiaHuntedPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", "Oldera"))
                .add(Restrictions.eq("tibiaPlayerId", playerId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        if (olderaHuntedPlayer != null) {
            OlderaPlayer olderaPlayer = (OlderaPlayer) sessionFactory.getCurrentSession()
                    .createCriteria(OlderaPlayer.class)
                    .add(Restrictions.eq("id", playerId))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .uniqueResult();
            return olderaPlayer;
        }


        return null;
    }

    @Override
    public ThroniaPlayer findThroniaHuntedPlayer(Long playerId, Long accountId) {

        TibiaHuntedPlayer throniaHuntedPlayer = (TibiaHuntedPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", "Thronia"))
                .add(Restrictions.eq("tibiaPlayerId", playerId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        if (throniaHuntedPlayer != null) {
            ThroniaPlayer throniaPlayer = (ThroniaPlayer) sessionFactory.getCurrentSession()
                    .createCriteria(ThroniaPlayer.class)
                    .add(Restrictions.eq("id", playerId))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .uniqueResult();
            return throniaPlayer;
        }

        return null;
    }

    @Override
    public OlderaPlayer addToOlderaHuntedList(Long playerId, Long accountId) {
        TibiaHuntedPlayer tibiaHuntedPlayer = new TibiaHuntedPlayer(accountId, "Oldera", playerId);
        sessionFactory.getCurrentSession()
                .save(tibiaHuntedPlayer);
        sessionFactory.getCurrentSession().flush();

        return findOlderaHuntedPlayer(playerId, accountId);
    }

    @Override
    public ThroniaPlayer addToThroniaHuntedList(Long playerId, Long accountId) {
        TibiaHuntedPlayer tibiaHuntedPlayer = new TibiaHuntedPlayer(accountId, "Thronia", playerId);
        sessionFactory.getCurrentSession()
                .save(tibiaHuntedPlayer);
        sessionFactory.getCurrentSession().flush();

        return findThroniaHuntedPlayer(playerId, accountId);
    }

    @Override
    public boolean removeFromOlderaHuntedList(Long playerId, Long accountId) {
        TibiaHuntedPlayer olderaHuntedPlayer = (TibiaHuntedPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", "Oldera"))
                .add(Restrictions.eq("tibiaPlayerId", playerId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        if (olderaHuntedPlayer != null) {
            sessionFactory.getCurrentSession()
                    .delete(olderaHuntedPlayer);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFromThroniaHuntedList(Long playerId, Long accountId) {
        TibiaHuntedPlayer throniaHuntedPlayer = (TibiaHuntedPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", "Thronia"))
                .add(Restrictions.eq("tibiaPlayerId", playerId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        if (throniaHuntedPlayer != null) {
            sessionFactory.getCurrentSession()
                    .delete(throniaHuntedPlayer);
            return true;
        }
        return false;
    }

    @Override
    public List<TibiaPlayer> getHuntedList(Long accountId, String serverName) {
        List<TibiaHuntedPlayer> tibiaHuntedPlayers = sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", serverName))
                .list();

        if (tibiaHuntedPlayers != null) {
            List<TibiaPlayer> tibiaPlayers = new ArrayList<>();
            tibiaHuntedPlayers.forEach(e -> {
                TibiaPlayer tibiaPlayer = findHuntedPlayer(e.getTibiaPlayerId(), accountId, serverName);
                if (tibiaPlayer != null) {
                    tibiaPlayers.add(tibiaPlayer);
                }
            });
            return tibiaPlayers;
        }
        return null;
    }

    @Override
    public List<OlderaPlayer> getOlderaHuntedList(Long accountId, String serverName) {
        List<TibiaHuntedPlayer> tibiaHuntedPlayers = sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", serverName))
                .list();

        if (tibiaHuntedPlayers != null) {
            List<OlderaPlayer> olderaPlayers = new ArrayList<>();
            tibiaHuntedPlayers.forEach(e -> {
                OlderaPlayer olderaPlayer = findOlderaHuntedPlayer(e.getTibiaPlayerId(), accountId);
                if (olderaPlayer != null) {
                    olderaPlayers.add(olderaPlayer);
                }
            });
            return olderaPlayers;
        }
        return null;
    }

    @Override
    public List<ThroniaPlayer> getThroniaHuntedList(Long accountId, String serverName) {
        List<TibiaHuntedPlayer> tibiaHuntedPlayers = sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", serverName))
                .list();

        if (tibiaHuntedPlayers != null) {
            List<ThroniaPlayer> throniaPlayers = new ArrayList<>();
            tibiaHuntedPlayers.forEach(e -> {
                ThroniaPlayer throniaPlayer = findThroniaHuntedPlayer(e.getTibiaPlayerId(), accountId);
                if (throniaPlayer != null) {
                    throniaPlayers.add(throniaPlayer);
                }
            });
            return throniaPlayers;
        }
        return null;
    }
}
