package it.polito.tdp.nyc.model;

import java.util.Objects;

public class Evento implements Comparable<Evento>{
	
	public enum EventType{
		share,
		stop
	}

	private int time;
	private NTA nta;
	private int durata;
	private EventType type;
	
	public Evento(int time, NTA nta, int durata, EventType type) {
		super();
		this.time = time;
		this.nta = nta;
		this.durata = durata;
		this.type = type;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public NTA getNta() {
		return nta;
	}

	public void setNta(NTA nta) {
		this.nta = nta;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(durata, nta, time, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		return durata == other.durata && Objects.equals(nta, other.nta) && time == other.time && type == other.type;
	}

	@Override
	public int compareTo(Evento o) {
		return this.time-o.time;
	}
	
	
	
	
}
