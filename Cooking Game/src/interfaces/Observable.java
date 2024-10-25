package interfaces;

import entity.Player;

public interface Observable {
    // FOR CLASSES THAT RENDER DRAWING WITHIN THE PLAYER'S FIELD OF VIEW
    // RENDER OBSERVABLE ITEMS WITHIN CAMERA

    default boolean inView(int tileSize, Player player, int worldX, int worldY) {

        return worldX + tileSize > player.getWorldX() - player.getPlayerCenteredScreenX() &&
                worldX - tileSize < player.getWorldX() + player.getPlayerCenteredScreenX() &&
                worldY + tileSize > player.getWorldY() - player.getPlayerCenteredScreenY() &&
                worldY - tileSize < player.getWorldY() + player.getPlayerCenteredScreenY();
    }
}
