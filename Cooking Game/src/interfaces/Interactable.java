package interfaces;

import animation.AnimationFactory;
import entity.Entity;

public interface Interactable {

    public void interact(Entity en, AnimationFactory animF);
}
