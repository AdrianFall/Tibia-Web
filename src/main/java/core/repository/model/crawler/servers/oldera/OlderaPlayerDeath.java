package core.repository.model.crawler.servers.oldera;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Adrian on 30/12/2015.
 */
@Entity
@Table(name = "oldera_player_death",
        uniqueConstraints = { @UniqueConstraint(name = "unique_death",columnNames =
                { "death_player_id", "ts_death" }) })
public class OlderaPlayerDeath {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "death_player_id")
    OlderaPlayer deathPlayer;

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
    OlderaPlayer playerKillerFirst;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "second_killer_id")
    OlderaPlayer playerKillerSecond;

    @Column(name = "ts_death")
    Timestamp tsDeath;

    // Constructors

    // Creature & Creature
    public OlderaPlayerDeath(String firstKillerCreatureName, String secondKillerCreatureName, Timestamp tsDeath, OlderaPlayer deathPlayer) {
        hasTwoKillers = firstKillerIsCreature = secondKillerIsCreature = true;
        firstKillerName = firstKillerCreatureName;
        secondKillerName = secondKillerCreatureName;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // Player & Player
    public OlderaPlayerDeath(OlderaPlayer firstKillerPlayer, OlderaPlayer secondKillerPlayer, Timestamp tsDeath, OlderaPlayer deathPlayer) {
        hasTwoKillers = true;
        firstKillerName = firstKillerPlayer.getName();
        secondKillerName = secondKillerPlayer.getName();
        playerKillerFirst = firstKillerPlayer;
        playerKillerSecond = secondKillerPlayer;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // Creature & Player
    public OlderaPlayerDeath(String firstKillerCreatureName, OlderaPlayer secondKillerPlayer, Timestamp tsDeath, OlderaPlayer deathPlayer) {
        hasTwoKillers = firstKillerIsCreature = true;
        firstKillerName = firstKillerCreatureName;
        secondKillerName = secondKillerPlayer.getName();
        playerKillerSecond = secondKillerPlayer;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // Player & Creature
    public OlderaPlayerDeath(OlderaPlayer firstKillerPlayer, String secondKillerCreatureName, Timestamp tsDeath, OlderaPlayer deathPlayer) {
        hasTwoKillers = secondKillerIsCreature = true;
        firstKillerName = firstKillerPlayer.getName();
        secondKillerName = secondKillerCreatureName;
        playerKillerFirst = firstKillerPlayer;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // Creature
    public OlderaPlayerDeath(String firstKillerCreatureName, Timestamp tsDeath, OlderaPlayer deathPlayer) {
        firstKillerIsCreature = true;
        firstKillerName = firstKillerCreatureName;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    // Player
    public OlderaPlayerDeath(OlderaPlayer firstKillerPlayer, Timestamp tsDeath, OlderaPlayer deathPlayer) {
        playerKillerFirst = firstKillerPlayer;
        this.tsDeath = tsDeath;
        this.deathPlayer = deathPlayer;
    }

    public OlderaPlayerDeath() {}

    // END Constructors

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OlderaPlayer getDeathPlayer() { return deathPlayer; }
    public OlderaPlayer getPlayerKillerFirst() { return playerKillerFirst; }
    public OlderaPlayer getPlayerKillerSecond() { return playerKillerSecond; }
    public String getFirstKillerName() { return firstKillerName; }
    public String getSecondKillerName() { return secondKillerName; }
    public Timestamp getTsDeath() { return tsDeath; }
}
