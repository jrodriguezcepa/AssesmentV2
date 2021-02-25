/**
 * CEPA
 * Assesment
 */
package assesment.communication.util;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import assesment.communication.language.Text;

/**
 * @author jrodriguez
 */
public class CountryConstants {

    List countries = new LinkedList();
    Hashtable cc = new Hashtable();
    
    public CountryConstants() {
        super();
        cc.put("31","datatype.country.uy"); 
        cc.put("32","datatype.country.br");
        cc.put("33","datatype.country.ar"); 
        cc.put("34","datatype.country.py"); 
        cc.put("37","datatype.country.ec"); 
        cc.put("38","datatype.country.bo"); 
        cc.put("39","datatype.country.ve"); 
        cc.put("40","datatype.country.us"); 
        cc.put("41","datatype.country.ca"); 
        cc.put("42","datatype.country.mx"); 
        cc.put("43","datatype.country.fr"); 
        cc.put("44","datatype.country.it"); 
        cc.put("45","datatype.country.de"); 
        cc.put("46","datatype.country.ru"); 
        cc.put("48","datatype.country.ag"); 
        cc.put("49","datatype.country.eg"); 
        cc.put("51","datatype.country.in"); 
        cc.put("52","datatype.country.jp"); 
        cc.put("53","datatype.country.be"); 
        cc.put("54","datatype.country.cl"); 
        cc.put("55","datatype.country.co"); 
        cc.put("56","datatype.country.cr"); 
        cc.put("57","datatype.country.do"); 
        cc.put("58","datatype.country.gr"); 
        cc.put("59","datatype.country.gt"); 
        cc.put("60","datatype.country.ha"); 
        cc.put("61","datatype.country.hn"); 
        cc.put("62","datatype.country.ni"); 
        cc.put("63","datatype.country.pa"); 
        cc.put("64","datatype.country.pe"); 
        cc.put("65","datatype.country.ph"); 
        cc.put("66","datatype.country.pr"); 
        cc.put("67","datatype.country.sv"); 
        cc.put("68","datatype.country.th"); 
        cc.put("69","datatype.country.tt"); 
        cc.put("70","datatype.country.bb"); 
        cc.put("71","datatype.country.cu"); 
        cc.put("72","datatype.country.gp"); 
        cc.put("73","datatype.country.ma"); 
        cc.put("74","datatype.country.at"); 
        cc.put("75","datatype.country.au"); 
        cc.put("76","datatype.country.bs"); 
        cc.put("77","datatype.country.cz"); 
        cc.put("78","datatype.country.dk"); 
        cc.put("79","datatype.country.ee"); 
        cc.put("80","datatype.country.fi"); 
        cc.put("81","datatype.country.hu"); 
        cc.put("82","datatype.country.id"); 
        cc.put("83","datatype.country.ie"); 
        cc.put("84","datatype.country.il"); 
        cc.put("85","datatype.country.ja"); 
        cc.put("86","datatype.country.lv"); 
        cc.put("87","datatype.country.lt"); 
        cc.put("88","datatype.country.my"); 
        cc.put("89","datatype.country.nl"); 
        cc.put("90","datatype.country.nz"); 
        cc.put("91","datatype.country.no"); 
        cc.put("93","datatype.country.pk"); 
        cc.put("94","datatype.country.pl"); 
        cc.put("95","datatype.country.pt"); 
        cc.put("96","datatype.country.sa"); 
        cc.put("97","datatype.country.hk"); 
        cc.put("98","datatype.country.sg"); 
        cc.put("99","datatype.country.sk"); 
        cc.put("100","datatype.country.za"); 
        cc.put("101","datatype.country.kr"); 
        cc.put("102","datatype.country.se"); 
        cc.put("103","datatype.country.ch"); 
        cc.put("104","datatype.country.tr"); 
        cc.put("106","datatype.country.ae"); 
        cc.put("107","datatype.country.vn"); 
        cc.put("153","datatype.country.cn"); 
        cc.put("154","datatype.country.bd"); 
        cc.put("156","datatype.country.lk"); 
        cc.put("157","datatype.country.tw"); 
        cc.put("159","datatype.country.bg"); 
        cc.put("160","datatype.country.es"); 
        cc.put("161","datatype.country.gb"); 
        cc.put("163","datatype.country.hr"); 
        cc.put("164","datatype.country.jo"); 
        cc.put("165","datatype.country.ke"); 
        cc.put("167","datatype.country.ro"); 
        cc.put("168","datatype.country.ua"); 
        cc.put("170","datatype.country.bz"); 
        cc.put("171","datatype.country.bm");
        cc.put("172","datatype.country.ba"); 
        cc.put("173","datatype.country.al"); 
        cc.put("174","datatype.country.dm"); 
        cc.put("175","datatype.country.lb"); 
        cc.put("176","datatype.country.lu"); 
        cc.put("177","datatype.country.si"); 
        cc.put("179","datatype.country.gf"); 
    }

    public CountryConstants(Text messages) {
        super();
        countries.add(new CountryData("31",messages.getText("datatype.country.uy"))); 
        countries.add(new CountryData("32",messages.getText("datatype.country.br")));
        countries.add(new CountryData("33",messages.getText("datatype.country.ar"))); 
        countries.add(new CountryData("34",messages.getText("datatype.country.py"))); 
        countries.add(new CountryData("37",messages.getText("datatype.country.ec"))); 
        countries.add(new CountryData("38",messages.getText("datatype.country.bo"))); 
        countries.add(new CountryData("39",messages.getText("datatype.country.ve"))); 
        countries.add(new CountryData("40",messages.getText("datatype.country.us"))); 
        countries.add(new CountryData("41",messages.getText("datatype.country.ca"))); 
        countries.add(new CountryData("42",messages.getText("datatype.country.mx"))); 
        countries.add(new CountryData("43",messages.getText("datatype.country.fr"))); 
        countries.add(new CountryData("44",messages.getText("datatype.country.it"))); 
        countries.add(new CountryData("45",messages.getText("datatype.country.de"))); 
        countries.add(new CountryData("46",messages.getText("datatype.country.ru"))); 
        countries.add(new CountryData("48",messages.getText("datatype.country.ag"))); 
        countries.add(new CountryData("49",messages.getText("datatype.country.eg"))); 
        countries.add(new CountryData("51",messages.getText("datatype.country.in"))); 
        countries.add(new CountryData("52",messages.getText("datatype.country.jp"))); 
        countries.add(new CountryData("53",messages.getText("datatype.country.be"))); 
        countries.add(new CountryData("54",messages.getText("datatype.country.cl"))); 
        countries.add(new CountryData("55",messages.getText("datatype.country.co"))); 
        countries.add(new CountryData("56",messages.getText("datatype.country.cr"))); 
        countries.add(new CountryData("57",messages.getText("datatype.country.do"))); 
        countries.add(new CountryData("58",messages.getText("datatype.country.gr"))); 
        countries.add(new CountryData("59",messages.getText("datatype.country.gt"))); 
        countries.add(new CountryData("60",messages.getText("datatype.country.ha"))); 
        countries.add(new CountryData("61",messages.getText("datatype.country.hn"))); 
        countries.add(new CountryData("62",messages.getText("datatype.country.ni"))); 
        countries.add(new CountryData("63",messages.getText("datatype.country.pa"))); 
        countries.add(new CountryData("64",messages.getText("datatype.country.pe"))); 
        countries.add(new CountryData("65",messages.getText("datatype.country.ph"))); 
        countries.add(new CountryData("66",messages.getText("datatype.country.pr"))); 
        countries.add(new CountryData("67",messages.getText("datatype.country.sv"))); 
        countries.add(new CountryData("68",messages.getText("datatype.country.th"))); 
        countries.add(new CountryData("69",messages.getText("datatype.country.tt"))); 
        countries.add(new CountryData("70",messages.getText("datatype.country.bb"))); 
        countries.add(new CountryData("71",messages.getText("datatype.country.cu"))); 
        countries.add(new CountryData("72",messages.getText("datatype.country.gp"))); 
        countries.add(new CountryData("73",messages.getText("datatype.country.ma"))); 
        countries.add(new CountryData("74",messages.getText("datatype.country.at"))); 
        countries.add(new CountryData("75",messages.getText("datatype.country.au"))); 
        countries.add(new CountryData("76",messages.getText("datatype.country.bs"))); 
        countries.add(new CountryData("77",messages.getText("datatype.country.cz"))); 
        countries.add(new CountryData("78",messages.getText("datatype.country.dk"))); 
        countries.add(new CountryData("79",messages.getText("datatype.country.ee"))); 
        countries.add(new CountryData("80",messages.getText("datatype.country.fi"))); 
        countries.add(new CountryData("81",messages.getText("datatype.country.hu"))); 
        countries.add(new CountryData("82",messages.getText("datatype.country.id"))); 
        countries.add(new CountryData("83",messages.getText("datatype.country.ie"))); 
        countries.add(new CountryData("84",messages.getText("datatype.country.il"))); 
        countries.add(new CountryData("85",messages.getText("datatype.country.ja"))); 
        countries.add(new CountryData("86",messages.getText("datatype.country.lv"))); 
        countries.add(new CountryData("87",messages.getText("datatype.country.lt"))); 
        countries.add(new CountryData("88",messages.getText("datatype.country.my"))); 
        countries.add(new CountryData("89",messages.getText("datatype.country.nl"))); 
        countries.add(new CountryData("90",messages.getText("datatype.country.nz"))); 
        countries.add(new CountryData("91",messages.getText("datatype.country.no"))); 
        countries.add(new CountryData("93",messages.getText("datatype.country.pk"))); 
        countries.add(new CountryData("94",messages.getText("datatype.country.pl"))); 
        countries.add(new CountryData("95",messages.getText("datatype.country.pt"))); 
        countries.add(new CountryData("96",messages.getText("datatype.country.sa"))); 
        countries.add(new CountryData("97",messages.getText("datatype.country.hk"))); 
        countries.add(new CountryData("98",messages.getText("datatype.country.sg"))); 
        countries.add(new CountryData("99",messages.getText("datatype.country.sk"))); 
        countries.add(new CountryData("100",messages.getText("datatype.country.za"))); 
        countries.add(new CountryData("101",messages.getText("datatype.country.kr"))); 
        countries.add(new CountryData("102",messages.getText("datatype.country.se"))); 
        countries.add(new CountryData("103",messages.getText("datatype.country.ch"))); 
        countries.add(new CountryData("104",messages.getText("datatype.country.tr"))); 
        countries.add(new CountryData("106",messages.getText("datatype.country.ae"))); 
        countries.add(new CountryData("107",messages.getText("datatype.country.vn"))); 
        countries.add(new CountryData("153",messages.getText("datatype.country.cn"))); 
        countries.add(new CountryData("154",messages.getText("datatype.country.bd"))); 
        countries.add(new CountryData("156",messages.getText("datatype.country.lk"))); 
        countries.add(new CountryData("157",messages.getText("datatype.country.tw"))); 
        countries.add(new CountryData("159",messages.getText("datatype.country.bg"))); 
        countries.add(new CountryData("160",messages.getText("datatype.country.es"))); 
        countries.add(new CountryData("161",messages.getText("datatype.country.gb"))); 
        countries.add(new CountryData("163",messages.getText("datatype.country.hr"))); 
        countries.add(new CountryData("164",messages.getText("datatype.country.jo"))); 
        countries.add(new CountryData("165",messages.getText("datatype.country.ke"))); 
        countries.add(new CountryData("167",messages.getText("datatype.country.ro"))); 
        countries.add(new CountryData("168",messages.getText("datatype.country.ua"))); 
        countries.add(new CountryData("170",messages.getText("datatype.country.bz"))); 
        countries.add(new CountryData("171",messages.getText("datatype.country.bm")));
        countries.add(new CountryData("172",messages.getText("datatype.country.ba"))); 
        countries.add(new CountryData("173",messages.getText("datatype.country.al"))); 
        countries.add(new CountryData("174",messages.getText("datatype.country.dm"))); 
        countries.add(new CountryData("175",messages.getText("datatype.country.lb"))); 
        countries.add(new CountryData("176",messages.getText("datatype.country.lu"))); 
        countries.add(new CountryData("177",messages.getText("datatype.country.si"))); 
        countries.add(new CountryData("179",messages.getText("datatype.country.gf"))); 

    }

    public void setLAData(Text messages) {
	    countries.add(new CountryData("31",messages.getText("datatype.country.uy"))); 
	    countries.add(new CountryData("32",messages.getText("datatype.country.br")));
	    countries.add(new CountryData("33",messages.getText("datatype.country.ar")));
        countries.add(new CountryData("34",messages.getText("datatype.country.py"))); 
        countries.add(new CountryData("54",messages.getText("datatype.country.cl"))); 
        countries.add(new CountryData("180",messages.getText("generic.messages.other"))); 
    }

    public Iterator getCountryIterator() {
        Collections.sort(countries);
        return countries.iterator();
    }
    
    public String find(String id) {
        Iterator it = countries.iterator();
        while(it.hasNext()) {
            CountryData data = (CountryData)it.next();
            if(data.getId().equals(id)) {
                return data.getName();
            }
        }
        return null;
    }

	public Hashtable getCc() {
		return cc;
	}

	public void setCc(Hashtable cc) {
		this.cc = cc;
	}

	public boolean exists(String id) {
		return cc.containsKey(id);
	}
}

