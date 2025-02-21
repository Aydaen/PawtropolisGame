package it.alten.doublechargg.pawtropolis.game.model;

import it.alten.doublechargg.pawtropolis.animals.model.abstracts.Animal;
import it.alten.doublechargg.pawtropolis.game.enums.CardinalPoints;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class Room {

    private final List<Item> items;
    private final List<Animal> animals;
    @Getter
    @Setter
    private String name;
    private EnumMap<CardinalPoints, Door> doors;

    public Room(String name) {
        this.name = name;
        items = new ArrayList<>();
        animals = new ArrayList<>();
        doors = new EnumMap<>(CardinalPoints.class);
    }

    public int getItemsNumber() {
        return items.size();
    }

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public boolean containsItem(Item item) {
        return items.contains(item);
    }

    public void addDoor(CardinalPoints cardinalPoint, Door door) {
        doors.put(cardinalPoint, door);
    }

    public int getAnimalNumber() {
        return animals.size();
    }

    public boolean addAnimal(Animal animal) {
        return animals.add(animal);
    }

    public boolean removeItem(Animal animal) {
        return animals.remove(animal);
    }

    public Door getDoorByCardinalPoint(CardinalPoints cardinalPoint) {
        return doors.get(cardinalPoint);
    }

    public Item getItemByName(String name) {
        return items.stream()
                .filter(item -> item.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    private String getItemsListAsString() {
        return items.stream()
                .map(Item::name)
                .collect(Collectors.joining(", "));
    }

    private String getAnimalsListAsString() {
        return animals.stream()
                .map(animal -> String.format("%s(%s)", animal.getName(), animal.getClass().getSimpleName()))
                .collect(Collectors.joining(", "));

    }

    private String getAdjacentRoomListAsString() {
        return doors.entrySet().stream()
                .map(entry -> entry.getKey().getName().concat("(" + (entry.getValue().isLocked()? "locked" : "unlocked") + ")"))
                .collect(Collectors.joining(", "));
    }

    public Item getRandomItem() {
        return items.stream()
                .skip(ThreadLocalRandom.current().nextInt(items.size()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format("You are in room %s%n" +
                        "Items: %s%n" +
                        "NPC: %s%n" +
                        "Doors: %s",
                name, getItemsListAsString(), getAnimalsListAsString(), getAdjacentRoomListAsString());
    }
}
