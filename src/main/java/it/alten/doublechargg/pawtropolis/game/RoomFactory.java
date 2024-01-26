package it.alten.doublechargg.pawtropolis.game;

import it.alten.doublechargg.pawtropolis.animals.AnimalFactory;
import it.alten.doublechargg.pawtropolis.game.model.Item;
import it.alten.doublechargg.pawtropolis.game.model.Room;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomFactory {

    private static final Random randomizer = new Random();
    public static int count = 0;
    private AnimalFactory animalFactory;
    private final Item[] items = {
            new Item(1L, "Sword", "A sword", 7),
            new Item(2L, "Knife", "A knife", 4),
            new Item(3L, "Torch", "A Torch", 2),
            new Item(4L, "Key", "A key", 1)
    };


    public Room createRoom() {
        count++;
        Room room = new Room(Integer.toString(count));
        val MAXIMUM_ITEMS = 4;
        final int availableItemsCount = items.length;
        while (room.getItemsNumber() <= randomizer.nextInt(MAXIMUM_ITEMS + 1)) {
            int selectedItemIndex = randomizer.nextInt(availableItemsCount);
            room.addItem(items[selectedItemIndex]);
        }
        val MAXIMUM_ANIMALS = 5;
        while (room.getAnimalNumber() <= randomizer.nextInt(MAXIMUM_ANIMALS + 1)) {
            room.addAnimal(animalFactory.createAnimal());
        }
        return room;
    }
}
