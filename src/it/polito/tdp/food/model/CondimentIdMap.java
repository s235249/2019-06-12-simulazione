package it.polito.tdp.food.model;

import java.util.HashMap;

import it.polito.tdp.food.db.Condiment;

public class CondimentIdMap extends HashMap<Integer,Condiment>{
	public Condiment get(Condiment condiment) {
		Condiment old = super.get(condiment.getCondiment_id());
		if (old != null) {
			return old;
		}
		super.put(condiment.getCondiment_id(), condiment);
		return condiment;
	}
}
