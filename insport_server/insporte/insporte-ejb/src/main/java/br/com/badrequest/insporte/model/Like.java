package br.com.badrequest.insporte.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"busline_id", "user_id"}))
@Data
public class Like implements Serializable {

	@Id
	@SequenceGenerator(name="sq_like",sequenceName="sq_like", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sq_like")
	private Long id;

	@ManyToOne
	private BusLine busLine;

	@ManyToOne
	private User user;

	private Boolean status;

}
