package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getCombinazione(4, 200, new Nerc(8, null)));

	}

}
