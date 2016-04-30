package core.repository.model.crawler;

import javax.persistence.*;

/**
 * Created by Adrian on 24/04/2016.
 */
@Entity
@Table(name = "tibia_hunted_player")
public class TibiaHuntedPlayer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;

    @Column(name = "account_id")
    Long accountId;

    @Column(name = "tibia_server_name")
    String tibiaServerName;

/*    @Column(name = "tibia_player_id")
    Long tibiaPlayerId;*/

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "tibia_player_id")
    TibiaPlayer tibiaPlayer;

    public TibiaHuntedPlayer() {}

    public TibiaHuntedPlayer(Long accountId, String tibiaServerName, Long tibiaPlayerId) {
        this.accountId = accountId;
        tibiaPlayer = new TibiaPlayer();
        tibiaPlayer.setId(tibiaPlayerId);
        this.tibiaServerName = tibiaServerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getTibiaServerName() {
        return tibiaServerName;
    }

    public void setTibiaServerName(String tibiaServerName) {
        this.tibiaServerName = tibiaServerName;
    }


    public TibiaPlayer getTibiaPlayer() {
        return tibiaPlayer;
    }

    public void setTibiaPlayer(TibiaPlayer tibiaPlayer) {
        this.tibiaPlayer = tibiaPlayer;
    }
}
