package com.marciorr.concretesolutionsproject.model;

public class JSONData_Model {

	/** Sever Json Parser Getter and Setter **/
	private String nomeRepo, descricaoRepo, nomeUsuario, fotoUsuario;
	private int forksRepo, starsRepo;

	public JSONData_Model(String nomeRepo, String descricaoRepo, String nomeUsuario, String fotoUsuario, int forksRepo,  int starsRepo) {
		this.nomeRepo = nomeRepo;
		this.descricaoRepo = descricaoRepo;
		this.nomeUsuario = nomeUsuario;
		this.forksRepo = forksRepo;
		this.fotoUsuario = fotoUsuario;
		this.starsRepo = starsRepo;
	}

	public String getNomeRepo() {
		return nomeRepo;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public String getDescricaoRepo() {
		return descricaoRepo;
	}

	public int getForksRepo() { return forksRepo; }

	public String getFotoUsuario() { return fotoUsuario; }

	public int getStarsRepo() { return starsRepo; }

}
