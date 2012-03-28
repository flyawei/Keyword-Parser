import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;


public class Keywords {
    final String url;
    TagNode rootNode;

    public Keywords(String htmlPage) throws MalformedURLException, IOException {
    	
        HtmlCleaner cleaner = new HtmlCleaner();
        url = htmlPage;
        rootNode = cleaner.clean(new URL(url));

    }

	public static BufferedReader read(String url) throws Exception {
		URL uRL = new URL(url);
		return new BufferedReader(
				new InputStreamReader(
						uRL.openStream()));
	}

    List<String> getSourceList() throws MalformedURLException
    {
        List<String> divList = new ArrayList<String>();

        // keyword meta
        // bold, ital, diff size
        // header, title
        //TagNode elements[] = rootNode.getAllElements(false);
        divList.add((new URL(url)).getHost());
        
        String[] tags = {"title", "h1", "h2", "h3", "h4", "h5", "strong"};
        // tags
        for (int k = 0; k < tags.length; k++) {
	        TagNode tagElements[] = rootNode.getElementsByName(tags[k], true);
	        for (int i = 0; tagElements != null && i < tagElements.length; i++)
	        {
	        	String temp = tagElements[i].getText().toString().trim().toLowerCase();
	        	divList.add(temp);
	        }
        }
        // keywords
        TagNode keyElements[] = rootNode.getElementsByAttValue("name", "keywords", true, false);
        for (int i = 0; keyElements != null && i < keyElements.length; i++)
        {
        	String temp = keyElements[i].getAttributeByName("content").toString().trim().toLowerCase();
        	String[] keys = temp.split(",");
        	for (int j = 0; j < keys.length; j++) {
        		divList.add(keys[j].trim());
        	}
        }
        
        
        return divList;
    }

	public static void main(String[] args) throws Exception {
		BufferedReader reader = read(args[0]);
		//String source = reader.readLine()
		String line = reader.readLine();

		while (line != null) {
			System.out.println(line);
			line = reader.readLine(); 
		}
/*		Keywords keywordParser = new Keywords(args[0]);		
        List<String> elements = keywordParser.getSourceList();

        for (String s : elements) {
        	System.out.println(s);
        	//System.out.println("_________");
        }*/
	}

}
