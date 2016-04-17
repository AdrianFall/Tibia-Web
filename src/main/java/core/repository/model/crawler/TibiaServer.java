package core.repository.model.crawler;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Adrian on 03/10/2015.
 */
@Entity
@Table(name = "tibia_server")
public class TibiaServer {


    /*@Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;*/

    @Id
    @Column(name = "name")
    String name;
    int update_interval_ms;

    @Column(name = "is_online")
    boolean isOnline;

    public TibiaServer(String name, int update_interval_ms, boolean isOnline) {
        this.name = name;
        this.update_interval_ms = update_interval_ms;
        this.isOnline = isOnline;
    }

    public TibiaServer() {}

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpdate_interval_ms() {
        return update_interval_ms;
    }

    public void setUpdate_interval_ms(int update_interval_ms) {
        this.update_interval_ms = update_interval_ms;
    }
}
