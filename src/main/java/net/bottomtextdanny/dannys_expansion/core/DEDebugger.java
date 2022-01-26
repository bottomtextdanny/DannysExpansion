package net.bottomtextdanny.dannys_expansion.core;

import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Formatter;

public class DEDebugger {
    private final String name;
    private String profile;
    private long start;
    private long end;
    private long restingTime;
    private boolean started;
    private boolean endedSuccessfully;

    public DEDebugger(String name) {
        this.name = name;
    }

    public DEDebugger() {
        this.name = "Unknown";
    }

    public void start(String profile, long rest) {
        this.restingTime = rest;
        this.endedSuccessfully = false;
        if (System.currentTimeMillis() - this.end > this.restingTime) {
            this.profile = profile;
            this.start = System.currentTimeMillis();
            this.started = true;
        }
    }

    public void end() {
        if (this.started) {
            this.end = System.currentTimeMillis();
            this.started = false;
            this.endedSuccessfully = true;
        }
    }

    public String info() {
        StringBuilder infoBuilder = new StringBuilder();
        if (this.endedSuccessfully) {
            infoBuilder.append(this.name.length() > 0 ? (this.end - this.start) + " ms from: " + this.profile : this.name + ": " + (this.end - this.start) + " ms from: Unknown");
        } else {
            infoBuilder.append("Debug is unfinished!");
        }

        return infoBuilder.toString();
    }


    @OnlyIn(Dist.CLIENT)
    public void chat() {
        if (this.endedSuccessfully) {
	        Formatter form = new Formatter();
	
	        ClientInstance.chatMsg(form.format("%d ms from: " + this.profile, this.end - this.start).toString());
        }

        clear();
    }

    public void sPrint() {
        if (this.endedSuccessfully) {
            
            Formatter form = new Formatter();
	        System.out.println(form.format("%.4f ns from: " + this.profile, (float)((this.end - this.start) / 100000)));
	        
        }
        clear();
    }

    public void clear() {
        this.profile = "";
    }
}
