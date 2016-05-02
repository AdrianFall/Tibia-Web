package core.repository.model.dto;

/**
 * Created by Adrian on 02/05/2016.
 */
public class ServerDTO {

    String name;
    boolean isOnline;
    int numberOfPlayersOnline;

    public ServerDTO(boolean isOnline, String name, int numberOfPlayersOnline) {
        this.isOnline = isOnline;
        this.name = name;
        this.numberOfPlayersOnline = numberOfPlayersOnline;
    }


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

    public int getNumberOfPlayersOnline() {
        return numberOfPlayersOnline;
    }

    public void setNumberOfPlayersOnline(int numberOfPlayersOnline) {
        this.numberOfPlayersOnline = numberOfPlayersOnline;
    }
}
