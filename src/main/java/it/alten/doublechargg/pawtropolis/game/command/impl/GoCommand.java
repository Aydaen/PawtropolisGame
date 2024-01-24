package it.alten.doublechargg.pawtropolis.game.command.impl;

import it.alten.doublechargg.pawtropolis.game.command.interfaces.CommandWithParam;
import it.alten.doublechargg.pawtropolis.game.controller.GameController;
import it.alten.doublechargg.pawtropolis.game.enums.CardinalPoints;
import it.alten.doublechargg.pawtropolis.game.model.Door;
import it.alten.doublechargg.pawtropolis.game.model.Item;
import it.alten.doublechargg.pawtropolis.game.model.Player;
import it.alten.doublechargg.pawtropolis.game.model.Room;
import lombok.extern.java.Log;

import java.util.Objects;
import java.util.Scanner;

@Log
public class GoCommand implements CommandWithParam {

    private GameController gameController;
    Player player;
    Room currentRoom;

    public GoCommand(GameController gameController) {
        this.gameController = gameController;
        this.player = gameController.getPlayer();
        this.currentRoom = gameController.getCurrentRoom();
    }

    @Override
    public String execute(String arg) {
        // check esistenza punto cardinale inserito
        CardinalPoints cardinalPoint = CardinalPoints.findByName(arg);
        if (Objects.isNull(cardinalPoint)) {
            return "Invalid input";
        }

        // check esistenza stanza nel punto cardinale inserito
        if (currentRoom.adjacentRoomExists(cardinalPoint)) {
            Door door = currentRoom.getDoorByCardinalPoint(cardinalPoint);

            // check porta chiusa
            if (door.isLocked()) {
                return unlockDoor(door, cardinalPoint);

            // se è sbloccata
            } else {
                gameController.setCurrentRoom(currentRoom.getAdjacentRoomByCardinalPoint(cardinalPoint));
                return gameController.getCurrentRoom().toString();
            }
        }
        return "Not existent room";
    }

    private String unlockDoor(Door door, CardinalPoints cardinalPoint) {
        log.info("The door is locked: would you like to use an item to unlock it?");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("Y")) {
            return handleUnlockAttempt(door, cardinalPoint, scanner);
        } else if (answer.equalsIgnoreCase("N")) {
            return "";
        } else {
            return "Invalid input";
        }
    }

    private String handleUnlockAttempt(Door door, CardinalPoints cardinalPoint, Scanner scanner) {
        log.info("Type the name of the chosen item");
        String itemName = scanner.nextLine();
        Item item = player.getItemFromBag(itemName);

        if (item != null) {
            return useUnlockingItem(door, cardinalPoint, item);
        } else {
            return "The selected item is not in the bag";
        }
    }

    private String useUnlockingItem(Door door, CardinalPoints cardinalPoint, Item item) {
        if (item.name().equalsIgnoreCase("Key")) {
            log.info("You unlocked the door!");
            door.setLocked(false);
            player.removeItem(item);
            gameController.setCurrentRoom(currentRoom.getAdjacentRoomByCardinalPoint(cardinalPoint));
            return gameController.getCurrentRoom().toString();
        } else {
            return "This is not the right item";
        }
    }
}
