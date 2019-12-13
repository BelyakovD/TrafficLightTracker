package com.belyakov.trafficlighttracker;

public class TrafficLight {
    private long id;
    private String name;
    private long greenStart;
    private long greenEnd;
    private long redEnd;
    private long routeId;

    TrafficLight(long id, String name, long greenStart, long greenEnd, long redEnd, long routeId){
        this.id = id;
        this.name = name;
        this.greenStart = greenStart;
        this.greenEnd = greenEnd;
        this.redEnd = redEnd;
        this.routeId = routeId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGreenStart() {
        return greenStart;
    }

    public void setGreenStart(long greenStart) {
        this.greenStart = greenStart;
    }

    public long getGreenEnd() {
        return greenEnd;
    }

    public void setGreenEnd(long greenEnd) {
        this.greenEnd = greenEnd;
    }

    public long getRedEnd() {
        return redEnd;
    }

    public void setRedEnd(long redEnd) {
        this.redEnd = redEnd;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        long epoch = System.currentTimeMillis()/1000;
        long green = this.greenStart;
        long red = this.greenEnd;
        long green2 = this.redEnd;
        long period1 = red - green;
        long period2 = green2 - red;
        long totalPeriod = period1 + period2;
        long time = (epoch - green) % totalPeriod - period1;
        long untilSwitch = period2 - time;
        if (untilSwitch > period2){
            untilSwitch -= period2;
            return this.name + " - до переключения " + untilSwitch + " с. (сейчас зеленый)";
        }
        else return this.name + " - до переключения " + untilSwitch + " с. (сейчас красный)";
    }
}
