package core.repository.model.dto.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import core.repository.model.dto.HuntedPlayerDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Adrian on 30/04/2016.
 */
public class HuntedPlayerDTOWrapper implements Serializable {

    @JsonProperty("huntedPlayers")
    private List<HuntedPlayerDTO> huntedPlayerDTOList;

    @JsonCreator
    public HuntedPlayerDTOWrapper(@JsonProperty("huntedPlayers") List<HuntedPlayerDTO> huntedPlayerDTOList) {
        this.huntedPlayerDTOList = huntedPlayerDTOList;
    }

    public List<HuntedPlayerDTO> getHuntedPlayerDTOList() {
        return huntedPlayerDTOList;
    }

    public void setHuntedPlayerDTOList(List<HuntedPlayerDTO> huntedPlayerDTOList) {
        this.huntedPlayerDTOList = huntedPlayerDTOList;
    }
}
