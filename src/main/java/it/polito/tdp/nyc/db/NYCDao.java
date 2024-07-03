package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.NTA;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getBorough(){
		String sql = "SELECT  distinct n.Borough "
				+ "FROM nyc_wifi_hotspot_locations AS n";
		List<String> borghi = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				borghi.add(res.getString("Borough"));
				}
			
			conn.close();
			return borghi;
		} catch (SQLException e) {
			
			throw new RuntimeException("SQL Error");
		}

	}
	
	public List<NTA> getNta(String b){
		String sql = "SELECT distinct n.NTACode, n.SSID "
				+ "FROM nyc_wifi_hotspot_locations AS n "
				+ "WHERE n.Borough = ? ORDER BY n.NTACode";
		
		List<NTA> vertici = new ArrayList<>(); 
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, b);
			ResultSet res = st.executeQuery();

			String last = "";
			while (res.next()) {
				if (!res.getString("NTACode").equals(last)) {
					List<String> ssdi = new ArrayList<>();
					ssdi.add(res.getString("SSID"));
					NTA n = new NTA(res.getString("NTACode"), ssdi);
					vertici.add(n);
					last =res.getString("NTACode");
				}else {
					vertici.get(vertici.size()-1).getSSDI().add(res.getString("SSID"));
				}
			}
			
			conn.close();
			return vertici;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
	
}
