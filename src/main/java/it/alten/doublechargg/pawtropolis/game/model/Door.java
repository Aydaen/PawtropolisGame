package it.alten.doublechargg.pawtropolis.game.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class Door {
    private boolean isLocked;
    private static final Random RANDOMIZER = new Random();

    public Door() {
     isLocked = RANDOMIZER.nextBoolean();
    }

    public String getDoorStatusAsString() {
        return isLocked ? "locked" : "unlocked";
    }
}
