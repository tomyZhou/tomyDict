package cn.com.tomy.utils;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import cn.com.tomy.domain.Dict;
import cn.com.tomy.domain.Sent;

public class SaxParseXml extends DefaultHandler {
	private Dict dict;
	private List<Sent> sents;
	private Sent sent = null;
	private StringBuffer acceptation;
	final int CONSTANT_DIC = 1;
	final int CONSTANT_KEY = 2;
	final int CONSTANT_PS = 3;
	final int CONSTANT_PRON = 4;
	final int CONSTANT_POS = 5;
	final int CONSTANT_ACCEPTATION = 6;
	final int CONSTANT_SENT = 7;
	final int CONSTANT_ORIG = 8;
	final int CONSTANT_TRANS = 9;
	int currentState = 0;
	
	@Override
	public void startDocument() throws SAXException {
		dict = new Dict();
		sents = new ArrayList<Sent>();
		acceptation = new StringBuffer();
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ((localName.equals("dict"))) {
			currentState = CONSTANT_DIC;
			return;
		}
		if ((localName.equals("key"))) {
			currentState = CONSTANT_KEY;
			return;
		}
		if ((localName.equals("ps"))) {
			currentState = CONSTANT_PS;
			return;
		}
		if ((localName.equals("pron"))) {
			currentState = CONSTANT_PRON;
			return;
		}
		if ((localName.equals("pos"))) {
			currentState = CONSTANT_POS;
			return;
		}
		if ((localName.equals("acceptation"))) {
			currentState = CONSTANT_ACCEPTATION;
			return;
		}
		if ((localName.equals("sent"))) {
			sent = new Sent();
			return;
		}
		if ((localName.equals("orig"))) {
			currentState = CONSTANT_ORIG;
			return;
		}
		if ((localName.equals("trans"))) {
			currentState = CONSTANT_TRANS;
			return;
		}
		currentState = 0;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(localName.equals("sent")){
			sents.add(sent);
		}else if(localName.equals("dict")){
			dict.setSents(sents);
		}
		currentState = 0;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String theString = new String(ch, start, length);
		System.out.println("currentState="+currentState);
		System.out.println(ch);
		System.out.println(start);
		System.out.println(length);
		switch (currentState) {
		case CONSTANT_DIC:
			break;
		case CONSTANT_KEY:
			dict.setKey(theString);
			currentState = 0;
			break;
		case CONSTANT_PS:
			dict.setPs(theString);
			currentState = 0;
			break;
		case CONSTANT_PRON:
			dict.setPron(theString);
			currentState = 0;
			break;
		case CONSTANT_POS:
			dict.setPos(theString);
			currentState = 0;
			break;
		case CONSTANT_ACCEPTATION:
			acceptation.append(theString);
			dict.setAcceptation(acceptation.toString());
			currentState = 0;
			break;
		case CONSTANT_ORIG:
			sent.setOrig(theString);
			currentState = 0;
			break;
		case CONSTANT_TRANS:
			sent.setTrans(theString);
			currentState = 0;
			break;
		default:
			return;
		}
	}

	public Dict getDict(){
		return dict;
	}
}
