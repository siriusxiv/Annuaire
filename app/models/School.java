package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class School extends Model{
	@Id
	public Integer id;
	
	public String name;
	@ManyToOne
	public Country country;
	
	public static Finder<Integer,School> find = new Finder<Integer,School>(Integer.class, School.class);
}