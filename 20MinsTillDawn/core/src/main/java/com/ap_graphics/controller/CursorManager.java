package com.ap_graphics.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

public class CursorManager {
    private Cursor normalCursor;
    private Cursor clickedCursor;

    public CursorManager() {
        // Load normal cursor (replace with your PNG paths)
        Pixmap normalPixmap = new Pixmap(Gdx.files.internal("images/visual/T_Cursor.png"));
        normalCursor = Gdx.graphics.newCursor(normalPixmap, 0, 0);
        normalPixmap.dispose();

        // Load clicked cursor
        Pixmap clickedPixmap = new Pixmap(Gdx.files.internal("images/visual/T_Cursor.png"));
        clickedCursor = Gdx.graphics.newCursor(clickedPixmap, 0, 0);
        clickedPixmap.dispose();
    }

    public void update(boolean isMouseDown) {
        Gdx.graphics.setCursor(isMouseDown ? clickedCursor : normalCursor);
    }

    public void dispose() {
        normalCursor.dispose();
        clickedCursor.dispose();
    }
}
