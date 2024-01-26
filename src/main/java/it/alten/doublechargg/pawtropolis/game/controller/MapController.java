package it.alten.doublechargg.pawtropolis.game.controller;

import it.alten.doublechargg.pawtropolis.game.RoomFactory;
import it.alten.doublechargg.pawtropolis.game.enums.CardinalPoints;
import it.alten.doublechargg.pawtropolis.game.model.Door;
import it.alten.doublechargg.pawtropolis.game.model.Item;
import it.alten.doublechargg.pawtropolis.game.model.Player;
import it.alten.doublechargg.pawtropolis.game.model.Room;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Getter
@Component
@Log
public class MapController {

    private static final Random RANDOMIZER = new Random();
    private final RoomFactory roomFactory;
    @Getter
    @Setter
    private Room currentRoom;

    @Autowired
    private MapController(RoomFactory roomFactory) {
        this.roomFactory = roomFactory;
        currentRoom = createMap();
    }

    public Room createMap() {
        List<Room> roomList = new ArrayList<>();
        val MINIMUM_ROOMS = 8;
        val MAXIMUM_ROOMS = 15;
        final int roomNumber = RANDOMIZER.nextInt(MINIMUM_ROOMS, MAXIMUM_ROOMS + 1);
        for (int i = 0; i <= roomNumber; i++) {
            roomList.add(roomFactory.createRoom());
        }
        for (int i = 0; i < roomNumber - 1; i++) {
            var selectedCardinalPointIndex = RANDOMIZER.nextInt(CardinalPoints.values().length);
            CardinalPoints cardinalPoint = CardinalPoints.values()[selectedCardinalPointIndex];
            connectRooms(cardinalPoint, roomList.get(i), roomList.get(i + 1));
        }
        return roomList.getFirst();
    }

    public void connectRooms(CardinalPoints cardinalPoint, Room room1, Room room2) {
        Door door = new Door(room1, room2);
        room1.addDoor(cardinalPoint, door);
        room2.addDoor(CardinalPoints.getOppositeCardinalPoint(cardinalPoint), door);
    }

    public boolean handleOpenDoorAttempt(Door door, Player player) {
        if (!door.isLocked()) {
            return true;
        }

        log.info("The door is locked: would you like to use an item to unlock it?");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("Y")) {
            return handleUnlockAttempt(door, player, scanner);
        } else if (answer.equalsIgnoreCase("N")) {
            return false;
        } else {
            log.info("Invalid input");
            return false;
        }
    }

    private boolean handleUnlockAttempt(Door door, Player player, Scanner scanner) {
        log.info("Type the name of the chosen item");
        String itemName = scanner.nextLine();
        Item item = player.getItemFromBag(itemName);

        if (item != null) {
            return useUnlockingItem(door, item, player);
        } else {
            log.info("The selected item is not in the bag");
            return false;
        }
    }

    private boolean useUnlockingItem(Door door, Item item, Player player) {
        if (item.name().equalsIgnoreCase(door.getUnlockingItem().name())) {
            log.info("You unlocked the door!");
            door.setLocked(false);
            player.removeItem(item);
            return true;
        } else {
            log.info("This is not the right item");
            return false;
        }
    }

    public void changeRoom(Door door) {
        currentRoom = door.getDestinationRoom();
        log.info(door.toString());
        door.swapRooms();
    }
}

