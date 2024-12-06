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
    Animation base, carryPlate, carryCornedBeef, carryEgg, carryOnion, carryPan, carryRawRice, carrySpam, carryTapa, carryCoke,
              carryTapaPlate, carrySpamPlate, carryEggPlate, carryCBPlate, carryRicePlate, carryDirtyPlate,
              carryTapsi, carryCornsi, carrySpamsi, carryTaplog, carryCornlog, carrySpamlog,
              carryTapsilog, carryCornsilog, carrySpamsilog;

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
        carryCoke = new Animation(gp, avatar, "/holdingCoke/");

        //new

        //ingredients in a plate
        carryTapaPlate = new Animation(gp, avatar, "/cookedTapa/");
        carryCBPlate = new Animation(gp, avatar, "/cookedCornedBeef/");
        carrySpamPlate = new Animation(gp, avatar, "/cookedSpam/");
        carryEggPlate = new Animation(gp, avatar, "/cookedEgg/");
        carryDirtyPlate = new Animation(gp, avatar, "/holdingDirtyPlate/");
        carryRicePlate = new Animation(gp, avatar, "/cookedRice/");

        carryTapsi= new Animation(gp, avatar, "/tapsi/");
        carryCornsi = new Animation(gp, avatar, "/cornsi/");
        carrySpamsi= new Animation(gp, avatar, "/spamsi/");
        carrySpamsilog = new Animation(gp, avatar, "/spamsilog/");
        carryTapsilog= new Animation(gp, avatar, "/tapsilog/");
        carryCornsilog = new Animation(gp, avatar, "/cornsilog/");

        carryTaplog= new Animation(gp, avatar, "/taplog/");
        carryCornlog = new Animation(gp, avatar, "/cornlog/");
        carrySpamlog = new Animation(gp, avatar, "/spamlog/");




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
            case CARRY_COKE:
                return carryCoke.getSprites();

            case CARRY_EGG_PLATE:
                return carryEggPlate.getSprites();
            case CARRY_TAPA_PLATE:
                return carryTapaPlate.getSprites();
            case CARRY_CB_PLATE:
                return carryCBPlate.getSprites();
            case CARRY_SPAM_PLATE:
                return carrySpam.getSprites();
            case CARRY_SPAMSI_PLATE:
                return carrySpamsi.getSprites();
            case CARRY_SPAMLOG_PLATE:
                return carrySpamlog.getSprites();
            case CARRY_TAPSI_PLATE:
                return carryEggPlate.getSprites();
            case CARRY_TAPLOG_PLATE:
                return carryTaplog.getSprites();
            case CARRY_CORNSI_PLATE:
                return carryCornsi.getSprites();
            case CARRY_CORNLOG_PLATE:
                return carryCornlog.getSprites();





        }

        return null;
    }
    public AnimationState getCurrentState() {
        return currentState;
    }
}
