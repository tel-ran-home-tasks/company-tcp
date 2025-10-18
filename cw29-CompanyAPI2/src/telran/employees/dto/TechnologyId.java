package telran.employees.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TechnologyId implements Serializable {
 int id;
 String technology;
 
 
 public TechnologyId(int id, String technology) {
	super();
	this.id = id;
	this.technology = technology;
 }


 public int getId() {
	return id;
 }


 public void setId(int id) {
	this.id = id;
 }


 public String getTechnology() {
	return technology;
 }


 public void setTechnology(String technology) {
	this.technology = technology;
 }
 
 
}
