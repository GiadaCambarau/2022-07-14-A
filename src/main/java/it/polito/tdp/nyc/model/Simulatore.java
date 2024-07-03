package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Evento.EventType;

public class Simulatore {
	//PARAMENTRI DI INPUT 
	private double p;
	private int duration;
	
	//STATO DEL SISTEMA 
	private Graph<NTA, DefaultWeightedEdge> graph;
	private Map<NTA, Integer> nShare;
	private List<NTA> vertici;
	
	//OUTOUT 
	private  Map<NTA, Integer> nTOTShare;
	
	//EVENTI
	private PriorityQueue<Evento> queue;

	public Simulatore(double p, int duration, Graph<NTA, DefaultWeightedEdge> graph) {
		super();
		this.p = p;
		this.duration = duration;
		this.graph = graph;
	}
	public void inizializzazione() {
		
		this.nShare = new HashMap<NTA, Integer>();
		this.nTOTShare = new HashMap<NTA, Integer>();
		
		for (NTA n : this.graph.vertexSet()) {
			this.nShare.put(n, 0);
			this.nTOTShare.put(n, 0);
		}
		
		this.vertici = new ArrayList<>(this.graph.vertexSet());
		
		this.queue = new PriorityQueue<>();
		
		//creo gli eventi iniziali
		
		for (int t=0; t<100; t++) {
			if (Math.random()<=this.p) {
				int n = (int)(Math.random()* this.vertici.size());
				this.queue.add(new Evento (t, this.vertici.get(n), this.duration, EventType.share));
			}
		}
	}
	
	public void run () {
		while (!this.queue.isEmpty()) {
			
			Evento e =  this.queue.poll();
			if (e.getTime()>=100) {
				break;
			}
			int time = e.getTime();
			int durata = e.getDurata();
			NTA nta = e.getNta();
			
			System.out.print(e.getType()+" "+time+" "+ nta.getNTACode()+" "+durata+"\n");
			
			switch(e.getType()) {
			case share:
				this.nShare.put(nta, this.nShare.get(nta)+1);
				this.nTOTShare.put(nta, this.nTOTShare.get(nta)+1);
				this.queue.add(new Evento (time+durata, nta,0 , EventType.stop));
				
				
				//ricondivisione
				if (durata/2 >0) {
					NTA nuovo = trova(nta);
					if (nuovo != null) {
						this.queue.add(new Evento (time+1, nuovo, durata / 2, EventType.share));
					}
				}
				break;
				
			case stop:
				this.nShare.put(nta, this.nShare.get(nta)-1);
				break;
			}
		}
	}
	private NTA trova(NTA nta) {
		int max = -1;
		NTA best = null;
		for (DefaultWeightedEdge e : this.graph.outgoingEdgesOf(nta)) {
			NTA vicino = Graphs.getOppositeVertex(this.graph, e, nta);
			int peso = (int) (this.graph.getEdgeWeight(e));
			
			if (peso>max && this.nShare.get(vicino)==0) {
				max = peso;
				best = vicino;
			}
		}
			
		return best;
	}
	public Map<NTA, Integer> getnTOTShare() {
		return nTOTShare;
	}

	
	
	
	

}
