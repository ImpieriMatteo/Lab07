package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List<Evento> partenza;
	private List<Evento> risultatoMigliore;
	private int numeroClientiSoluzioneMigliore;
	private int oreDisservizio; 
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<Evento> getCombinazione(int totAnni, int totOre, Nerc nerc) {
		this.partenza = new ArrayList<>(podao.getEventoPerNerc(nerc));
		this.risultatoMigliore = new ArrayList<>();
		this.numeroClientiSoluzioneMigliore = 0;
		this.oreDisservizio = 0;
		
		List<Evento> parziale = new ArrayList<>();
		
		cercaCombinazione(parziale, totAnni, totOre, 0);
		
		return risultatoMigliore;
	}

	private void cercaCombinazione(List<Evento> parziale, int totAnni, int totOre, int livello) {
		
		if(livello==partenza.size()) {
			int totClientiColpiti = this.numeroTotClienti(parziale);
			if(totClientiColpiti>this.numeroClientiSoluzioneMigliore) {
				this.numeroClientiSoluzioneMigliore = totClientiColpiti;
				this.risultatoMigliore = new ArrayList<>(parziale);
				return;
			}
			return;
		}
		
		if(parziale.size()==partenza.size()) { 
			this.numeroClientiSoluzioneMigliore = this.numeroTotClienti(parziale);
			this.risultatoMigliore = new ArrayList<>(parziale);
			return;
		}
			
		if(this.oreDisservizio==totOre) {
			int totClientiColpiti = this.numeroTotClienti(parziale);
			if(totClientiColpiti>this.numeroClientiSoluzioneMigliore) {
				this.numeroClientiSoluzioneMigliore = totClientiColpiti;
				this.risultatoMigliore = new ArrayList<>(parziale);
				return;
			}
			return;
		}
		/*
		else {
			for(int i=0; i<partenza.size(); i++) {
				Evento e = partenza.get(i);
				if(parziale.size()==0) {
					parziale.add(e);
					this.oreDisservizio += e.getDurataDisservizio();
					cercaCombinazione(parziale, totAnni, totOre, livello++);
					if(parziale.size()==partenza.size())
						return;
					
					parziale.remove(e);
					this.oreDisservizio -= e.getDurataDisservizio();
				}
				else if((e.getAnnoInizio()-parziale.get(0).getAnnoInizio())<=totAnni) {
					if(!parziale.contains(e) && i>=parziale.size()) {
						if((this.oreDisservizio+e.getDurataDisservizio())>totOre) {
							int totClientiColpiti = this.numeroTotClienti(parziale);
							if(totClientiColpiti>this.numeroClientiSoluzioneMigliore) {
								this.numeroClientiSoluzioneMigliore = totClientiColpiti;
								this.risultatoMigliore = new ArrayList<>(parziale);
								return;
							}
						}
						else {
							parziale.add(e);
							this.oreDisservizio += e.getDurataDisservizio();
							cercaCombinazione(parziale, totAnni, totOre, livello++);
							if(parziale.size()==partenza.size())
								return;
							
							parziale.remove(e);
							this.oreDisservizio -= e.getDurataDisservizio();
							cercaCombinazione(parziale, totAnni, totOre, livello++);
							if(parziale.size()==partenza.size())
								return;
						}
					}
				}
			}
		}
		*/
		Evento e = partenza.get(livello);
		if((this.oreDisservizio+e.getDurataDisservizio())>totOre) {
			int totClientiColpiti = this.numeroTotClienti(parziale);
			if(totClientiColpiti>this.numeroClientiSoluzioneMigliore) {
				this.numeroClientiSoluzioneMigliore = totClientiColpiti;
				this.risultatoMigliore = new ArrayList<>(parziale);
			}
		}
		parziale.add(e);
		boolean flag = false;
		if((e.getAnnoInizio()-parziale.get(0).getAnnoInizio())<=totAnni) {
			if((this.oreDisservizio+e.getDurataDisservizio())<=totOre) {
				this.oreDisservizio += e.getDurataDisservizio();
				cercaCombinazione(parziale, totAnni, totOre, livello+1);
				if(parziale.size()==partenza.size())
					return;
				this.oreDisservizio -= e.getDurataDisservizio();
			}
			parziale.remove(e);
			flag = true;
			
			cercaCombinazione(parziale, totAnni, totOre, livello+1);
		}
		if(!flag)
			parziale.remove(e);
	}

	private int numeroTotClienti(List<Evento> parziale) {
		int totClienti = 0; 
		for(Evento e : parziale) {
			totClienti += e.getClientiColpiti();
		}
		return totClienti;
	}

}
