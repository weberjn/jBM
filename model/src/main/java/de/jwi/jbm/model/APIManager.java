package de.jwi.jbm.model;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

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

	public APIManager(BookmarkManager bm) {
		super();
		this.bm = bm;
		
		outputFactory = XMLOutputFactory.newInstance();
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	

	public void addBookmark(User user, Writer writer, String url, String description, String tags) throws XMLStreamException, IOException
	{
		Bookmark b = new Bookmark();
		b.setAddress(url);
		
		StringBuffer keywords = new StringBuffer();

		bm.fetchBookmarkFromURL(b, keywords, url, null);
		
		String[] ts = tags.toString().split(",");
		List<String> tagsFromApi = Arrays.asList(ts);
		
		String[] ts1 = keywords.toString().split(",");
		if (ts1 != null && ts1.length > 0)
		{
			Set<String> tagsFromURL = new HashSet<String>(Arrays.asList(ts1));
			tagsFromURL.addAll(tagsFromApi);
		}
		
		try
		{
			bm.addBookmark(user, b);
			
			for (String s : tagsFromApi)
			{
				bm.addTag(user, b, s);
			}
			
			writer.write("<result code=\"done\" />");
			
		} catch (MalformedURLException e)
		{
			writer.write("<result code=\"something went wrong\" />");
		}
	}
	
	
	public void fetchAllBookmarks(User user, Writer writer) throws XMLStreamException
	{
		List<Bookmark> bookmarks = bm.getBookmarks(user);
		
//		<posts tag="" user="user">
//		<post href="http://www.weather.com/" description="weather.com"
//		hash="6cfedbe75f413c56b6ce79e6fa102aba" tag="weather reference"
//		time="2005-11-29T20:30:47Z" />

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
