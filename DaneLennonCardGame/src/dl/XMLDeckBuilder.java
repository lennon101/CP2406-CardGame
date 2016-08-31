
package dl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.File;

public class XMLDeckBuilder implements DeckBuilder {

    private String _filePath;
    private Deck _deck;
    private boolean _hasBeenRead = false;

    public XMLDeckBuilder(String filePath) {
        _filePath = filePath;
        _deck = new BasicDeck();
    }

    @Override
    public Deck deck() {
        if (!_hasBeenRead) {
            readXMLFile();
            _hasBeenRead = true;
        }
        return _deck;
    }

    private void stripEmptyLines(Document doc) {
        try {
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPathExpression xpathExp = xpathFactory.newXPath().compile("//text()[normalize-space(.) = '']");
            NodeList emptyTextNodes = (NodeList) xpathExp.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < emptyTextNodes.getLength(); i++) {
                Node emptyTextNode = emptyTextNodes.item(i);
                emptyTextNode.getParentNode().removeChild(emptyTextNode);
            }
        } catch (XPathExpressionException e) {
            System.out.println("XPath Expression Issue");
        }
    }

    private void readXMLFile() {
        try {
            File inputFile = new File(_filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            stripEmptyLines(doc);

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList cardList = (NodeList) xPath.evaluate("/plist/dict[key='cards']/array/dict", doc.getDocumentElement(), XPathConstants.NODESET);

            for (int index = 0; index < cardList.getLength(); index++) {
                try {
                    NodeList list = cardList.item(index).getChildNodes();

                    if (foundCardOfType("play",list)){
                        _deck.add(extractPlayCard(list));
                    } else if (foundCardOfType("trump",list)){
                        //_deck.add(extractCard(list));

                    } else if (foundCardOfType("rule",list)){
                        //_deck.add(extractCard(list));
                    }
                } catch (CardNotFoundException ex) {
                    System.out.println("Card not found for specific dict key");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class CardNotFoundException extends Exception {

    }

    private boolean foundCardOfType(String cardType, NodeList list) throws CardNotFoundException {
        for (int i = 1; i < list.getLength(); i++) {
            Node xmlNode = list.item(i);

            if (xmlNode.getPreviousSibling().getTextContent().equals("card_type") && xmlNode.getTextContent().equals(cardType)) {
                System.out.println("found " + cardType + " card");
                return true;
            }
        }
        return false;
    }

    private PlayCard extractPlayCard(NodeList list) {
        //function is passed a list that contains all the card data

        String cardType = "";
        String title = "";
        String chemistry="";
        String classification="";
        String crystalSystem="";
        String[] occurrence = new String[3];
        String hardness="";
        String specificGravity="";
        String cleavage="";
        String crystalAbundance="";
        String economicValue="";

        for (int i = 1; i < list.getLength(); i++) {
            Node xmlNode = list.item(i);

            if (xmlNode.getPreviousSibling().getTextContent().equals("card_type") && xmlNode.getNodeName().equals("string")) {
                cardType = xmlNode.getTextContent();
            } else if (xmlNode.getPreviousSibling().getTextContent().equals("title") && xmlNode.getNodeName().equals("string")) {
                title = xmlNode.getTextContent();
            } else if (xmlNode.getPreviousSibling().getTextContent().equals("chemistry") && xmlNode.getNodeName().equals("string")) {
                chemistry = xmlNode.getTextContent();
            }else if (xmlNode.getPreviousSibling().getTextContent().equals("classification") && xmlNode.getNodeName().equals("string")) {
                classification = xmlNode.getTextContent();
            }else if (xmlNode.getPreviousSibling().getTextContent().equals("crystal_system") && xmlNode.getNodeName().equals("string")) {
                crystalSystem = xmlNode.getTextContent();
            }else if (xmlNode.getPreviousSibling().getTextContent().equals("occurrence") && xmlNode.getNodeName().equals("array")) {
                NodeList occurrenceList = xmlNode.getChildNodes();

                for (int j = 0; j<occurrenceList.getLength();++j){
                    Node occNode = occurrenceList.item(j);
                    if (occNode.getNodeName().equals("string")){
                        occurrence[j] = occNode.getTextContent();
                    }
                }
            }else if (xmlNode.getPreviousSibling().getTextContent().equals("hardness") && xmlNode.getNodeName().equals("string")) {
                hardness = xmlNode.getTextContent();
            }
            else if (xmlNode.getPreviousSibling().getTextContent().equals("specific_gravity") && xmlNode.getNodeName().equals("string")) {
                specificGravity = xmlNode.getTextContent();
            }else if (xmlNode.getPreviousSibling().getTextContent().equals("cleavage") && xmlNode.getNodeName().equals("string")) {
                cleavage = xmlNode.getTextContent();
            }else if (xmlNode.getPreviousSibling().getTextContent().equals("crustal_abundance") && xmlNode.getNodeName().equals("string")) {
                crystalAbundance = xmlNode.getTextContent();
            }else if (xmlNode.getPreviousSibling().getTextContent().equals("economic_value") && xmlNode.getNodeName().equals("string")) {
                economicValue = xmlNode.getTextContent();
            }
        }

        return new PlayCard(cardType,title,chemistry,classification,crystalSystem,occurrence,hardness,specificGravity,cleavage,crystalAbundance,economicValue);

    }
}
