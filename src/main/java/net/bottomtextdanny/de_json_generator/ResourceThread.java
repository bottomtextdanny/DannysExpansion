package net.bottomtextdanny.de_json_generator;

import net.bottomtextdanny.de_json_generator.types.base.Generator;

public class ResourceThread extends Thread {
	public static final int WAITING_SEC = 10;
	private long start_ms;
	
	@Override
	public void run() {
		super.run();
        this.start_ms = System.currentTimeMillis();
		
		xdxd();
		sendResources();
		
	}
	
	public void xdxd(){
		synchronized(this){
			while(System.currentTimeMillis() < this.start_ms + (long)WAITING_SEC * 1000L){
			}
		}
	}
	
	public synchronized void sendResources() {
		Generator.sendResources();
	}
}
