package com.codesnipets.controller;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.codesnipets.bean.CodeSnippets;

/**
 * 
 * @author memon
 * Class for Showing, deleting, gettingJSON, Filtering and searching the created Code Snippet 
 *
 */

@Path("ShowSnippet")
public class ShowSnippetController{
	
	/**
	 * return JSON
	 */	
	@GET
	@Path("/getJson")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, CodeSnippets> getList() {
		return SnippetController.snippetwithID;
	}
	
	/**
	 * return JSON with object ID
	 */
	@GET
	@Path("/getJson/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, CodeSnippets> getSnippetById(@PathParam("id") String id) {
		
		Map<String, CodeSnippets> snippetsbyId = new ConcurrentHashMap<String, CodeSnippets>();
		
		for (Entry<String, CodeSnippets> key : SnippetController.snippetwithID.entrySet()) {
			String Id = key.getKey();
			if(Id.equals(id)) {
				snippetsbyId.put(id, SnippetController.snippetwithID.get(id));
				return snippetsbyId;
			}
		}
		return null;
	}
	
	/**
	 * return HTML page for showing the list of snippets
	 */	
	@GET
	@Path("/getJson/showList")
	public Response showtList(@Context ServletContext context) {
		
	    UriBuilder uriBuilder = UriBuilder.fromUri(URI.create(context.getContextPath()));
	    uriBuilder.queryParam("lists", 20);
	    uriBuilder.path("/snippetlist.html");
	    URI uri = uriBuilder.build();
	    return Response.seeOther(uri).build();
	}
	
	/**
	 * Deleting the snippet by its secret
	 */
	@DELETE
	@Path("/getJson/delete/{secret}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, CodeSnippets> deleteSnippet(@PathParam("secret") String secret) {
		
		for (Entry<String, CodeSnippets> key : SnippetController.snippetwithID.entrySet()) {
			String Id = key.getKey();
			String _secret = key.getValue().getSecret();
			if(_secret.equals(secret)) {
				SnippetController.snippetwithID.remove(Id);
			}
		}
	    return SnippetController.snippetwithID;
	}
	
	/**
	 * Searching for a keyword
	 */
	@Path("/getJson/search/{keyword}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, CodeSnippets>searchSnippet(@PathParam("keyword") String keyword) {
		Map<String, CodeSnippets> snippetsbyKeyword = new ConcurrentHashMap<String, CodeSnippets>();
		
		for (Entry<String, CodeSnippets> key : SnippetController.snippetwithID.entrySet()) {
			String Id = key.getKey();
			String name = key.getValue().getName().toLowerCase();
			String snippet = key.getValue().getSnippet().toLowerCase();
			String description = key.getValue().getDescription().toLowerCase();
			String language = key.getValue().getLanguage().toLowerCase();
			String privacy = key.getValue().getPrivacy();
			
			if( name.contains(keyword.toLowerCase()) 
					|| snippet.contains(keyword.toLowerCase()) 
					|| description.contains(keyword.toLowerCase())
					|| language.contains(keyword.toLowerCase())){
				if(privacy.equals("null")) {
					snippetsbyKeyword.put(Id, SnippetController.snippetwithID.get(Id));					
				}
			}
		}
		return snippetsbyKeyword;
	}
	
	/**
	 * Filtering snippet by a date or programming Language
	 * YYYY-MM-DD format only at the moment, but can be converted to any Date Format.
	 */
	@Path("/getJson/filter/{filter}") //YYYY-MM-DD format only at the moment, but can be converted to any Date Format.
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, CodeSnippets> FilterSnippetByDate(@PathParam("filter") String filter) {
		Map<String, CodeSnippets> snippetsbyFilter = new ConcurrentHashMap<String, CodeSnippets>();
		for (Entry<String, CodeSnippets> key : SnippetController.snippetwithID.entrySet()) {
			String Id = key.getKey();
			String timestamp = key.getValue().getCreatedTime().toLowerCase();
			String language = key.getValue().getLanguage().toLowerCase();
			String privacy = key.getValue().getPrivacy();
			if(timestamp.contains(filter.toLowerCase()) && privacy.equals("null")) {
				System.out.println("In timestamp filter");
				snippetsbyFilter.put(Id, SnippetController.snippetwithID.get(Id));					
				}
			if(language.equals(filter.toLowerCase()) && privacy.equals("null")) {
				System.out.println("In language filter");
				snippetsbyFilter.put(Id, SnippetController.snippetwithID.get(Id));					
			}
		}
		return snippetsbyFilter;
	}
	
	/**
	 * paginating list of snippets
	 */
	@Path("getJson/paginate/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, CodeSnippets> SnippetPagination(@PathParam("pageSize") int pageSize) {
		int count = 0;
		Map<String, CodeSnippets> paginatedSnippets = new ConcurrentHashMap<String, CodeSnippets>();
		if(pageSize > SnippetController.snippetwithID.size()) {
			return SnippetController.snippetwithID;
		}
		else {
			for (Entry<String, CodeSnippets> key : SnippetController.snippetwithID.entrySet()) {
				count++;
				String Id = key.getKey();
				String privacy = key.getValue().getPrivacy();
				if(count <= pageSize && privacy.equals("null")) {
					paginatedSnippets.put(Id, SnippetController.snippetwithID.get(Id));	
				}
			}	
		}
		return paginatedSnippets;
	}
}
