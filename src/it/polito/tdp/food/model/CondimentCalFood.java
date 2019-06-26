package it.polito.tdp.food.model;

import it.polito.tdp.food.db.Condiment;

public class CondimentCalFood implements Comparable<CondimentCalFood>{
	private Condiment condiment;
	private int peso;
	public CondimentCalFood(Condiment condiment, int peso) {
		super();
		this.condiment = condiment;
		this.peso = peso;
	}
	
	@Override
	public String toString() {
		return condiment.getDisplay_name()+" "+condiment.getCondiment_calories()+"       "+peso+"\n";
	}

	@Override
	public int compareTo(CondimentCalFood o) {
		return Double.compare(this.condiment.getCondiment_calories(), o.condiment.getCondiment_calories());
	}

}
