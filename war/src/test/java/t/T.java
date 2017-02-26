package t;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class T
{

	public static void main(String[] args) throws IOException
	{
		Document doc = Jsoup.connect("http://www.zeit.de/").get();
		String title = doc.title();
		System.out.println(title);
		
//		 <meta name="description" content="News und Foren zu Computer, IT, Wissenschaft, Medien und Politik. Preisvergleich von Hardware und Software sowie Downloads bei Heise Medien.">
//         <meta name="keywords" content="heise online, c't, iX, Technology Review, Newsticker, Telepolis, Security, Netze">
 
		
		Elements metaTags = doc.getElementsByTag("meta");


		for (Element metaTag : metaTags) {
			  String name = metaTag.attr("name");
			
		  String content = metaTag.attr("content");

		  if ("description".equals(name) || "keywords".equals(name))
		  
		  System.err.println(name + " " + content);
		
		}
		
	}

}
