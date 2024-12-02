package interfaces;

import animation.AnimationState;
import main.Asset;
import object.SuperObject;

public interface Pickupable {
    // THIS INTERFACE PICKS UP THE OBJECT AND DELETES THE COPY IN THE WORLD

    // flip state
    boolean isPickingUp(AnimationState curr);

    // deploy
    default void reposition(Pickupable obj, SuperObject surface) {
        ((SuperObject) obj).setWorldX(surface.getWorldX());
        ((SuperObject) obj).setWorldY(surface.getWorldY());
    }
    default void reposition(Pickupable obj, int surfaceX, int surfaceY) {
        ((SuperObject) obj).setWorldX(surfaceX);
        ((SuperObject) obj).setWorldY(surfaceY);
    }
}
