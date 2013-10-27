package br.com.insporte.db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
@SequenceGenerator(name = "sq_like", sequenceName = "sq_like", initialValue = 1, allocationSize = 1)
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_like")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_linha")
	private Linha linha;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	private Boolean status;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

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


}
