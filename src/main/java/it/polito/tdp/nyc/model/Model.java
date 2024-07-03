package it.polito.tdp.nyc.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private List<String> borghi;
	private NYCDao dao;
	private Graph<NTA, DefaultWeightedEdge> graph;
	
	
	public Model() {
		super();
		
		this.dao = new NYCDao();
		this.borghi =dao.getBorough();
		this.graph = new SimpleWeightedGraph<NTA, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	public List<String> getBorghi(){
		return this.borghi;
	}
	
	public List<NTA> loadVertici(String b){
		return dao.getNta(b); 
	}
	
	
	public void creaGrafo(String b) {
		List <NTA> vertici= loadVertici(b);
		Graphs.addAllVertices(this.graph, vertici);
		int count =0;
		System.out.println("GRAFO CREATO! \n\n#Vertici: "+ vertici.size()+"\n");
		for (NTA n1: vertici) {
			for (NTA n2 : vertici) {
				if (!n1.equals(n2)) {
					Set<String> unione = new HashSet<>(n1.getSSDI());
					unione.addAll(n2.getSSDI());
					if (unione.size()>0) {
						Graphs.addEdge(this.graph, n1, n2, unione.size());
					}
					
				}
				
			}
		}
		System.out.println("#Archi: "+this.graph.edgeSet().size());

	}
	
	public int loadArchi() {
		int  n= this.graph.edgeSet().size();
		return  n;
	}
	
	public List<Arco> analisiArchi(){
		double media =0;
		for (DefaultWeightedEdge e : this.graph.edgeSet()) {
			media += this.graph.getEdgeWeight(e);
		}
		media = media/this.graph.edgeSet().size();
		List<Arco> result = new ArrayList<>();
		for (DefaultWeightedEdge e : this.graph.edgeSet()) {
			if (this.graph.getEdgeWeight(e)>media) {
				result.add(new Arco(this.graph.getEdgeSource(e).getNTACode(),this.graph.getEdgeTarget(e).getNTACode(), this.graph.getEdgeWeight(e) ));
			}
		}
		Collections.sort(result);
		return result;
	}
	
	
	public Map<NTA, Integer> simula(double p, int durata) {
		Simulatore sim = new Simulatore(p, durata, this.graph);
		sim.inizializzazione();
		sim.run();
		return sim.getnTOTShare();
		
	}
	
	
	
	

	
}
