package it.alten.doublechargg.pawtropolis.animals;

import it.alten.doublechargg.pawtropolis.animals.model.Eagle;
import it.alten.doublechargg.pawtropolis.animals.model.Lion;
import it.alten.doublechargg.pawtropolis.animals.model.Tiger;
import it.alten.doublechargg.pawtropolis.animals.model.abstracts.Animal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static it.alten.doublechargg.pawtropolis.animals.utilities.FactoryUtils.*;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnimalFactory {

    public Tiger createTiger() {
        return Tiger.builder()
                .name(names[randomizer.nextInt(names.length)])
                .favoriteFood(favoriteFoods[randomizer.nextInt(favoriteFoods.length)])
                .age(randomizer.nextInt(11))
                .entryDate(between(LocalDate.of(2014, 1, 1), LocalDate.now()))
                .weight(Double.parseDouble(decimalFormat.format(randomizer.nextDouble(225, 300))))
                .height(Double.parseDouble(decimalFormat.format(randomizer.nextDouble(0.85, 1.2))))
                .tailLength(Double.parseDouble(decimalFormat.format(randomizer.nextDouble(0.65, 3))))
                .build();
    }

    public Lion createLion() {
        return Lion.builder()
                .name(names[randomizer.nextInt(names.length)])
                .favoriteFood(favoriteFoods[randomizer.nextInt(favoriteFoods.length)])
                .age(randomizer.nextInt(11))
                .entryDate(between(LocalDate.of(2014, 1, 1), LocalDate.now()))
                .weight(Double.parseDouble(decimalFormat.format(randomizer.nextDouble(200, 250))))
                .height(Double.parseDouble(decimalFormat.format(randomizer.nextDouble(0.9, 1.2))))
                .tailLength(Double.parseDouble(decimalFormat.format(randomizer.nextDouble(0.7, 1))))
                .build();
    }

    public Eagle createEagle() {
        return Eagle.builder()
                .name(names[randomizer.nextInt(names.length)])
                .favoriteFood(favoriteFoods[randomizer.nextInt(favoriteFoods.length)])
                .age(randomizer.nextInt(11))
                .entryDate(between(LocalDate.of(2014, 1, 1), LocalDate.now()))
                .weight(Double.parseDouble(decimalFormat.format(randomizer.nextDouble(3, 7))))
                .height(Double.parseDouble(decimalFormat.format(randomizer.nextDouble(0.75, 0.84))))
                .wingspan(Double.parseDouble(decimalFormat.format(randomizer.nextDouble(1.8, 2.3))))
                .build();
    }

    public Animal createAnimal() {
        return switch (randomizer.nextInt(3)) {
            case 0 -> createEagle();
            case 1 -> createLion();
            default -> createTiger();
        };
    }
}
