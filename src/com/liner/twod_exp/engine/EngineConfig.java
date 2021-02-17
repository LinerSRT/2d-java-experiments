package com.liner.twod_exp.engine;

public class EngineConfig {
    private final int screenWidth;
    private final int screenHeight;
    private final String name;
    private final int ticksPerSecond;
    private final int framesPerSecond;

    private EngineConfig(Builder builder){
        this.screenHeight = builder.screenHeight;
        this.screenWidth = builder.screenWidth;
        this.name = builder.name;
        this.ticksPerSecond = builder.ticksPerSecond;
        this.framesPerSecond = builder.framesPerSecond;
    }


    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public String getName() {
        return name;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public int getTicksPerSecond() {
        return ticksPerSecond;
    }

    public static class Builder{
        private int screenWidth = 300;
        private int screenHeight = 300;
        private int ticksPerSecond = 60;
        private int framesPerSecond = 60;

        private String name = "Sample";

        public Builder() {
        }

        public Builder setScreenWidth(int screenWidth) {
            this.screenWidth = screenWidth;
            return this;
        }

        public Builder setScreenHeight(int screenHeight) {
            this.screenHeight = screenHeight;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFramesPerSecond(int framesPerSecond) {
            this.framesPerSecond = framesPerSecond;
            return this;
        }

        public Builder setTicksPerSecond(int ticksPerSecond) {
            this.ticksPerSecond = ticksPerSecond;
            return this;
        }

        public EngineConfig build(){
            return new EngineConfig(this);
        }
    }
}
