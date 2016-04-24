package core.repository.model.web.form;

import javax.validation.constraints.NotNull;

/**
 * Created by Adrian on 24/04/2016.
 */
public class HuntedPlayerForm {

    @NotNull
    private String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
