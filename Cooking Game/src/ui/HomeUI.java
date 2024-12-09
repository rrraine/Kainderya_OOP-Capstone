package ui;

import game.Time;
import interfaces.Drawable;
import main.GamePanel;
import main.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// INNER CLASS UI STATES ---------------------------------------------------------
public class HomeUI extends UI implements Drawable {

    public static substate homeState;

    public enum substate { TITLE, SELECTION, CREDITS, MULTIPLAYER, COMINGSOON }

    private BufferedImage wallpaper;
    private BufferedImage wallpaperFront;

    private int loadTime;

    String inputText = " ";
    private boolean characterSelected = false;

    // ANIMATED WALLPAPER
    UIElement girl, boy, civilian;

    UIElement loadingBar;


    public HomeUI(GamePanel gp, Time time) {
        super(gp, time);
        homeState = substate.TITLE;
        wallpaper = importWallpaper("wallpapers", "homeUI", "homeUI_Back");
        wallpaperFront = importWallpaper("wallpapers", "homeUI", "homeUI_Front");

        // ANIMATED WALLPAPER
        girl = new UIElement(gp, "npc", "studentFemale",  30,true);
        girl.reposition((gp.tileSize * 8) + gp.tileSize * 5, (gp.tileSize *3+20) + gp.tileSize * 5);

        boy = new UIElement(gp, "npc","studentMale", 16,false);
        boy.reposition(gp.tileSize + gp.tileSize * 5, (gp.tileSize *3+20) + gp.tileSize * 5);

        civilian = new UIElement(gp, "npc","civilianfem1", 20, true);
        civilian.reposition((gp.tileSize * 11) + gp.tileSize * 5, (gp.tileSize *3+20) + gp.tileSize * 5);


    }
    @Override
    public void draw(Graphics2D g2) {

        // HOME BACKGROUND BACK
        g2.drawImage(wallpaper, 0, 0, gp.screenWidth, gp.screenHeight, null);

        switch (homeState) {

            case TITLE:
                homeTITLE(g2);
                break;

            case SELECTION:
                homeSELECTION(g2);
                break;

            case CREDITS:
                homeCREDITS(g2);
                break;

            case MULTIPLAYER:
                homeMULTIPLAYER(g2);
                break;

            case COMINGSOON:
                homeCOMINGSOON(g2);
                break;


        }
    }

    // SUB-STATES
    private void homeTITLE(Graphics2D g2) {

        g2.setFont(paytoneOne);

        // ANIMATED WALLPAPER
        girl.drawAvatarSideMoving(g2);
        boy.drawAvatarSideMoving(g2);
        civilian.drawAvatarSideMoving(g2);

        // HOME BACKGROUND FRONT
        g2.drawImage(wallpaperFront, 0, 0, gp.screenWidth, gp.screenHeight, null);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 135F));
        String text = "KAiNDERYA";
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        int y = gp.tileSize * 4 - 40;

        // LETTERING: WHITE
        g2.setColor(Color.WHITE);
        g2.drawString(text, x + 16, y + 16);

        // SHADOW TEXT COLOR
        g2.setColor(primaryAccent);
        g2.drawString(text, x + 14, y + 14);

        // BORDERING
        drawLetterBorder(g2, text, Color.BLACK, 6, x, y);

        // MAIN TEXT COLOR
        if (Utility.Regulator.flipSwitch(2))
            g2.setColor(primary);
        else
            g2.setColor(secondary);

        if (Utility.Regulator.flipSwitch(2))
            g2.drawString(text, x , y);
        else
            g2.drawString(text, x + shakeEffect(1), y);


        // NEW GAME
        g2.setFont(g2.getFont().deriveFont(48F));
        text = "NEW GAME";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize * 3 -5;
        // SHADOW TEXT COLOR
        g2.setColor(Color.BLACK);
        g2.drawString(text, x +7, y +5);
        // BORDERING
        UI.drawLetterBorder(g2, text, Color.BLACK, 3, x, y);
        // MAIN TEXT COLOR
        if (command == 0) {
            drawCursor(g2, text, x, y, false, false);
        }
        else {
            g2.setColor(Color.WHITE);
        }
        g2.drawString(text, x, y);


        // CREDITS
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "CREDITS";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize;
        // SHADOW TEXT COLOR
        g2.setColor(Color.BLACK);
        g2.drawString(text, x +7, y +5);
        // BORDERING
        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);
        // MAIN TEXT COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if (command == 1) {
            drawCursor(g2, text, x, y, false, false);
        } else {
            g2.setColor(Color.WHITE);
        }
        g2.drawString(text, x, y);

        // QUIT
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "QUIT";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize;
        // SHADOW TEXT COLOR
        g2.setColor(Color.BLACK);
        g2.drawString(text, x +7, y +5);
        // BORDERING
        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);
        // MAIN TEXT COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if (command == 2) {
            drawCursor(g2, text, x, y, false, false);
            g2.setColor(secondary);
        } else {
            g2.setColor(Color.WHITE);
        }
        g2.drawString(text, x, y);
    }
    private void homeSELECTION(Graphics2D g2) {

        // SUB-WINDOW
        int frameX = gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 18;
        int frameHeight = gp.tileSize * 10;
        drawPopUpWindow(g2, frameX, frameY, frameWidth, frameHeight);

        g2.setFont(luckiestGuy);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(42F));

        String text = "Select Avatar";
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        int y = gp.tileSize * 2 + 25;
        // SHADOW TEXT COLOR
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 6, y);
        // MAIN TEXT COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // instructions
        text = "ESC to Reset   |   ENTER to Confirm";
        g2.setFont(g2.getFont().deriveFont(18F));
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize - 10;
        drawLetterBorder(g2, text, Color.BLACK, 2, x, y);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // SELECTION HERE
        int gridX = gp.tileSize * 3 + 24, gridY = gp.tileSize * 4;
        int gridWidth = gp.tileSize * 3;
        int gridHeight = gp.tileSize * 4;

        cook1.reposition(gridX + gp.tileSize - 18, gridY + gp.tileSize + 20);

        g2.setFont(g2.getFont().deriveFont(30F));

        // GRID 1
        text = "Miguel";
        x = gridX + gp.tileSize - 17;
        y = gridY * 2 + gp.tileSize /2;
        // BORDER
        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);
        // MAIN TEXT COLOR

        // DRAW NAME
        if (gp.getKeyB().getCharacterSelectedNum() == 1) {
            g2.setColor(player1);
        }
        else if (command == 0) {
            g2.setColor(primary);
        } else
            g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        if (command == 0) {
            drawCursor(g2, text, x, y, true, false);
        }

        // DRAW GRID BOX
        if (gp.getKeyB().getCharacterSelectedNum() == 1) {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, primary, Color.BLACK);
            cook1.drawAvatarFrontMoving(g2);
        }
        else if (command == 0) {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, primaryAccent, primary);
            cook1.drawAvatarFrontMoving(g2);
        }
        else {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, transWhite, Color.BLACK);
            cook1.drawAvatarFrontStatic(g2);
        }

        // GRID 2
        gridX += (gp.tileSize * 3) + 30;
        drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight);

        cook2.reposition(gridX + gp.tileSize - 18, gridY + gp.tileSize + 20);

        text = "Gina";
        x = gridX + gp.tileSize - 4;
        y = gridY * 2 + gp.tileSize /2;
        // BORDER
        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);

        // DRAW NAME
        if (gp.getKeyB().getCharacterSelectedNum() == 2) {
            g2.setColor(player1);
        }
        else if (command == 1) {
            g2.setColor(primary);
        } else
            g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        if (command == 1) {
            drawCursor(g2, text, x, y, true, false);
        }

        // DRAW GRID BOX
        if (gp.getKeyB().getCharacterSelectedNum() == 2) {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, primary, Color.BLACK);
            cook2.drawAvatarFrontMoving(g2);
        }
        else if (command == 1) {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, primaryAccent, primary);
            cook2.drawAvatarFrontMoving(g2);
        } else {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, transWhite, Color.BLACK);
            cook2.drawAvatarFrontStatic(g2);
        }


        // GRID 3
        gridX += (gp.tileSize * 3) + 30;
        drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight);

        cook3.reposition(gridX + gp.tileSize - 18, gridY + gp.tileSize + 20);

        text = "Javier";
        x = gridX + gp.tileSize - 18;
        y = gridY * 2 + gp.tileSize /2;
        // BORDER
        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);

        // DRAW NAME
        if (gp.getKeyB().getCharacterSelectedNum() == 3) {
            g2.setColor(player1);
        }
        else if (command == 2) {
            g2.setColor(primary);
        } else
            g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        if (command == 2) {
            drawCursor(g2, text, x, y, true, false);
        }

        // DRAW GRID BOX
        if (gp.getKeyB().getCharacterSelectedNum() == 3) {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, primary, Color.BLACK);
            cook3.drawAvatarFrontMoving(g2);
        }
        else if (command == 2) {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, primaryAccent, primary);
            cook3.drawAvatarFrontMoving(g2);
        } else {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, transWhite, Color.BLACK);
            cook3.drawAvatarFrontStatic(g2);
        }


        // GRID 4
        gridX += (gp.tileSize * 3) + 30;
        drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight);

        cook4.reposition(gridX + gp.tileSize - 18, gridY + gp.tileSize + 20);

        text = "Sofia";
        x = gridX + gp.tileSize - 6;
        y = gridY * 2 + gp.tileSize /2;
        // BORDER
        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);

        // DRAW NAME
        if (gp.getKeyB().getCharacterSelectedNum() == 4) {
            g2.setColor(player1);
        }
        else if (command == 3) {
            g2.setColor(primary);
        } else
            g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        if (command == 3) {
            drawCursor(g2, text, x, y, true, false);
        }

        // DRAW GRID BOX
        if (gp.getKeyB().getCharacterSelectedNum() == 4) {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, primary, Color.BLACK);
            cook4.drawAvatarFrontMoving(g2);
        }
        else if (command == 3) {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, primaryAccent, primary);
            cook4.drawAvatarFrontMoving(g2);
        } else {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, transWhite, Color.BLACK);
            cook4.drawAvatarFrontStatic(g2);
        }

        //  TEXTFIELD HERE!!!!!!!!!!!!!!
        text = "Name: ";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2) - (gp.tileSize * 4);
        y += gp.tileSize + 32;
        // SHADOW TEXT COLOR
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 6, y);
        // BORDER
        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);

        // DRAW TEXT
        if (gp.getKeyB().isTypingName()) {
            drawCursor(g2, text, x, y, true, false);
            g2.setColor(player1);
        }
        else if (command == 4 && !gp.getKeyB().isTypingName()) {
            drawCursor(g2, text, x, y, true, false);
            g2.setColor(primary);
        } else
            g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // NAME FIELD
        x += gp.tileSize * 3;
        y -= 40;
        int width = gp.tileSize * 7;
        int height = gp.tileSize - 10;

        if (gp.getKeyB().isTypingName()) {

            if (gp.getKeyB().getInputText().isBlank()) {
                drawPopUpWindow(g2, x, y, width, height, secondary, Color.BLACK);
            }
            else {
                drawPopUpWindow(g2, x, y, width, height, player1, Color.BLACK);
            }

        }
        else if (command == 4) {
            drawPopUpWindow(g2, x, y, width, height, transWhite, Color.BLACK);
        }


        g2.setColor(Color.BLACK);
        g2.setFont(fredokaSemiBold);
        g2.setFont(g2.getFont().deriveFont(24F));
        g2.drawString(gp.getKeyB().getInputText(), x + 25, y + 35);
    }
    private void homeCREDITS(Graphics2D g2) {

        // SUB-WINDOW
        int frameX = gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 18;
        int frameHeight = gp.tileSize * 10;
        drawPopUpWindow(g2, frameX, frameY, frameWidth, frameHeight);

        g2.setFont(luckiestGuy);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(42F));

        String text = "CREDITS <3";
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        int y = gp.tileSize * 2 + 25;
        // SHADOW TEXT COLOR
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 6, y);
        // MAIN TEXT COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // TEAM
        g2.setFont(g2.getFont().deriveFont(30F));
        text = "Procramming   |   2024";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize + 32;

        // LETTERING: WHITE
        g2.drawString(text, x + 7, y + 4);
        // SHADOW TEXT COLOR
        g2.setColor(primaryAccent);
        g2.drawString(text, x + 5, y + 2);
        // BORDER
        drawLetterBorder(g2, text, Color.BLACK, 2, x, y);
        // MAIN
        g2.setColor(orange);
        g2.drawString(text, x, y);

        // OUR BELOVED NAMES
        g2.setFont(g2.getFont().deriveFont(42F));
        // GK
        text = "Carreon       Gianna Katrin";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize + 38;

        // LETTERING: WHITE
        g2.setColor(Color.WHITE);
        g2.drawString(text, x + 14, y + 6);
        // SHADOW TEXT COLOR
        g2.setColor(primaryAccent);
        g2.drawString(text, x + 11, y + 4);

        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);
        g2.setColor(blue);
        g2.drawString(text, x, y);

        // RON
        text = "Jatayna       Aaron Dei";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize;

        // LETTERING: WHITE
        g2.setColor(Color.WHITE);
        g2.drawString(text, x + 14, y + 6);
        // SHADOW TEXT COLOR
        g2.setColor(primaryAccent);
        g2.drawString(text, x + 11, y + 4);

        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);
        g2.setColor(secondary);
        g2.drawString(text, x, y);

        // VJ
        text = "Juarez       Venice Jonah";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize;

        // LETTERING: WHITE
        g2.setColor(Color.WHITE);
        g2.drawString(text, x + 14, y + 6);
        // SHADOW TEXT COLOR
        g2.setColor(primaryAccent);
        g2.drawString(text, x + 11, y + 4);

        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);
        g2.setColor(blue);
        g2.drawString(text, x, y);

        // LORI
        text = "Quezada       Lorraine";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize;

        // LETTERING: WHITE
        g2.setColor(Color.WHITE);
        g2.drawString(text, x + 14, y + 6);
        // SHADOW TEXT COLOR
        g2.setColor(primaryAccent);
        g2.drawString(text, x + 11, y + 4);

        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);
        g2.setColor(secondary);
        g2.drawString(text, x, y);

        // BACK
        g2.setFont(g2.getFont().deriveFont(30F));
        text = "Back";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize + 48;
        drawLetterBorder(g2, text, Color.BLACK, 2, x, y);
        g2.setColor(primary);
        g2.drawString(text, x, y);
        drawCursor(g2, text, x, y, false, true);
    }
    private void homeMULTIPLAYER(Graphics2D g2) {

        g2.setFont(luckiestGuy);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(42F));

        String text = "MULTIPLAYER";
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        int y = gp.tileSize * 2 + 25;
        // SHADOW TEXT COLOR
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 6, y);
        // MAIN TEXT COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // SINGLE PLAYER -----

        // DRAW GRID BOX
        int gridX = gp.tileSize * 4 + 52, gridY = gp.tileSize * 4 -20;
        int gridWidth = gp.tileSize * 5;
        int gridHeight = gp.tileSize * 6;

        cook1.reposition(gridX + gp.tileSize + 42, gridY + gp.tileSize + 20);

        g2.setFont(g2.getFont().deriveFont(30F));

        // PERSON MOVING
        if (command == 0) {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, primaryAccent, primary);
            cook1.drawAvatarFrontMoving(g2);
        } else {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, transBlack, Color.WHITE);
            cook1.drawAvatarFrontStatic(g2);
        }

        // DRAW NAME
        text = "ONE PLAYER";
        x = gridX + gp.tileSize + 18;
        y = gridY * 2 + gp.tileSize;
        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);

        if (command == 0) {
            g2.setColor(primary);
            drawCursor(g2, text, x, y, true, false);
        } else
            g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);


        // TWO PLAYERS -----

        // DRAW GRID BOX
        gridX += (gp.tileSize * 5) + 30;
        drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight);
        cook2.reposition(gridX + gp.tileSize - 18, gridY + gp.tileSize + 20);
        cook4.reposition(gridX + gp.tileSize * 2 + 36, gridY + gp.tileSize + 20);

        if (command == 1) {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, primaryAccent, primary);
            cook2.drawAvatarFrontMoving(g2);
            cook4.drawAvatarFrontMoving(g2);
        } else {
            drawPopUpWindow(g2, gridX, gridY, gridWidth, gridHeight, transBlack, Color.WHITE);
            cook2.drawAvatarFrontStatic(g2);
            cook4.drawAvatarFrontStatic(g2);
        }

        // DRAW NAME
        text = "TWO PLAYERS";
        x = gridX + gp.tileSize + 8;
        y = gridY * 2 + gp.tileSize;
        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);

        if (command == 1) {
            g2.setColor(primary);
            drawCursor(g2, text, x, y, true, false);
        } else
            g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);


    }
    private void homeCOMINGSOON(Graphics2D g2) {

        // SUB-WINDOW
        int frameX = gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 18;
        int frameHeight = gp.tileSize * 10;
        drawPopUpWindow(g2, frameX, frameY, frameWidth, frameHeight);

        g2.setFont(luckiestGuy);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(42F));

        String text = "SOMETHING AWESOME IS COOKING— STAY TUNED.";
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        int y = gp.screenHeight /2;
        // SHADOW TEXT COLOR
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 6, y);
        // BORDERING
        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);
        // MAIN TEXT COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // BACK
        g2.setFont(g2.getFont().deriveFont(30F));
        text = "Back";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y += gp.tileSize *2;
        drawLetterBorder(g2, text, Color.BLACK, 2, x, y);
        g2.setColor(primary);
        g2.drawString(text, x, y);
        drawCursor(g2, text, x, y, false, true);
    }

    public boolean isCharacterSelected() {
        return characterSelected;
    }

    public void setCharacterSelected(boolean characterSelected) {
        this.characterSelected = characterSelected;
    }
}
