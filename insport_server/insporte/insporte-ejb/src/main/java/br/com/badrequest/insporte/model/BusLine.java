package br.com.badrequest.insporte.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Table(name = "bus_line")
@Data
public class BusLine implements Serializable {

	@Id
	@SequenceGenerator(name="sq_bus_line",sequenceName="sq_bus_line", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sq_bus_line")
	private Long id;
	
	private String code;
	
	private String name;
	
}
