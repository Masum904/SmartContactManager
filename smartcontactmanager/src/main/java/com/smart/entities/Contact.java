package com.smart.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CONTACT")
public class Contact {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String name;
private String secondName;
private String work;
private String email;
private String phone;
private String image;
@Column(length = 5000)
private String discription;
@ManyToOne
private User user;


public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}


public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSecondName() {
	return secondName;
}
public void setSecondName(String secondName) {
	this.secondName = secondName;
}
public String getWork() {
	return work;
}
public void setWork(String work) {
	this.work = work;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public String getDiscription() {
	return discription;
}
public void setDiscription(String discription) {
	this.discription = discription;
}
public Contact() {
	super();
	// TODO Auto-generated constructor stub
}
public Contact(int id, String name, String secondName, String work, String email, String phone, String image,
		String discription) {
	super();
	this.id = id;
	this.name = name;
	this.secondName = secondName;
	this.work = work;
	this.email = email;
	this.phone = phone;
	this.image = image;
	this.discription = discription;
}


}
