package com.utils;

import java.util.ArrayList;
import java.util.List;

public class Counter {
	public static int i = 0;
	public static int showSteps = 0;
	public static int steps = 1;
	public static List<Integer> caches = new ArrayList<Integer>();
	
	public static void add() {
		i += steps;
		if (showSteps > 0 && i % showSteps == 0) {
			System.out.println(i);
		}
	}
	
	public static void add(int psteps) {
		i += psteps;
		if (showSteps > 0 && i % showSteps == 0) {
			System.out.println(i);
		}
	}
	
	public static void caches() {
		caches.add(new Integer(i));
	}
	
	public static void init() {
		i = 0;
		showSteps = 0;
	}
	
	public static void print() {
		System.out.println("当前计数：" + i);
	}
	
	public static void printCaches() {
		int j = 0;
		for (int ic : caches) {
			System.out.println("计数" + (++j) + "：" + ic);
		}
	}
}
