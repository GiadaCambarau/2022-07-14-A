package it.polito.tdp.nyc.model;

import java.util.List;
import java.util.Objects;

public class NTA {
	private String NTACode;
	private List<String> SSDI;
	
	
	public NTA(String nTACode, List<String> sSDI) {
		super();
		NTACode = nTACode;
		SSDI = sSDI;
	}


	public String getNTACode() {
		return NTACode;
	}


	public void setNTACode(String nTACode) {
		NTACode = nTACode;
	}


	public List<String> getSSDI() {
		return SSDI;
	}


	public void setSSDI(List<String> sSDI) {
		SSDI = sSDI;
	}


	@Override
	public int hashCode() {
		return Objects.hash(NTACode, SSDI);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NTA other = (NTA) obj;
		return Objects.equals(NTACode, other.NTACode) && Objects.equals(SSDI, other.SSDI);
	}
	
	
	
	
	

}
