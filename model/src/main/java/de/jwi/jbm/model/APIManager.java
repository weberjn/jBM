package de.jwi.jbm.model;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.User;

public class APIManager
{
	private BookmarkManager bm;
	private DateFormat dateFormat;
	private XMLOutputFactory outputFactory;
	
	private static final Logger log = Logger.getLogger(APIManager.class.getName());


	public APIManager(BookmarkManager bm) {
		super();
		this.bm = bm;
		
		outputFactory = XMLOutputFactory.newInstance();
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	

	public void addBookmark(User user, Writer writer, String url, String description, String extended,
			String tags, String status, String replace) throws XMLStreamException, IOException
	{
		int iStatus = Integer.parseInt(status);
		boolean bReplace = "yes".equalsIgnoreCase(replace);
		
		Bookmark b = new Bookmark();
		b.setAddress(url);
		
		b.setTitle(description); // sic
		b.setDescription(extended); // sic
		
		b.setStatus(iStatus);
		

		String[] ts = tags.toString().split(",");
		
		try
		{
			if (bReplace)
			{
				b = bm.updateBookmark(user, b);
				if (b == null)
				{
					log.log(Level.SEVERE, "Bookmark for update not found in DB: " + url); 
					writer.write("<result code=\"something went wrong\" />");
				}
			}
			else
			{
				bm.addBookmark(user, b);
			}
			
			bm.updateTags(user, b, ts);
			
			writer.write("<result code=\"done\" />");
			
		} catch (MalformedURLException e)
		{
			writer.write("<result code=\"something went wrong\" />");
		}
	}
	
	public void deleteBookmark(User user, HttpServletResponse response, String address) throws XMLStreamException, IOException
	{
		URL url = new URL(address);
		
		Bookmark b = bm.findBookmark(user, url);
		if (b == null)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
		boolean done = bm.removeBookmark(user, b.getId());
		
		response.getWriter().write("<result code=\"done\" />");
	}
	
	public void fetchAllBookmarks(User user, Writer writer) throws XMLStreamException
	{
		List<Bookmark> bookmarks = bm.getBookmarks(user);
		
		XMLStreamWriter xmlWriter = outputFactory.createXMLStreamWriter(writer);

		
		xmlWriter.writeStartElement("posts");
		
		xmlWriter.writeAttribute("tag","");
		
		xmlWriter.writeAttribute("user", user.getUsername());
		
		for (Bookmark b : bookmarks)
		{
			xmlWriter.writeStartElement("post");
			xmlWriter.writeAttribute("href", b.getAddress());
			
			xmlWriter.writeAttribute("description", b.getTitle());
			
			xmlWriter.writeAttribute("hash", b.getHash());
			
			String description = b.getDescription() != null ? b.getDescription() : "";
			
			xmlWriter.writeAttribute("extended", description);

			
			StringBuffer sb = new StringBuffer();
			bm.tagsToCSV(b.getTags(), sb);
			xmlWriter.writeAttribute("tag", sb.toString());
			
			xmlWriter.writeAttribute("status", b.getStatus().toString());				
				
			xmlWriter.writeAttribute("time", dateFormat.format(b.getModified()));
			
			xmlWriter.writeEndElement();
			
		}
		xmlWriter.writeEndElement();
	}

}
