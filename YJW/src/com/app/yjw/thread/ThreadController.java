package com.app.yjw.thread;

import java.util.LinkedList;
import java.util.Queue;

@Deprecated
public class ThreadController {

	private Queue<Thread> threadQueue;
	//private Queue<Thread> runningQueue;
	private static ThreadController controller;

	private ThreadController() {
		threadQueue = new LinkedList<Thread>();
		//runningQueue = new LinkedList<Thread>();
	}

	
	public static ThreadController getInstance() {
		if (controller == null) {
			controller = new ThreadController();
		}
		return controller;
	}

	public void addThread(Thread r) {
		threadQueue.add(r);
	}

	public void removeThread(Thread t) {
		if (!t.isAlive()) {
			threadQueue.remove(t);
		}
	}

	public void RunAsync() {
		for (Thread t : threadQueue) {
			if (!t.isAlive()) {
				//Log.d("Thread",t.getClass().getName());
				t.start();
			}
		}
	}

	public void RunSync() {
		for (Thread t : threadQueue) {
			if (!t.isAlive()) {
				t.run();
			}
		}
	}

	public void runAndRemove() {
		while (!threadQueue.isEmpty()) {
			Thread t = threadQueue.poll();
			if (!t.isAlive()) {
				t.start();
				//t.run();
			}
			//runningQueue.add(t);
		}
	}

	public Thread fetchThread(Class<?> c) {
		for (Thread t : threadQueue) {
			if (t.getClass() == c) {
				return t;
			}
		}
		return null;
	}
	
	public void killAll(){
		//for (Thread t : runningQueue) 
		//	t.stop();		
		android.os.Process.killProcess(android.os.Process.myPid()); 
	}

}
