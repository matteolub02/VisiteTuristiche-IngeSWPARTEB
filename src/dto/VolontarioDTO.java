package dto;

import java.util.List;
import java.util.Map;

public class VolontarioDTO  implements UserDTO, DTO {
	private String username = null;
	private List<String> tipi_visite = null;
	
	public VolontarioDTO(String username) {
		this.username = username;
	}
	
	public VolontarioDTO(String username, List<String> tipi_visite) {
		this.username = username;
		this.tipi_visite = tipi_visite;
	}

	public String getUsername() {
		return username;
	}

	public List<String> getTipi_visite() {
		return tipi_visite;
	}
	
	public String toString() {
		String result = "Volontario: " + getUsername() + "\nTipi associati: " + getTipi_visite().toString();
		return result;
	}

	@Override
	public Map<String, List<String>> infoDTO() {
		return null;
	}
	
}
