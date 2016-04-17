package core.repository.model.crawler.servers.oldera;


import core.repository.model.crawler.TibiaPlayer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 23/12/2015.
 */
@Entity
@Table(name = "oldera_player")
public class OlderaPlayer extends TibiaPlayer {

    // Vars

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "oldera_player_death",
            joinColumns = {@JoinColumn(name= "death_player_id")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    List<OlderaPlayerDeath> olderaPlayerDeathList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "oldera_player_level_history",
            joinColumns = {@JoinColumn(name= "oldera_player_id")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    List<OlderaPlayerDeath> olderaPlayerLevelHistoryList = new ArrayList<>();



    public OlderaPlayer() {}

    public OlderaPlayer(String name, int level, String vocation) {
        super("Oldera", name, level, vocation);
    }

    /**
     * Adds new death to the current death list
     * @param olderaPlayerDeath the death object
     * @return whether death was added to the death list
     * */
    public boolean addDeath(OlderaPlayerDeath olderaPlayerDeath) {

        boolean isPresent = olderaPlayerDeathList.stream()
                .filter(e -> e.getDeathPlayer().getName().equals(olderaPlayerDeath.getDeathPlayer().getName()))
                .filter(e -> e.getTsDeath().equals(olderaPlayerDeath.getTsDeath()))
                .findFirst()
                .isPresent();

        // Make sure it doesn't already exist
        if (!isPresent) {
            // continue
            if (olderaPlayerDeath.getTsDeath() != null
                    && olderaPlayerDeath.getFirstKillerName() != null
                    && !olderaPlayerDeath.getFirstKillerName().equals("")) {
                olderaPlayerDeathList.add(olderaPlayerDeath);
                return true;
            }
        }


        return false;
    }

    // Getters & Setters
    public List<OlderaPlayerDeath> getOlderaPlayerDeathList() { return olderaPlayerDeathList; }
    public void setOlderaPlayerDeathList(List<OlderaPlayerDeath> olderaPlayerDeathList) { this.olderaPlayerDeathList = olderaPlayerDeathList; }

    public List<OlderaPlayerDeath> getOlderaPlayerLevelHistoryList() {
        return olderaPlayerLevelHistoryList;
    }
}
