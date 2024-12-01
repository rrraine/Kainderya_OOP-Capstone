package animation;

import interfaces.Drawable;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class AnimationFactory {

    private static AnimationFactory instance;
    GamePanel gp;
    String avatar;

    AnimationState currentState;
    Animation base, carryPlate, carryCornedBeef, carryEgg, carryOnion, carryPan, carryRawRice, carrySpam, carryTapa;

    // 2 METHODS FOR CONSTRUCTOR SINGLETON
    private AnimationFactory(GamePanel gp, String avatar) {
        this.gp = gp;
        this.avatar = avatar;

        base = new Animation(gp, avatar, "/base/");
        carryPlate = new Animation(gp, avatar, "/holdingPlate/");
        carryCornedBeef = new Animation(gp, avatar, "/holdingCornedBeef/");
        carryEgg = new Animation(gp, avatar, "/holdingEgg/");
        carryOnion = new Animation(gp, avatar, "/holdingOnion/");
        carryPan = new Animation(gp, avatar, "/holdingPan/");
        carryRawRice = new Animation(gp, avatar, "/holdingRawRice/");
        carrySpam = new Animation(gp, avatar, "/holdingSpam/");
        carryTapa = new Animation(gp, avatar, "/holdingTapa/");


        // TODO OTHER HOLDINGS

        currentState = AnimationState.BASE;
    }
    public static AnimationFactory instantiate(GamePanel gp, String avatar) {
        if (instance == null) {
            instance = new AnimationFactory(gp, avatar);
        }
        return instance;
    }

    public void switchState(AnimationState newState) {
        currentState = newState;
    }
    public List<BufferedImage> getSpriteArray() {

        // TODO ADD MORE
        switch (currentState) {
            case BASE:
                return base.getSprites();
            case CARRY_PLATE:
                return carryPlate.getSprites();
            case CARRY_CORNEDBEEF:
                return carryCornedBeef.getSprites();
           case CARRY_EGG:
               return carryEgg.getSprites();
           case CARRY_ONION:
               return carryOnion.getSprites();
           case CARRY_PAN:
               return carryPan.getSprites();
           case CARRY_RAW_RICE:
                return carryRawRice.getSprites();
           case CARRY_SPAM:
                 return carrySpam.getSprites();
           case CARRY_TAPA:
                return carryTapa.getSprites();
        }

        return null;
    }
    public AnimationState getCurrentState() {
        return currentState;
    }
}
