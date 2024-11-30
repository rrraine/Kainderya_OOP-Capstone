package interfaces;

import food.Ingredients;

public interface Combinable {
    // THIS INTERFACE COMBINES TWO INGREDIENTS AND CREATES A NEW IMAGE

    boolean combineWith(Ingredients other);

}
