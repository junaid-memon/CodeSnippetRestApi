package com.codesnipets.bean;

/**
 * 
 * @author memon
 * POJO Class
 */
public class CodeSnippets {
	private String Name;
	private String Description;
	private String Snippet;
	private String language;
	private String time;
	private String secret;
	private String privacy;
	
	public String getName(){
		return Name;
	}
	public void setName(String Name) {
		this.Name = Name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String Description) {
		this.Description =  Description;
	}
	public String getSnippet(){
		return Snippet;
	}
	public void setSnippet(String Snippet) {
		this.Snippet = Snippet;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setCreatedTime(String time) {
		this.time = time;
	}
	public String getCreatedTime() {
		return time.replace("T", " ");
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getSecret() {
		return secret.substring(3);
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public String getPrivacy() {
		return privacy;
	}
}
