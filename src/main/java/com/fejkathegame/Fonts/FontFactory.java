package com.fejkathegame.Fonts;

import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

/**
 * Created by Swartt on 2015-05-25.
 */
public class FontFactory {
    Font font;
    TrueTypeFont ttf;
    String text;

    public FontFactory(String font, int size, String text) {
        this.font = new Font(font, Font.BOLD, size);
        ttf = new TrueTypeFont(this.font, true);
        this.text = text;
    }

    public void renderText(float x ,float y ){
        ttf.drawString(x, y, text);
    }
}
