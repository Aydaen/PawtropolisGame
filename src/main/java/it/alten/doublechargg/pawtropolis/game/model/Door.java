package it.alten.doublechargg.pawtropolis.game.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class Door {
    private boolean isLocked;
    private static final Random RANDOMIZER = new Random();
    private Room originRoom;
    private Room destinationRoom;
    private Item unlockingItem;

    public Door(Room originRoom, Room destinationRoom) {
     isLocked = RANDOMIZER.nextBoolean();
     this.originRoom = originRoom;
     this.destinationRoom = destinationRoom;
     this.unlockingItem = getRandomUnlockingItem(originRoom);
    }

    private Item getRandomUnlockingItem(Room originRoom) {
        return originRoom.getRandomItem();
    }

    public String getDoorStatusAsString() {
        return isLocked ? "locked" : "unlocked";
    }

    public void swapRooms() {
        Room temp = originRoom;
        originRoom = destinationRoom;
        destinationRoom = temp;
    }

}
