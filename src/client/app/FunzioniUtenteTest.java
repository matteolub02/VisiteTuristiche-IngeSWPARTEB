package client.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import client.controller_utente.*;
import utility.Credenziali;

@TestMethodOrder(MethodOrderer.MethodName.class)
class FunzioniUtenteTest {
	
	private App app = new AppUI();
	
	private int getIndexAzione(String azione) {
		return app.getGu().getAzioniDisponibiliConNomi().indexOf(azione);
	}
	
	@AfterEach
	void setUp() {
		app.getGl().stopConnection();
		app = new AppUI(); 
	}
	
	@BeforeEach
	void setUpLogin() {
		app.getGl().inviaCredenziali(new Credenziali("conf1", "admin"));
	}
	
	@Test
	void test1_Login() {
		assertTrue(app.getGu() instanceof HandlerConfiguratore);
		assertTrue(app.scegliAzione("6")); //true azione eseguita o ignorata
		assertFalse(app.scegliAzione("esc")); //false errore o esc
		assertTrue(app.scegliAzione("25"));
	}
	
	@Test
	void test2_ConfiguratoreView() {
		int i = getIndexAzione("Visualizza visite proposte, complete, confermate, cancellate e effettuate");
		app.scegliAzione(String.valueOf(i));
		i = getIndexAzione("Visualizza elenco tipi visite per luogo");
		app.scegliAzione(String.valueOf(i));
		i = getIndexAzione("Visualizza elenco luoghi visitabili");
		app.scegliAzione(String.valueOf(i));
		i = getIndexAzione("Visualizza lista volontari");
		app.scegliAzione(String.valueOf(i));
	}
	
	@Test
	void test3_SceltaAzioneNonDisponibile() {
		int i = getIndexAzione("Effettua iscrizione ad una visita proposta");
		assertEquals(i, -1);
	}
	
	@Test
	void test4_AggiuntaCredenzialiVolontario() {
		int i = getIndexAzione("Aggiungi volontari ad un tipo di visita esistente");
		app.scegliAzione(String.valueOf(i)); //manualmente da inserire dati
		assertTrue(app.getGu().checkIfUserExists("volontariotest"));
	}
	
	@Test
	void test5_RimozioneCredenzialiVolontario() {
		int i = getIndexAzione("Rimuovi volontario");
		app.scegliAzione(String.valueOf(i)); //manualmente da inserire dati
		assertFalse(app.getGu().checkIfUserExists("volontariotest"));
	}
}
