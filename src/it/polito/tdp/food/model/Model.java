package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	private FoodDao dao;
	private SimpleWeightedGraph<Condiment, DefaultWeightedEdge> grafo;
	private CondimentIdMap condimentIdMap;
	private List<Condiment> ottima;
	double maxCalorie = 0;
	
	public Model() {
		dao = new FoodDao();
		condimentIdMap = new CondimentIdMap();
	}
	
	public List<Condiment> listCondimentUnderCalories(double cal){
		return dao.listCondimentUnderCalories(cal, condimentIdMap);
	}
	
	public void createGraph(double cal) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<Condiment> ingredienti = dao.listCondimentUnderCalories(cal, condimentIdMap);
		Graphs.addAllVertices(grafo, ingredienti);
		
		for(CondimentCounter cc : dao.getCondimentCounter(cal, condimentIdMap)) {
			Graphs.addEdgeWithVertices(grafo, cc.getC1(), cc.getC2(), cc.getCounter());
		}
		
		System.out.println("Grafo creato con "+grafo.vertexSet().size()+" vertici e "+
		grafo.edgeSet().size()+" archi");
	}

	public int getNumeroVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getNumeroArchi() {
		return grafo.edgeSet().size();
	}
	
	public List<CondimentCalFood> getCalFood(){
		List<CondimentCalFood> condimentCalFood = new ArrayList<>();
		
		for(Condiment c : this.grafo.vertexSet()) {
			int peso = 0;
			for(Condiment vicino : Graphs.neighborListOf(grafo, c)) {
				DefaultWeightedEdge e = grafo.getEdge(c, vicino);
				peso += this.grafo.getEdgeWeight(e);
			}
			condimentCalFood.add(new CondimentCalFood(c, peso));
		}
		Collections.sort(condimentCalFood);
		return condimentCalFood;
	}
	
	public List<Condiment> calcolaDieta(Condiment condPartenza){
		this.ottima = new ArrayList<Condiment>();
		List<Condiment> parziale = new ArrayList<Condiment>();
		parziale.add(condPartenza);
		for(Condiment c : parziale) {
			System.out.println(c);
		}
		cerca(parziale);
		
		return ottima;
	}
	
	private void cerca(List<Condiment> parziale) {
		if(calcolaCalorie(parziale) > maxCalorie) {
			this.ottima = new LinkedList<>(parziale); // clonare la lista
			maxCalorie = calcolaCalorie(parziale);
		}
		
		for(Condiment c : this.grafo.vertexSet()) {
			if(!parziale.contains(c)) {
				for(Condiment ingredientiInLista : parziale) {
					System.out.println(ingredientiInLista);
					if(!Graphs.neighborListOf(grafo, ingredientiInLista).contains(c)) {
						parziale.add(c);
						cerca(parziale);
						parziale.remove(c);
					}
				}
			}
		}
	}

	private double calcolaCalorie(List<Condiment> parziale) {
		double calorieDieta = 0;
		for(Condiment c : parziale) {
			calorieDieta += c.getCondiment_calories();
		}
		return calorieDieta;
	}
}
