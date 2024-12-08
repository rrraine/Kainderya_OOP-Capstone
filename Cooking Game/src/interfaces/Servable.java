package interfaces;

import object.SuperObject;

public interface Servable {

    default boolean serve(Servable onHand, String order) {

        if (onHand instanceof SuperObject meal) {
            return order.equals(meal.getName());
        }
        return false;
    }
}
