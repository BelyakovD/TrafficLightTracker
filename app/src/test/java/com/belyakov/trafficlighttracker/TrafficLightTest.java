package com.belyakov.trafficlighttracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrafficLightTest {
    @Test
    public void gettingId_isCorrect() {
        TrafficLight trafficLight = new TrafficLight(1, "Перекресток", 1526345, 1526355, 1526395, 1);
        long expected = 1;
        assertEquals(expected, trafficLight.getId());
    }
    @Test
    public void gettingName_isCorrect() {
        TrafficLight trafficLight = new TrafficLight(1, "Перекресток", 1526345, 1526355, 1526395, 1);
        String expected = "Перекресток";
        assertEquals(expected, trafficLight.getName());
    }
    @Test
    public void gettingGreenStart_isCorrect() {
        TrafficLight trafficLight = new TrafficLight(1, "Перекресток", 1526345, 1526355, 1526395, 1);
        long expected = 1526345;
        assertEquals(expected, trafficLight.getGreenStart());
    }
    @Test
    public void gettingGreenEnd_isCorrect() {
        TrafficLight trafficLight = new TrafficLight(1, "Перекресток", 1526345, 1526355, 1526395, 1);
        long expected = 1526355;
        assertEquals(expected, trafficLight.getGreenEnd());
    }
    @Test
    public void gettingRedEnd_isCorrect() {
        TrafficLight trafficLight = new TrafficLight(1, "Перекресток", 1526345, 1526355, 1526395, 1);
        long expected = 1526395;
        assertEquals(expected, trafficLight.getRedEnd());
    }
    @Test
    public void gettingRouteId_isCorrect() {
        TrafficLight trafficLight = new TrafficLight(1, "Перекресток", 1526345, 1526355, 1526395, 1);
        long expected = 1;
        assertEquals(expected, trafficLight.getRouteId());
    }
    @Test
    public void gettingToString_isCorrect() {
        TrafficLight trafficLight = new TrafficLight(1, "Перекресток", 1526345, 1526355, 1526395, 1);
        long epoch = System.currentTimeMillis()/1000;
        long untilSwitch = (1526395 - 1526355) - ((epoch - 1526345) % (1526395 - 1526345) - (1526355 - 1526345));
        String expected;
        if (untilSwitch > (1526395 - 1526355)) {
            untilSwitch -= (1526395 - 1526355);
            expected = "Перекресток - до переключения " + untilSwitch + " с. (сейчас зеленый)";
        }
        else expected = "Перекресток - до переключения " + untilSwitch + " с. (сейчас красный)";
        assertEquals(expected, trafficLight.toString());
    }
}