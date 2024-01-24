package it.alten.doublechargg.pawtropolis.game.command.impl;

import it.alten.doublechargg.pawtropolis.game.command.interfaces.CommandWithParam;
import it.alten.doublechargg.pawtropolis.game.controller.GameController;
import it.alten.doublechargg.pawtropolis.game.controller.MapController;
import it.alten.doublechargg.pawtropolis.game.enums.CardinalPoints;
import it.alten.doublechargg.pawtropolis.game.model.Door;
import it.alten.doublechargg.pawtropolis.game.model.Player;
import it.alten.doublechargg.pawtropolis.game.model.Room;
import lombok.extern.java.Log;

import java.util.Objects;

@Log
public class GoCommand implements CommandWithParam {

    private GameController gameController;
    private MapController mapController;
    Player player;
    Room currentRoom;

    public GoCommand(GameController gameController) {
        this.gameController = gameController;
        this.player = gameController.getPlayer();
        this.mapController = gameController.getMapController();
        this.currentRoom = mapController.getCurrentRoom();
    }

    @Override
    public String execute(String arg) {
        CardinalPoints cardinalPoint = CardinalPoints.findByName(arg);

        if (Objects.isNull(cardinalPoint)) {
            return "Invalid input";
        }

        if (currentRoom.adjacentRoomExists(cardinalPoint)) {
            Door door = currentRoom.getDoorByCardinalPoint(cardinalPoint);

            if (mapController.handleOpenDoorAttempt(door, player)) {
                mapController.changeRoom(cardinalPoint);
                return gameController.getCurrentRoom().toString();
            } else {
                return "";
            }
        }
        return "Not existent room";
    }
}
