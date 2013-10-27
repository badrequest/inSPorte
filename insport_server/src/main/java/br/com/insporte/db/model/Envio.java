package br.com.insporte.db.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "envio")
@SequenceGenerator(name = "sq_envio", sequenceName = "sq_envio", initialValue = 1, allocationSize = 1)
public class Envio {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_envio")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_linha")
	private Linha linha;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@OneToOne
	@JoinColumn(name="id_comentario")
	private Comentario comentario;
	
	private Date data;
	
	private Double lat;
	
	private Double lon;
	
	@OneToMany(mappedBy = "envio")
	private List<Resposta> respostas;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
	
}
