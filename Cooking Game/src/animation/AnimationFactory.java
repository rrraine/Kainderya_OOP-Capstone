package animation;

import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.List;

public class AnimationFactory {

    private static AnimationFactory instance;
    GamePanel gp;
    String avatar;

    AnimationState currentState;
    Animation base, carryPlate, carryCornedBeef, carryEgg, carryOnion, carryPan, carryRawRice, carryCookedSpamOnly, carryTapa, carryCoke, carryWater,
            carryCookedTapaOnly, carrySpamPlate, carryCookedEggOnly, carryCookedCBeefOnly, carryCookedRiceOnly, carryDirtyPlate, carryOnionOnly,
              carryTapsi, carryCornsilogNoEgg, carrySpamsilogNoEgg, carryTapsilogNoRice, carryCornsilogNoRice, carrySpamsilogNoRice, carryNoMain,
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
        carryCookedSpamOnly = new Animation(gp, avatar, "/holdingSpam/");
        carryTapa = new Animation(gp, avatar, "/holdingTapa/");
        carryCoke = new Animation(gp, avatar, "/holdingCoke/");
        carryWater = new Animation(gp, avatar, "/holdingWater/");

        //ingredients in a plate
        carryCookedTapaOnly = new Animation(gp, avatar, "/cookedTapa/");
        carryCookedCBeefOnly = new Animation(gp, avatar, "/cookedCornedBeef/");
        carrySpamPlate = new Animation(gp, avatar, "/cookedSpam/");
        carryCookedEggOnly = new Animation(gp, avatar, "/cookedEgg/");
        carryDirtyPlate = new Animation(gp, avatar, "/holdingDirtyPlate/");
        carryCookedRiceOnly = new Animation(gp, avatar, "/cookedRice/");

        carryTapsi= new Animation(gp, avatar, "/tapsi/");
        carryCornsilogNoEgg = new Animation(gp, avatar, "/cornsi/");
        carrySpamsilogNoEgg = new Animation(gp, avatar, "/spamsi/");
        carrySpamsilog = new Animation(gp, avatar, "/spamsilog/");
        carryTapsilog= new Animation(gp, avatar, "/tapsilog/");
        carryCornsilog = new Animation(gp, avatar, "/cornsilog/");

        carryTapsilogNoRice = new Animation(gp, avatar, "/taplog/");
        carryCornsilogNoRice = new Animation(gp, avatar, "/cornlog/");
        carrySpamsilogNoRice = new Animation(gp, avatar, "/spamlog/");

        carryOnionOnly = new Animation(gp, avatar, "/onionPlate/");

        carryNoMain = new Animation(gp, avatar, "/noMain/");

        // TODO CARY COOKED SPAM ONLY ANIMATION
        // TODO CARRY TAPSILOG NO EGG ANIMATIONS

        // TODO pan ANIMATIONS:
//            case CARRY_PAN_BURNT:
//            case CARRY_PAN_CBEEF:
//            case CARRY_PAN_EGG:
//            case CARRY_PAN_ONION:
//            case CARRY_PAN_CBEEFEGG:
//            case CARRY_PAN_SPAM:
//            case CARRY_PAN_TAPA:
//            case CARRY_NOMAIN:

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
           case CARRY_RAWRICE:
                return carryRawRice.getSprites();
           case CARRY_SPAM:
                 return carryCookedSpamOnly.getSprites();
           case CARRY_TAPA:
                return carryTapa.getSprites();
            case CARRY_COKE:
                return carryCoke.getSprites();
            case CARRY_WATER:
                return carryWater.getSprites();

            case CARRY_COOKEDRICEONLY:
                return carryCookedRiceOnly.getSprites();
            case CARRY_COOKEDEGGONLY:
                return carryCookedEggOnly.getSprites();
            case CARRY_COOKEDTAPAONLY:
                return carryCookedTapaOnly.getSprites();
            case CARRY_COOKEDCBEEFONLY:
                return carryCookedCBeefOnly.getSprites();
           case CARRY_COOKEDSPAMONLY:
               return carrySpamPlate.getSprites();
            case CARRY_SPAMSILOGNOEGG:
                return carrySpamsilogNoEgg.getSprites();
            case CARRY_SPAMSILOGNORICE:
                return carrySpamsilogNoRice.getSprites();
           case CARRY_TAPSILOGNOEGG:
             return carryCookedEggOnly.getSprites();
            case CARRY_TAPSILOGNORICE:
                return carryTapsilogNoRice.getSprites();
            case CARRY_CORNSILOGNOEGG:
                return carryCornsilogNoEgg.getSprites();
            case CARRY_CORNSILOGNORICE:
                return carryCornsilogNoRice.getSprites();
            case CARRY_ONIONONLY:
                return carryOnionOnly.getSprites();
            case CARRY_NOMAIN:
                return carryNoMain.getSprites();

            case CARRY_CORNSILOGFINAL:
                return carryCornsilog.getSprites();
            case CARRY_SPAMSILOGFINAL:
                return carryCornsilog.getSprites();
            case CARRY_TAPSILOGFINAL:
                return carryTapsilog.getSprites();


                // TODO pan ANIMATIONS:
//            case CARRY_PAN_BURNT:
//            case CARRY_PAN_CBEEF:
//            case CARRY_PAN_EGG:
//            case CARRY_PAN_ONION:
//            case CARRY_PAN_CBEEFEGG:
//            case CARRY_PAN_SPAM:
//            case CARRY_PAN_TAPA:



        }

        return null;
    }
    public AnimationState getCurrentState() {
        return currentState;
    }

    public void resetParams() {
        avatar = "";

        base = null;
        carryPlate = null;
        carryCornedBeef = null;
        carryEgg = null;
        carryOnion = null;
        carryPan = null;
        carryRawRice = null;
        carryCookedSpamOnly = null;
        carryTapa = null;
        carryCoke = null;

        //ingredients in a plate
        carryCookedTapaOnly = null;
        carryCookedCBeefOnly = null;
        carrySpamPlate = null;
        carryCookedEggOnly = null;
        carryDirtyPlate = null;
        carryCookedRiceOnly = null;

        carryTapsi= null;
        carryCornsilogNoEgg = null;
        carrySpamsilogNoEgg = null;
        carrySpamsilog = null;
        carryTapsilog= null;
        carryCornsilog = null;

        carryTapsilogNoRice = null;
        carryCornsilogNoRice = null;
        carrySpamsilogNoRice = null;

        carryOnionOnly = null;

        carryNoMain = null;

        currentState = null;

        instance = null;
    }
}
