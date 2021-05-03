/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Evento;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	txtResult.clear();
    	
    	int totAnni;
    	int totOre;
    	try {
    		totAnni = Integer.parseInt(this.txtYears.getText());
    		totOre = Integer.parseInt(this.txtHours.getText());
    		
    	} catch(NumberFormatException nfe) {
    		this.txtResult.setText("Valori numerici non inseriti correttamente!");
    		this.txtHours.clear();
    		this.txtYears.clear();
    		return;
    	}
    	
    	if(totAnni>14) {
    		this.txtResult.setText("Il numero di anni inserito supera il valore massimo considerabile per il db");
    		this.txtYears.clear();
    		return;
    	}
    	
    	Nerc nerc = this.cmbNerc.getValue();
    	if(nerc==null) {
    		this.txtResult.setText("Nerc non selezionata!");
    		this.txtHours.clear();
    		this.txtYears.clear();
    		return;
    	}
    	
    	List<Evento> eventi = this.model.getCombinazione(totAnni, totOre, nerc);
    	
    	int totClientiColpiti = 0;
    	int totOreDisagio = 0;
    	for(Evento e : eventi) {
    		totClientiColpiti += e.getClientiColpiti();
    		totOreDisagio += e.getDurataDisservizio();
    	}
    	
    	this.txtResult.appendText("Tot persone colpite: " + totClientiColpiti + "\n");
    	this.txtResult.appendText("Tot ore di disagio: " + totOreDisagio + "\n");
    	for(Evento e : eventi) 
    		this.txtResult.appendText(e.toString()+"\n");
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	for(Nerc n : this.model.getNercList()) 
    		this.cmbNerc.getItems().add(n);
    }
}
