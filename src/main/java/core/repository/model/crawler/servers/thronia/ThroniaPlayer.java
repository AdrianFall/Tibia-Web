package core.repository.model.crawler.servers.thronia;

import core.repository.model.crawler.TibiaPlayer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 25/02/2016.
 */
@Entity
@Table(name = "thronia_player")
public class ThroniaPlayer extends TibiaPlayer {

    /* @Id resolved by hibernate inheritance annotation in TibiaPlayer class */

    // Vars
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "thronia_player_death",
            joinColumns = {@JoinColumn(name= "death_player_id")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    List<ThroniaPlayerDeath> throniaPlayerDeathList = new ArrayList<>();

    // Constructors
    public ThroniaPlayer() {}
    public ThroniaPlayer(String name, int level, String vocation) {
        super("Thronia", name, level, vocation);
    }

    /**
     * Adds new death to the current death list
     * @param throniaPlayerDeath the new death object
     * @return whether death was added to the death list
     * */
    public boolean addDeath(ThroniaPlayerDeath throniaPlayerDeath) {

        boolean isPresent = throniaPlayerDeathList.stream()
                .filter(e -> e.getDeathPlayer().getName().equals(throniaPlayerDeath.getDeathPlayer().getName()))
                .filter(e -> e.getTsDeath().equals(throniaPlayerDeath.getTsDeath()))
                .findFirst()
                .isPresent();

        // Make sure it doesn't already exist
        if (!isPresent) {
            // continue
            if (throniaPlayerDeath.getTsDeath() != null
                    && throniaPlayerDeath.getFirstKillerName() != null
                    && !throniaPlayerDeath.getFirstKillerName().equals("")) {
                throniaPlayerDeathList.add(throniaPlayerDeath);
                return true;
            }
        }


        return false;
    }

    // Getters & Setters
    public List<ThroniaPlayerDeath> getThroniaPlayerDeathList() { return throniaPlayerDeathList; }

}
