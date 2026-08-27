package com.thumbnailgen.service;

import org.junit.jupiter.api.Test;

import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextPlacementTest {

    @Test
    void safeZone_appliesYouTubeMargins() {
        Rectangle zone = TextPlacement.safeZone(1280, 720);
        assertEquals(new Rectangle(80, 60, 1120, 600), zone);
    }

    @Test
    void getPlacementArea_top() {
        Rectangle zone = new Rectangle(80, 60, 1120, 600);
        assertEquals(new Rectangle(80, 60, 1120, 200), TextPlacement.getPlacementArea(zone, "top"));
    }

    @Test
    void getPlacementArea_bottom() {
        Rectangle zone = new Rectangle(80, 60, 1120, 600);
        assertEquals(new Rectangle(80, 460, 1120, 200), TextPlacement.getPlacementArea(zone, "bottom"));
    }

    @Test
    void getPlacementArea_left() {
        Rectangle zone = new Rectangle(80, 60, 1120, 600);
        assertEquals(new Rectangle(80, 60, 560, 600), TextPlacement.getPlacementArea(zone, "left"));
    }

    @Test
    void getPlacementArea_right() {
        Rectangle zone = new Rectangle(80, 60, 1120, 600);
        assertEquals(new Rectangle(640, 60, 560, 600), TextPlacement.getPlacementArea(zone, "right"));
    }

    @Test
    void getPlacementArea_centerAndNullDefault() {
        Rectangle zone = new Rectangle(80, 60, 1120, 600);
        Rectangle expected = new Rectangle(360, 210, 560, 300);
        assertEquals(expected, TextPlacement.getPlacementArea(zone, "center"));
        assertEquals(expected, TextPlacement.getPlacementArea(zone, null));
        assertEquals(expected, TextPlacement.getPlacementArea(zone, "unknown"));
        assertEquals("center", TextPlacement.DEFAULT_PLACEMENT);
    }
}
