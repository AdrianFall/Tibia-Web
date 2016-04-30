package core.repository.model.crawler;

import javax.persistence.*;

/**
 * Created by Adrian on 03/10/2015.
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "tibia_player")
public class TibiaPlayer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "server_name")
    TibiaServer server;

    @Column(name="name")
    String name;
    @Column(name="level")
    int level;
    @Column(name="vocation")
    String vocation;

    @Column(name = "is_online")
    boolean isOnline;

    public TibiaPlayer() {}

    public TibiaPlayer(String server_name, String name, int level, String vocation) {
        server = new TibiaServer();
        server.setName(server_name);
        this.name = name;
        this.level = level;
        this.vocation = vocation;
        // TODO rethink

        isOnline = true;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer_name() {
        return server.getName();
    }

    public void setServer_name(String server_name) {
        this.server.setName(server_name);
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }


}
