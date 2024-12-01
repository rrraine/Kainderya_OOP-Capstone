package interfaces;

import animation.AnimationState;

public interface Pickupable {
    // THIS INTERFACE PICKS UP THE OBJECT AND DELETES THE COPY IN THE WORLD

    // flip state
    boolean isPickingUp(AnimationState curr);
}
