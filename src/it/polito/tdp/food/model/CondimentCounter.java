package it.polito.tdp.food.model;

import it.polito.tdp.food.db.Condiment;

public class CondimentCounter {
	Condiment c1;
	Condiment c2;
	int counter;
	
	public CondimentCounter(Condiment c1, Condiment c2, int counter) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.counter = counter;
	}
	public Condiment getC1() {
		return c1;
	}
	public void setC1(Condiment c1) {
		this.c1 = c1;
	}
	public Condiment getC2() {
		return c2;
	}
	public void setC2(Condiment c2) {
		this.c2 = c2;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
}
