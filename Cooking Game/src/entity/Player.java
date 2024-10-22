package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Boots;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    // PLAYER SCREEN COORDINATES ALWAYS IN CENTER
    public final int screenX;
    public final int screenY;

    // NUM OF ITEMS PLAYER HAS
    int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        // PLAYER CENTERED ON SCREEN
        screenX = gp.screenWidth / 2 - (gp.tileSize /2);
        screenY = gp.screenHeight / 2 - (gp.tileSize /2);

        // COLLISION DIMENSIONS
        solidArea = new Rectangle(11, 16, 26, 31);
        // DEFAULT COLLISION
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        // STARTING POSITION
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 5;
        direction = "down";
    }

    public void getPlayerImage() {

        try {

            // load images
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            // DETECT DIRECTION BY KEYSTROKE
            if (keyH.upPressed) {
                direction = "up";
            }
            else if (keyH.downPressed) {
                direction = "down";
            }
            else if (keyH.leftPressed) {
                direction = "left";
            }
            else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objectIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {

                switch (direction) {

                    case "up":
                        worldY -= speed;
                        break;

                    case "down":
                        worldY += speed;
                        break;

                    case "left":
                        worldX -= speed;
                        break;

                    case "right":
                        worldX += speed;
                        break;
                }
            }


            // ALTERNATE SPRITE IMAGE EVERY 12 FRAMES
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {

        // INDEX 999 IF OBJ UNTOUCHED
        if (i != 999) {

            // DELETE TOUCHED OBJ FROM WORLD
            String objName = gp.obj[i].name;

            switch (objName) {

                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    System.out.println("Key: " + hasKey);
                    break;

                case "Door":
                    if (hasKey > 0) {
                        // CHANGE TO COLLISION = FALSE IF U DONT WANT TO DELETE OBJ
                        gp.obj[i] = null;
                        System.out.println("Door opened");
                        hasKey--;
                    }
                    System.out.println("Key: " + hasKey);
                    break;

                case "Boots":
                    if (gp.obj[i] instanceof OBJ_Boots) {

                        speed += ((OBJ_Boots)gp.obj[i]).speedIncrease;
                    }
                    gp.obj[i] = null;
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;

            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;

            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;

            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}