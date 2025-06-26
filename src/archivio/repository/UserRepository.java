package archivio.repository;

import java.util.List;

import dto.DataDisponibilitaDTO;
import dto.PrenotazioneDTO;
import dto.TipoVisitaDTO;
import dto.UserDTO;
import dto.VisitaDTO;
import utility.Credenziali;

public interface UserRepository {
	public int getTipoUtente (String username);
	public boolean checkIfCanLinkVolontario(String volontario, String tipoVisita);
	//Precondizione: callerType = CostantiStruttura.CONFIGURATORE
	public boolean associaVolontarioEsistenteATipoVisitaEsistente(String volontario, String tipoVisita);
	public boolean associaVolontariATipoVisita(List<Credenziali> volontari, String tipoVisita);
	//Precondizione: callerTyper = CostantiStruttura.VOLONTARIO
    public boolean inserisciDisponibilita(String data, String username);
	//Precondizione: callerType = CostantiStruttura.VOLONTARIO
    public List<DataDisponibilitaDTO> getDatePerDisponibilita(String username);
    public List<TipoVisitaDTO> getElencoTipiVisiteVolontario (String username);

	/*
	 * Precondizione: 0 < tipo_user < 4
	 */
	public List<UserDTO> getListaUser(int tipo_user);
	/*
	 * Precondizione: callerType = CostantiStruttura.VOLONTARIO && tipiVisitaAssociati esistenti
	 * Postcondizione: remove Volontario && remove associazioni
	 */
	public boolean rimuoviVolontario (String volontario);
	//Precondizione: isPrimoAvvio
	public Credenziali getCredenzialiConfIniziale();
	public boolean checkCredenzialiCorrette (Credenziali c);
	public boolean checkPrimoAccesso (String username);
	public List<VisitaDTO> getElencoVisiteProposteConfermateCancellateFruitoreGiornoDato (String date);
	public PrenotazioneDTO getPrenotazione (String codicePrenotazione);
	public String linkedUserToPrenotazione (String codicePrenotazione);
	/*
	 * Precondizione: callerType = CostantiStruttura.FRUITORE
	 * Postcondizione: new Prenotazione
	 */
	public String inserisciPrenotazione (String username, PrenotazioneDTO prenotazione);
	public boolean modificaCredenziali (String username, Credenziali c);
	public boolean primoAccessoEseguito (String user);
	
	/*
	 * Precondizione: callerType = CostantiStruttura.VOLONARIO
	 * Postcondizione: remove Prenotazione
	 */
	public boolean rimuoviPrenotazione(String username, String codicePrenotazione);
	//Precondizione: callerType = CostantiStruttura.VOLONTARIO
	public List<VisitaDTO> visiteConfermateVolontario (String username);
	//Precondizione: callerType = CostantiStruttura.FRUITORE
	public List<PrenotazioneDTO> getElencoPrenotazioniFruitore (String username);
	public List<VisitaDTO> getElencoVisiteProposteConfermateCancellateFruitore();
	//Precondizione: callerType = CostantiStruttura.FRUITORE
	public List<VisitaDTO> getElencoVisiteProposteConfermateCancellatePrenotateDalFruitore (String username);
	public boolean checkIfUserExists (String username);
	//Precondizione: callerTyper = CostantiStruttura.CONFIGURATORE
		public boolean impostaCredenzialiNuovoVolontario (Credenziali credenziali, List<String> tipi_visiteVal, boolean tipiVisitaNecessario);
	public boolean impostaCredenzialiNuovoFruitore(Credenziali credenziali);
	public boolean impostaCredenzialiNuovoConfiguratore(Credenziali credenziali);


}
