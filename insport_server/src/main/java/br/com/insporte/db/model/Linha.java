package br.com.insporte.db.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "linha")
@SequenceGenerator(name = "sq_linha", sequenceName = "sq_linha", initialValue = 1, allocationSize = 1)
public class Linha {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_linha")
	private Long id;
	
	private String nome;
	
	private String codigo;
	
	@OneToMany(mappedBy = "linha")
	private List<Envio> envios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Envio> getEnvios() {
		return envios;
	}

	public void setEnvios(List<Envio> envios) {
		this.envios = envios;
	}
	
}
