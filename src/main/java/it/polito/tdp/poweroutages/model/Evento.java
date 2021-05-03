package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Evento {
	
	private int id;
	private Nerc nerc;
	private int clientiColpiti;
	private LocalDateTime dataInizioEvento;
	private LocalDateTime dataFineEvento;
	
	public Evento(int id, Nerc nerc, int clientiColpiti, LocalDateTime dataInizioEvento, LocalDateTime dataFineEvento) {
		this.id = id;
		this.nerc = nerc;
		this.clientiColpiti = clientiColpiti;
		this.dataInizioEvento = dataInizioEvento;
		this.dataFineEvento = dataFineEvento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public int getClientiColpiti() {
		return clientiColpiti;
	}

	public void setClientiColpiti(int clientiColpiti) {
		this.clientiColpiti = clientiColpiti;
	}

	public LocalDateTime getDataInizioEvento() {
		return dataInizioEvento;
	}

	public void setDataInizioEvento(LocalDateTime dataInizioEvento) {
		this.dataInizioEvento = dataInizioEvento;
	}

	public LocalDateTime getDataFineEvento() {
		return dataFineEvento;
	}

	public void setDataFineEvento(LocalDateTime dataFineEvento) {
		this.dataFineEvento = dataFineEvento;
	}
	
	public int getAnnoInizio() {
		return this.dataInizioEvento.getYear();
	}
	
	public int getDurataDisservizio() {
		long minutes = ChronoUnit.MINUTES.between(dataInizioEvento, dataFineEvento);
		
		return (int)minutes/60;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
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
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ""+this.getAnnoInizio() + " " + dataInizioEvento.toString() + " " + dataFineEvento.toString() +
				" " + this.getDurataDisservizio()+ " " + clientiColpiti;
	}

}
