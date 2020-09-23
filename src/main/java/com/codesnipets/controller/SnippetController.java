package com.codesnipets.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.time.*;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.codesnipets.bean.CodeSnippets;
import com.codesnipets.service.SecretKeyService;


/**
 * 
 * @author memon
 * Form Data handling Class
 *
 */

@Path("SubmitSnippet")
public class SnippetController {
	
	public static Map<String, CodeSnippets> snippetwithID 
				= new ConcurrentHashMap<String, CodeSnippets>();
	private CodeSnippets _snippet = new CodeSnippets();
	public static int newId = 0;
	
	/**
	 * In case of URL parameters such as "/query?name=snippet", 
	 * we will user @QueryParam instead of @FormParam
	 */
	
	@Path("add")
	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
	public Response saveSnippet(@FormParam("name") String name,  
								@FormParam("description") String description,
								@FormParam("snippet") String snippet,
								@FormParam("privacy") String privacy,
								@FormParam("language") String language,
								@Context ServletContext context) 
					throws NoSuchAlgorithmException {
		
		if(name.isEmpty() || description.isEmpty() || snippet.isEmpty()) {
			return Response.ok("Snippet not added due to empty fields! "
					+ "<a href=http:/CodeSnippetsRestApi> "
					+ "Click here to add code snippet again</a>").build();	
		}
		else {
			newId++;
	        _snippet.setName(name);
	        _snippet.setDescription(description);
	        _snippet.setSnippet(snippet);
	        _snippet.setCreatedTime(LocalDateTime.now().toString());
	        _snippet.setSecret(String.valueOf(SecretKeyService.generate()
	        		.getEncoded()));
	        _snippet.setPrivacy(String.valueOf((String) privacy));
	        _snippet.setLanguage(language);
	        snippetwithID.put(Integer.toString(newId), _snippet);
	        
	        UriBuilder uriBuilder = UriBuilder.fromUri(URI.create(context.getContextPath()));
	        uriBuilder.queryParam("Id", newId);
		    uriBuilder.path("/JsonRepresentedSnippet.html");
		    URI uri = uriBuilder.build();
	        return Response.seeOther(uri).build();
		}
		
	}
}
