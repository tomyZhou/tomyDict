package cn.com.tomy.utils;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import cn.com.tomy.domain.Dict;

public class SearchWords {

	private final static String URL = "http://dict-co.iciba.com/api/dictionary.php?w=";

	public static Dict tansWord(String word) {
		System.out.println("---------------------------" + URL + word);
		// InputStream in=DataAcess.getStreamByUrl(URL+word);
		Dict dict = null;
		String str = DataAcess.getStreamByUrl(URL + word);
		if (str != null) {
			StringReader read = new StringReader(str);
			// 创建输入源 使用 InputSource 对象来确定读取XML
			InputSource mInputSource = new InputSource(read);
			try {
				SAXParserFactory msSaxParserFactory = SAXParserFactory
						.newInstance();
				SAXParser msSaxParser = msSaxParserFactory.newSAXParser();
				XMLReader msXmlReader = msSaxParser.getXMLReader();
				SaxParseXml handler = new SaxParseXml();
				msXmlReader.setContentHandler(handler);
				msXmlReader.parse(mInputSource);
				dict = handler.getDict();
				return dict;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
