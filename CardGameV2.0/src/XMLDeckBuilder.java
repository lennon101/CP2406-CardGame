

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.File;
import java.lang.reflect.Array;

public class XMLDeckBuilder implements DeckBuilder {

    private String _filePath;
    private Deck _deck;
    private boolean _hasBeenRead = false;

    private String[] cleavageList = {"none","poor/none","1 poor","2 poor","1 good","1 good, 1 poor",
            "2 good","3 good","1 perfect","1 perfect, 1 good","1 perfect, 2 good",
            "2 perfect, 1 good","3 perfect","4 perfect","6 perfect"};
    private String[] _crustalAbundanceList = {"ultratrace", "trace", "low", "moderate", "high", "very high"};
    private String[] _economicValueList = {"trivial","low","moderate","high","very high","I'm rich!"};
    private String[] _trumpTypeList = {"none","any","Hardness","Economic value","Specific gravity","Crustal abundance","Cleavage"};

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
                        _deck.add(extractNormalCard(list));
                    } else if (foundCardOfType("trump",list)){
                        _deck.add(extractTrumpCard(list));
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
        //this is a void class to stop the thrown exception being intrusive
    }

    private boolean foundCardOfType(String cardType, NodeList list) throws CardNotFoundException {
        for (int i = 1; i < list.getLength(); i++) {
            Node xmlNode = list.item(i);

            if (xmlNode.getPreviousSibling().getTextContent().equals("card_type") && xmlNode.getTextContent().equals(cardType)) {
                return true;
            }
        }
        return false;
    }

    private NormalCard extractNormalCard(NodeList list) {
        //function is passed a list that contains all the cardPanel data
        String title = "";
        String chemistry="";
        String classification="";
        String crystalSystem="";
        String[] occurrence = new String[3];
        Range hardness= new Range(0);
        Range specificGravity = new Range(0);
        CleavageValue cleavage = CleavageValue.NONE;
        CrustalAbundanceValue crustalAbundance = CrustalAbundanceValue.NONE;
        EconomicValue economicValue = EconomicValue.NONE;

        for (int i = 1; i < list.getLength(); i++) {
            Node xmlNode = list.item(i);

            if (xmlNode.getPreviousSibling().getTextContent().equals("title") && xmlNode.getNodeName().equals("string")) {
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
                String hardnessString = xmlNode.getTextContent();
                hardness = splitThis(hardnessString);
            }
            else if (xmlNode.getPreviousSibling().getTextContent().equals("specific_gravity") && xmlNode.getNodeName().equals("string")) {
                String gravityString = xmlNode.getTextContent();
                specificGravity = splitThis(gravityString);

            }else if (xmlNode.getPreviousSibling().getTextContent().equals("cleavage") && xmlNode.getNodeName().equals("string")) {
                String cleavageString = xmlNode.getTextContent();
                cleavage = getCleavageType(cleavageString);
            }else if (xmlNode.getPreviousSibling().getTextContent().equals("crustal_abundance") && xmlNode.getNodeName().equals("string")) {
                String crustalAbundanceString = xmlNode.getTextContent();
                crustalAbundance = getCrustalAbundanceType(crustalAbundanceString);
            }else if (xmlNode.getPreviousSibling().getTextContent().equals("economic_value") && xmlNode.getNodeName().equals("string")) {
                String economicValueString = xmlNode.getTextContent();
                economicValue = getEconomicValueType(economicValueString);
            }else{
                //System.out.println("no PlayCard attributes found in xml node");
            }
        }
        return new NormalCard(TrumpType.NONE,title,hardness,specificGravity,cleavage,crustalAbundance,economicValue);
    }

    private CleavageValue getCleavageType(String cleavageString) {
        for (int i = 0; i< this.cleavageList.length; ++i){
            if (this.cleavageList[i].equals(cleavageString)){
                return CleavageValue.values()[i];
            }
        }
        return null;
    }
    private CrustalAbundanceValue getCrustalAbundanceType(String crustalAbundanceString) {
        for (int i = 0; i< this._crustalAbundanceList.length; ++i){
            if (this._crustalAbundanceList[i].equals(crustalAbundanceString)){
                return CrustalAbundanceValue.values()[i];
            }
        }
        return null;
    }

    private EconomicValue getEconomicValueType(String economicValueString) {
        for (int i = 0; i< this._economicValueList.length; ++i){
            if (this._economicValueList[i].equals(economicValueString)){
                return EconomicValue.values()[i];
            }
        }
        return null;
    }

    private Range splitThis(String toSplit) {
        boolean splitSuccess = false;
        Double[] range = {0.0,0.0};
        if (!splitSuccess){
            try{
                range[0] = Double.parseDouble(toSplit.split(" - ")[0]);
                range[1] = Double.parseDouble(toSplit.split(" - ")[1]);
                splitSuccess = true;
            }catch (Throwable t){}
        }
        if (!splitSuccess){
            try{
                range[0] = Double.parseDouble(toSplit.split("-")[0]);
                range[1] = Double.parseDouble(toSplit.split("-")[1]);
                splitSuccess = true;
            }catch (Throwable t){}
        }
        if (!splitSuccess){
            try{
                range[0] = Double.parseDouble(toSplit.split(" ")[0]);
                range[1] = Double.parseDouble(toSplit.split(" ")[1]);
                splitSuccess = true;
            }catch (Throwable t){}
        }
        if (!splitSuccess){
            range[0] = Double.parseDouble(toSplit);
            range[1] = Double.parseDouble(toSplit);
        }

        return new Range(range[0],range[1]);
    }

    public TrumpCard extractTrumpCard(NodeList list){
        //function is passed a list that contains all the cardPanel data
        String title = "";
        TrumpType trumpType = TrumpType.NONE;

        for (int i = 1; i < list.getLength(); i++) {
            Node xmlNode = list.item(i);

            if (xmlNode.getPreviousSibling().getTextContent().equals("title") && xmlNode.getNodeName().equals("string")) {
                title = xmlNode.getTextContent();
            } else if (xmlNode.getPreviousSibling().getTextContent().equals("subtitle") && xmlNode.getNodeName().equals("string")) {
                String subTitle = xmlNode.getTextContent();
                trumpType = getTrumpType(subTitle);
            }
        }
        return new TrumpCard(trumpType,title);
    }

    private TrumpType getTrumpType(String subTitle) {
        for (int i = 0; i< this._trumpTypeList.length; ++i){
            if (this._trumpTypeList[i].equals(subTitle)){
                return TrumpType.values()[i];
            }
        }
        return TrumpType.ANY;
    }
}
