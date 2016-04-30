package core.repository.model.dto;

/**
 * Created by Adrian on 30/04/2016.
 */

/* For querying the hunted player of particular account*/
public class HuntedPlayerDTO {

    Long accountId;
    String huntedPlayerName;
    String serverName;

    public HuntedPlayerDTO(Long accountId, String huntedPlayerName, String serverName) {
        this.accountId = accountId;
        this.huntedPlayerName = huntedPlayerName;
        this.serverName = serverName;
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
