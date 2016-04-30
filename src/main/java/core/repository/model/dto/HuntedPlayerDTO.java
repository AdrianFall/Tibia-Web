package core.repository.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Adrian on 30/04/2016.
 */

/* For querying the hunted player of particular account*/
@JsonIgnoreProperties(ignoreUnknown=true)
public class HuntedPlayerDTO implements Serializable {


    Long accountId;
    @JsonProperty("huntedPlayerName")
    String huntedPlayerName;
    @JsonProperty("serverName")
    String serverName;

  /*  public HuntedPlayerDTO(Long accountId, String huntedPlayerName, String serverName) {
        this.accountId = accountId;
        this.huntedPlayerName = huntedPlayerName;
        this.serverName = serverName;
    }*/

    public HuntedPlayerDTO(String huntedPlayerName, String serverName) {
        this.huntedPlayerName = huntedPlayerName;
        this.serverName = serverName;
    }

    public HuntedPlayerDTO(Long accountId, String huntedPlayerName, String serverName) {
        this.accountId = accountId;
        this.huntedPlayerName = huntedPlayerName;
        this.serverName = serverName;
    }

    public HuntedPlayerDTO() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getHuntedPlayerName() {
        return huntedPlayerName;
    }

    public void setHuntedPlayerName(String huntedPlayerName) {
        this.huntedPlayerName = huntedPlayerName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
