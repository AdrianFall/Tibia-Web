package core.repository.model.crawler.servers.thronia;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Adrian on 25/02/2016.
 */
@Entity
@Table(name = "thronia_player_death")
public class ThroniaPlayerDeath {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "death_player_id")
    private ThroniaPlayer deathPlayer;
    
    @Column(name = "has_two_killers")
    boolean hasTwoKillers = false;

    @Column(name = "first_killer_is_creature")
    boolean firstKillerIsCreature;

    @Column(name = "second_killer_is_creature")
    boolean secondKillerIsCreature = false;

    @Column(name = "first_killer_name")
    String firstKillerName;

    @Column(name = "second_killer_name")
    String secondKillerName;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "first_killer_id")
    ThroniaPlayer playerKillerFirst;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "second_killer_id")
    ThroniaPlayer playerKillerSecond;

    @Column(name = "ts_death")
    Timestamp tsDeath;

    // Constructors

    // Creature & Creature
    public ThroniaPlayerDeath(String firstKillerCreatureName, String secondKillerCreatureName, Timestamp tsDeath, ThroniaPlayer deathPlayer) {
        hasTwoKillers = firstKillerIsCreature = secondKillerIsCreature = true;
        firstKillerName = firstKillerCreatureName;
        secondKillerName = secondKillerCreatureName;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // Player & Player
    public ThroniaPlayerDeath(ThroniaPlayer firstKillerPlayer, ThroniaPlayer secondKillerPlayer, Timestamp tsDeath, ThroniaPlayer deathPlayer) {
        hasTwoKillers = true;
        firstKillerName = firstKillerPlayer.getName();
        secondKillerName = secondKillerPlayer.getName();
        playerKillerFirst = firstKillerPlayer;
        playerKillerSecond = secondKillerPlayer;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // Creature & Player
    public ThroniaPlayerDeath(String firstKillerCreatureName, ThroniaPlayer secondKillerPlayer, Timestamp tsDeath, ThroniaPlayer deathPlayer) {
        hasTwoKillers = firstKillerIsCreature = true;
        firstKillerName = firstKillerCreatureName;
        secondKillerName = secondKillerPlayer.getName();
        playerKillerSecond = secondKillerPlayer;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // Player & Creature
    public ThroniaPlayerDeath(ThroniaPlayer firstKillerPlayer, String secondKillerCreatureName, Timestamp tsDeath, ThroniaPlayer deathPlayer) {
        hasTwoKillers = secondKillerIsCreature = true;
        firstKillerName = firstKillerPlayer.getName();
        secondKillerName = secondKillerCreatureName;
        playerKillerFirst = firstKillerPlayer;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // Creature
    public ThroniaPlayerDeath(String firstKillerCreatureName, Timestamp tsDeath, ThroniaPlayer deathPlayer) {
        firstKillerIsCreature = true;
        firstKillerName = firstKillerCreatureName;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // Player
    public ThroniaPlayerDeath(ThroniaPlayer firstKillerPlayer, Timestamp tsDeath, ThroniaPlayer deathPlayer) {
        playerKillerFirst = firstKillerPlayer;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // END Constructors

    // Getters & Setters
    public ThroniaPlayer getDeathPlayer() { return deathPlayer; }
    public ThroniaPlayer getPlayerKillerFirst() { return playerKillerFirst; }
    public ThroniaPlayer getPlayerKillerSecond() { return playerKillerSecond; }
    public String getFirstKillerName() { return firstKillerName; }
    public String getSecondKillerName() { return secondKillerName; }
    public Timestamp getTsDeath() { return tsDeath; }
    public boolean isFirstKillerIsCreature() {
        return firstKillerIsCreature;
    }
    public boolean isSecondKillerIsCreature() {
        return secondKillerIsCreature;
    }
    public boolean isHasTwoKillers() {
        return hasTwoKillers;
    }
}
