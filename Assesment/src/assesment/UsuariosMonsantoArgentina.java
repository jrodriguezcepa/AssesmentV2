package assesment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.util.MD5;

public class UsuariosMonsantoArgentina {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

	public static void main(String[] args) {
       	String[][] users = {{"ADALBERTO DANTE","DE SETA","dante.deseta@monsanto.com"},{
       		"ADOLFO ALFREDO","MARTIRE","adolfo.alfredo.martire@monsanto.com"},{
       			"ADRIAN","GOMEZ","adrian.gomez@monsanto.com"},{
       			"ADRIANA CLAUDIA","GALASSI","claudia.adriana.galassi@monsanto.com"},{
       			"AGATA AGUSTINA","BALIAN","agata.balian@monsanto.com"},{
       			"AGOSTINA","MAZZOLENI","agostina.mazzoleni@monsanto.com"},{
       			"AGUSTIN","BOURDIEU","agustin.bourdieu@monsanto.com"},{
       			"AGUSTIN","SALVARREDI","agustin.salvarredi@monsanto.com"},{
       			"AGUSTIN RICARDO","PONTACUARTO","agustin.pontacuarto@monsanto.com"},{
       			"ALBERTO","ALBORNOZ","alberto.albornoz@monsanto.com"},{
       			"ALBERTO","PEPER","alberto.peper@monsanto.com"},{
       			"ALBERTO SEBASTIAN","BONGIOVANNI","alberto.sebastian.bongiovanni@monsanto.com"},{
       			"ALDO ALBERTO","SUAREZ","aldo.suarez@monsanto.com"},{
       			"ALEJANDRA PAULA","PRADO HAYWARD","alejandra.p.prado@monsanto.com"},{
       			"ALEJANDRO","CRESPO","alejandro.hernan.crespo@monsanto.com"},{
       			"ALEJANDRO AGUSTIN","MASCIOTTA","alejandro.masciotta@monsanto.com"},{
       			"ALEJANDRO BENJAMIN ","MAROLDA","benjamin.marolda@monsanto.com"},{
       			"ALEJANDRO DAMIAN","CELANI","alejandro.celani@monsanto.com"},{
       			"ALEJANDRO SEBASTIAN","GOMEZ","alejandro.sebastian.gomez@monsanto.com"},{
       			"ALEJO","SERINO","alejo.serino@monsanto.com"},{
       			"ALEJO ","VALVERDE","alejo.valverde@monsanto.com"},{
       			"ALFREDO SERGIO","MENEVICHIAN","alfredo.s.menevichian@monsanto.com"},{
       			"ANA MARIA ","CARDOSO","ana.maria.cardoso@monsanto.com"},{
       			"ANTONIO","SMITH","antonio.c.smith@monsanto.com"},{
       			"ARIEL","KOZELECZYK","ariel.kozeleczyk@monsanto.com"},{
       			"AUGUSTO RAUL","GUILERA","augusto.raul.guilera@monsanto.com"},{
       			"BERNARDO","CALVO  ISAZA","bernardo.calvo.isaza@monsanto.com"},{
       			"CAMILA","MALASPINA","camila.malaspina@monsanto.com"},{
       			"CARINA","SANTA MARIA","carina.santa.maria@monsanto.com"},{
       			"CARLOS","ANDRADE","carlos.r.andrade@monsanto.com"},{
       			"CARLOS","CODA","carlos.coda@monsanto.com"},{
       			"CARLOS","MURATORI","carlos.muratori@monsanto.com"},{
       			"CARLOS ADRIAN","CARLETTI","carlos.carletti@monsanto.com"},{
       			"CARLOS ALBERTO","CINGOLANI","carlos.cingolani@monsanto.com"},{
       			"CARLOS ANDRES","PINO","carlos.andres.pino.egusquiza@monsanto.com"},{
       			"CARLOS MARIO","CANALE","carlos.canale@monsanto.com"},{
       			"CAROLINA","GONZALEZ ECHEGARAY","carolina.gonzalez.echegaray@monsanto.com"},{
       			"CAROLINA P","MIGLIORE","carolina.migliore@monsanto.com"},{
       			"CECILIA","PELUFFO","cecilia.peluffo@monsanto.com"},{
       			"CESAR ORLANDO","ZANIN","cesar.o.zanin@monsanto.com"},{
       			"CLARA","RUBINSTEIN","clara.p.rubinstein@monsanto.com"},{
       			"CLAUDIO GABRIEL","ROBREDO","claudio.robredo@monsanto.com"},{
       			"CLAUDIO OSCAR","GARCIA","claudio.garcia@monsanto.com"},{
       			"CRISTIAN","SANTOS","cristian.santos@monsanto.com"},{
       			"CRISTIAN","BUSSIO","cristian.bussio@monsanto.com"},{
       			"CRISTIAN ALBERTO","CASELLES ANDREONI","cristian.caselles@monsanto.com"},{
       			"CRUZ","GOTI","cruz.goti@monsanto.com"},{
       			"DANIEL ALFREDO","TOCCALINO","daniel.toccalino@monsanto.com"},{
       			"DANIEL EDGARDO","AGUDO","daniel.1.agudo@monsanto.com"},{
       			"DANIEL RAMON","FRANCO","daniel.franco@monsanto.com"},{
       			"DARDO SERGIO","LIZARRAGA","dardo.s.lizarraga@monsanto.com"},{
       			"DEBORA IRENE","PUECHER","debora.irene.puecher@monsanto.com"},{
       			"DIEGO","KAVANAS","diego.kavanas@monsanto.com"},{
       			"DIEGO ","VILAPLANA","diego.ernesto.vilaplana@monsanto.com"},{
       			"DIEGO FRANCISCO","VALIENTE","diego.francisco.valiente@monsanto.com"},{
       			"EDGARDO OSMAR","GUIDA","edgardo.o.guida@monsanto.com"},{
       			"EDUARDO JAVIER","SZYMSIOWICZ","eduardo.j.szymsiowicz@monsanto.com"},{
       			"ELIO GASTON","TISERA","elio.tisera@monsanto.com"},{
       			"EMILIA","MALONE","emilia.malone@monsanto.com"},{
       			"ENRIQUE MARIANO","SORO","enrique.m.soro@monsanto.com"},{
       			"ERNESTO","TERAN VEGA","ernesto.teran.vega@monsanto.com"},{
       			"ERNESTO DANIEL","WOLCANYIK","ernesto.wolcanyik@monsanto.com"},{
       			"ERNESTO MARTIN","MIGUENS","ernesto.miguens@monsanto.com"},{
       			"ESTEBAN ","ARDISSONE","ESTEBAN.ARDISSONE@monsanto.com"},{
       			"ESTEBAN AGUSTIN","NOVELLO","esteban.franschina.novello@monsanto.com"},{
       			"EZEQUIEL","LATORRE","ezequiel.latorre@monsanto.com"},{
       			"EZEQUIEL","MORENO PELLEGRINi","ezequiel.moreno.pellegrini@monsanto.com"},{
       			"FABIAN OMAR","HERRERA","fabian.herrera@monsanto.com"},{
       			"FABIANA CARMEN","ALDAVE","fabiana.carmen.aldave@monsanto.com"},{
       			"FACUNDO ","FONTANA","facundo.fontana@monsanto.com"},{
       			"FACUNDO EDUARDO","DOMINGUEZ","facundo.dominguez@monsanto.com"},{
       			"FEDERICO","CECONELLO","federico.ceconello@monsanto.com"},{
       			"FEDERICO","FIRPO","federico.pedro.firpo@monsanto.com"},{
       			"FEDERICO","GARAT","federico.garat@monsanto.com"},{
       			"FEDERICO","MATTIOLI","federico.mattioli@monsanto.com"},{
       			"FEDERICO","NOVILLO CORVALAN","federico.novillo@monsanto.com"},{
       			"FEDERICO","OVEJERO","federico.ovejero@monsanto.com"},{
       			"FEDERICO","VARTORELLI","federico.alberto.vartorelli@monsanto.com"},{
       			"FERNANDO","BAUSO","fernando.bauzo@monsanto.com"},{
       			"FERNANDO","MATA","FERNANDO.MATA@monsanto.com"},{
       			"FERNANDO","SOLARI","fernando.solari@monsanto.com"},{
       			"FERNANDO HUGO","GIANNONI","fernando.hugo.giannoni@monsanto.com"},{
       			"FLORENCIA","NALDA","florencia.nalda@monsanto.com"},{
       			"FRANCISCO","CANO","francisco.s.cano@monsanto.com"},{
       			"FRANCO","DONATI","franco.donati@monsanto.com"},{
       			"GABRIEL","CARINII","gabriel.carini@monsanto.com"},{
       			"GABRIEL ADRIAN","CALCAGNO","gabriel.calcagno@monsanto.com"},{
       			"GABRIELA","SANTAROSSA","gabriela.santarossa@monsanto.com"},{
       			"GABRIELA","VITALE","gabriela.paola.vitale@monsanto.com"},{
       			"GERMAN","HEGI","german.hegi@monsanto.com"},{
       			"GERMAN ","FERRARI","german.ferrari@monsanto.com"},{
       			"GERTRUDIS","CORREA","gertrudis.correa@monsanto.com"},{
       			"GERVASIO CASIANO","CASAS","gervasio.casas@monsanto.com"},{
       			"GONZALO","ROVIRA","gonzalo.rovira@monsanto.com"},{
       			"GUILLERMO","SOLA VEDOYA","guillermo.sola.vedoya@monsanto.com"},{
       			"GUILLERMO","VAN BECELARE","guillermo.van.becelaere@monsanto.com"},{
       			"GUILLERMO","ZAMORA ","guillermo.zamora@monsanto.com"},{
       			"GUILLERMO MARTIN","CANEPA","guillermo.martin.canepa@monsanto.com"},{
       			"GUILLERMO WALDINO","VIDELA","guillermo.w.videla@monsanto.com"},{
       			"GUSTAVO","CALCATERRA","gustavo.oscar.calcaterra@monsanto.com"},{
       			"GUSTAVO","SABATO","gustavo.h.sabato@monsanto.com"},{
       			"GUSTAVO ADRIAN ","DEGLI ALBERTI","gustavo.adrian.degli.alberti@monsanto.com"},{
       			"GUSTAVO ALBERTO","CANAL","gustavo.canal@monsanto.com"},{
       			"GUSTAVO ARIEL","PAVON","gustavo.ariel.pavon@monsanto.com"},{
       			"GUSTAVO CARLOS","MEYER","gustavo.c.meyer@monsanto.com"},{
       			"GUSTAVO GABRIEL","LOPEZ","gustavo.lopez@monsanto.com"},{
       			"GUSTAVO OSCAR","GAUDIO","gustavo.o.gaudio@monsanto.com"},{
       			"HECTOR","TAMARGO","hector.tamargo@monsanto.com"},{
       			"HECTOR ALBERTO","MATTU","hector.alberto.mattu@monsanto.com"},{
       			"HECTOR DANIEL","FERELA","daniel.ferela@monsanto.com"},{
       			"HECTOR DANIEL","MEDINA LUNA","hector.daniel.medina.luna@monsanto.com"},{
       			"HERNAN","MORA","hernan.mora@monsanto.com"},{
       			"HERNAN FRANCISCO","INGRASIA BLANCO","hernan.francisco.ingrassia@monsanto.com"},{
       			"HUGO ENRIQUE","ROTEA","hugo.enrique.rotea@monsanto.com"},{
       			"HUMBERTO ","FLORES","humberto.flores@monsanto.com"},{
       			"HUMBERTO AGUSTIN","SIMONCINI","humberto.a.simoncini@monsanto.com"},{
       			"IGNACIO","MARCHETTO","ignacio.marchetto@monsanto.com"},{
       			"IGNACIO","NEGRI ARANGUREN","inegria@monsanto.com"},{
       			"JAVIER","LOTANO","javier.lotano@monsanto.com"},{
       			"JAVIER","SLONGO","javier.e.slongo@monsanto.com"},{
       			"JAVIER ALEJANDRO","CARRIEGO","javier.carriego@monsanto.com"},{
       			"JAVIER JESUS","CARLETTI BUENO","javier.j.carletti@monsanto.com"},{
       			"JOAQUIN","FERNANDEZ","joaquin.fernandez@monsanto.com"},{
       			"JORGE","MOLINA ZAVALLA","jorge.molina.zaballa@monsanto.com"},{
       			"JORGE","MAGNANO","jorge.r.magnano@monsanto.com"},{
       			"JORGE EDUARDO","AMADEO","jorge.e.amadeo@monsanto.com"},{
       			"JORGE MARIO","RUSSO","jorge.m.russo@monsanto.com"},{
       			"JORGELINA","SANCHEZ","jorgelina.sanchez@monsanto.com"},{
       			"JOSE","LASCANO","jose.lascano@monsanto.com"},{
       			"JOSE ANTONIO","SELMI","jose.selmi@monsanto.com"},{
       			"JOSE CLEMENTINO","CORREA","jose.correa@monsanto.com"},{
       			"JOSE DARIO","OLESZCZUCK","jose.d.oleszczuk@monsanto.com"},{
       			"JOSE HORACIO","MUTTI","jose.h.mutti@monsanto.com"},{
       			"JOSE LUIS","LOPEZ","jose.luis.lopez@monsanto.com"},{
       			"JOSE LUIS","PIOLUCCI","jose.l.piolucci@monsanto.com"},{
       			"JOSE LUIS","TOLOZA","jose.tolosa@la2.monsanto.com"},{
       			"JOSE MANUEL","GUTIERREZ","jose.manuel.gutierrez@monsanto.com"},{
       			"JUAN","FARINATI","juan.m.farinati@seminis.com"},{
       			"JUAN CRUZ","WALLACE","juan.cruz.wallace@monsanto.com"},{
       			"JUAN EDUARDO","AZCARATE","juan.eduardo.azcarate@monsanto.com"},{
       			"JUAN JOSE","DELEO","juan.deleo@monsanto.com"},{
       			"JUAN JOSE","VILLAVICENCIO","juan.jose.villavicencio@monsanto.com"},{
       			"JUAN MANUEL","DE SANTA EDUVIGES","juan.manuel.de.santa.eduviges@monsanto.com"},{
       			"JUAN MARTIN","LIZARDO","juan.m.lizardo@monsanto.com"},{
       			"JUAN PABLO","MARINI","juan.pablo.marini@monsanto.com"},{
       			"JUAN PATRICIO","GUNNING","patricio.gunning@monsanto.com"},{
       			"JUAN PEDRO","GAZZOTTI","juan.pedro.gazzotti@monsanto.com"},{
       			"JULIO","CANTAGALLO","julio.cantagallo@monsanto.com"},{
       			"JULIO EDUARDO","DELUCCHI","julio.e.delucchi@monsanto.com"},{
       			"KARINA","ZITTI","karina.e.zitti@monsanto.com"},{
       			"KRISTIAN","HANSEN","kristian.andres.hansen@monsanto.com"},{
       			"LEONARDO","GIRAUDO","leonardo.giraudo@seminis.com"},{
       			"LEONARDO","ROSSELLO","leonardo.rossello@monsanto.com"},{
       			"LEONARDO JAVIER","GONZALEZ","leonardo.javier.gonzalez@monsanto.com"},{
       			"LORENA","GARCIA","lorena.natalia.garcia@monsanto.com"},{
       			"LUCIANO JAVIER","SCHETTINO","luciano.javier.schettino@monsanto.com"},{
       			"LUCIANO MAURO","PANE","luciano.pane@monsanto.com"},{
       			"LUIS","COPELAND","luis.copeland@monsanto.com"},{
       			"LUIS","RASO","francisco.raso@monsanto.com"},{
       			"LUIS ALBERTO","BAGUR","luis.alberto.bagur@monsanto.com"},{
       			"LUIS ALBERTO","SALZMAN","luis.salzman@monsanto.com"},{
       			"LUIS ALBERTO","ZABALA GONELLA","luis.zabala@monsanto.com"},{
       			"LUIS EDUARDO","SILVA","luis.eduardo.silva@monsanto.com"},{
       			"LUIS FRANCISCO","DEBOUVRY","luis.f.debouvry@monsanto.com"},{
       			"MABEL ELSA","GALEANO","mabel.galeano@monsanto.com"},{
       			"MACARENA","ALCALA ZAMORA","macarena.alcala.zamora@monsanto.com"},{
       			"MARCELO","MELANI","marcelo.melani@monsanto.com"},{
       			"MARCELO","PERUILH","marcelo.c.peruilh@monsanto.com"},{
       			"MARCELO ABEL","SAUTHIER","marcelo.sauthier@monsanto.com"},{
       			"MARCELO DAVID","ARNOLFO","marcelo.arnolfo@monsanto.com"},{
       			"MARCELO E.","BOJANICH","marcelo.elis.bojanich@monsanto.com"},{
       			"MARCOS","ZAPIOLA SOMMER","marcos.zapiola.sommer@monsanto.com"},{
       			"MARCOS","CARRERA","marcos.carrera@monsanto.com"},{
       			"MARIA AGUSTINA","CACHAU","maria.agustina.cachau@monsanto.com"},{
       			"MARIA ANDREA","BADAROUX","maria.badaroux@monsanto.com"},{
       			"MARIA CAROLINA","ALEGRE","maria.carolina.alegre@monsanto.com"},{
       			"MARIA CECILIA","LUCERO","maria.cecilia.lucero@monsanto.com"},{
       			"MARIA CELESTE","BALBI","maria.celeste.balbi@monsanto.com"},{
       			"MARIA CLARA","AMBROGGIO","maria.clara.ambroggio@monsanto.com"},{
       			"MARIA ELENA","ANTONELLI","maria.elena.antonelli@monsanto.com"},{
       			"MARIA EUGENIA","SANGUINETTI","maria.e.sanguinetti@monsanto.com"},{
       			"MARIA EVA","GONZALEZ","maria.eva.gonzalez@monsanto.com"},{
       			"MARIA FLORENCIA","SCALAMBRO","maria.scalambro@monsanto.com"},{
       			"MARIA GUILLERMINA","FIGUEREDO","guillermina.figueredo@monsanto.com"},{
       			"MARIA PAULA","BODRONE","paula.bodrone@monsanto.com"},{
       			"MARIA SILVIA","CONTI","maria.s.conti@monsanto.com"},{
       			"MARIA SUSANA","ROSSI","maria.susana.rossi@monsanto.com"},{
       			"MARIANA","ROCCO","mariana.i.rocco@monsanto.com"},{
       			"MARIANO","MENDIETA","mariano.mendieta@monsanto.com"},{
       			"MARIANO","FORMOSO","mariano.formoso@monsanto.com"},{
       			"MARIANO","MORETTI","mariano.moretti@monsanto.com"},{
       			"MARIANO ANIBAL","TURPIN","mariano.turpin@monsanto.com"},{
       			"MARIELA BEATRIZ","OLMEDO","mariela.olmedo@monsanto.com"},{
       			"MARIO","BONILLA","mario.bonilla@monsanto.com"},{
       			"MARIO CESAR","FAURE","mario.faure@monsanto.com"},{
       			"MARIO DARIO","FERNANDEZ","dario.fernandez@seminis.com"},{
       			"MARIO DAVID","CELA","mario.cela@monsanto.com"},{
       			"MARTIN","SATRAGNI","martin.satragni@monsanto.com"},{
       			"MARTIN","URIBELARREA","martin.uribelarrea@monsanto.com"},{
       			"MARTIN","LARES","martin.lares@monsanto.com"},{
       			"MARTIN","SACKMANN VARELA","martin.sackmann@monsanto.com"},{
       			"MARTIN DAMIAN","ESCOBAR VEGA","martin.damian.escobar.vega@monsanto.com"},{
       			"MARTIN EUGENIO","GOMEZ","martin.e.gomez@monsanto.com"},{
       			"MATIAS","ALTAMIRANO","matias.altamirano@monsanto.com"},{
       			"MATIAS AGUSTIN","JARALAMBIDES","matias.jaralambides@monsanto.com"},{
       			"MATIAS ALEJANDRO","MARCANTONIO","matias.marcantonio@monsanto.com"},{
       			"MAURICIO","ACOSTA","mauricio.acosta@monsanto.com"},{
       			"MAURO","DIAZ","mauro.diaz@monsanto.com"},{
       			"MAXIMILIANO NESTOR","CUETO","maximiliano.nestor.cueto@monsanto.com"},{
       			"MAXIMO ALBERTO","VAZQUEZ","maximo.vazquez@monsanto.com"},{
       			"MICHIEL","DE JONGH","michiel.de.jongh@monsanto.com"},{
       			"MIGUEL","ALVAREZ ARANCEDO","miguel.alvarez.arancedo@monsanto.com"},{
       			"MIGUEL ANGEL","SEQUEIRA","miguel.sequeira@monsanto.com"},{
       			"MIGUEL ANGEL","VALDEZ","miguel.angel.valdez@monsanto.com"},{
       			"MIGUEL ARTURO","REGIDOR","miguel.a.regidor@monsanto.com"},{
       			"MIRNA DE LOS MILAGROS","RICCIARDI","mirna.ricciardi@monsanto.com"},{
       			"MITCHELL","ANDREW","andrew.mitchell@monsanto.com"},{
       			"NICOLAS EMILIO","SENOR","NICOLAS.EMILIO.SENOR@MONSANTO.com"},{
       			"NIKALAI","GOMEZ BARANOFF","nikalai.gomez.baranoff@monsanto.com"},{
       			"NORMA BEATRIZ","FERNANDEZ","norma.b.fernandez@monsanto.com"},{
       			"OMAR ALCIDES","RIAL","omar.rial@monsanto.com"},{
       			"OMAR JOSE","PAOLUCCI","omar.paolucci@monsanto.com"},{
       			"OSCAR","JIMENEZ CALLERI","oscar.jimenez.calleri@monsanto.com"},{
       			"OSCAR A.","BERNARD","oscar.bernard@monsanto.com"},{
       			"OSCAR EDUARDO","GONZALEZ","oscar.gonzalez@monsanto.com"},{
       			"PABLO","COPERTARI","pablo.copertari@monsanto.com"},{
       			"PABLO","GERMAN TALANO","pablo.german.talano@monsanto.com"},{
       			"PABLO","LEONARD","pablo.leonard@monsanto.com"},{
       			"PABLO","MASTRONARDI","pablo.mastronardi@monsanto.com"},{
       			"PABLO","OGALLAR","pablo.ogallar@monsanto.com"},{
       			"PABLO","TROCCOLI","pablo.troccoli@monsanto.com"},{
       			"PABLO ","KALNAY","pablo.kalnay@monsanto.com"},{
       			"PABLO A.","VAQUERO","pablo.a.vaquero@monsanto.com"},{
       			"PABLO ALEJANDRO","GROSSO","pablo.a.grosso@monsanto.com"},{
       			"PABLO ANDRES","GUISTETTI","pablo.andres.giustetti@monsanto.com"},{
       			"PABLO DANIEL","CARPANE","pablo.carpane@monsanto.com"},{
       			"PABLO IGNACIO","FERGUSON","pablo.ferguson@monsanto.com"},{
       			"PATRICIO","MARSHALL","patricio.marshall@monsanto.com"},{
       			"PATRICIO","VIGNOLI","patricio.vignoli@monsanto.com"},{
       			"PEDRO CLEMENTE","ALONSO HIDALGO","pedro.alonso.hidalgo@monsanto.com"},{
       			"PEDRO MIGUEL","HALES","pedro.hales@monsanto.com"},{
       			"PORFIRIO","VILLALBA MIRANDA","porfirio.villalba.miranda@monsanto.com"},{
       			"RAFAEL","GUZMAN GARCIA","rafael.guzman@monsanto.com"},{
       			"RAFAEL","ROMANO CAVANAGH","rafael.cavanagh@monsanto.com"},{
       			"RAMON DARIO","MANSILLA","ramon.mansilla@monsanto.com"},{
       			"RAUL","DI FRANCESCO","raul.difrancesco@monsanto.com"},{
       			"RAUL DAVID","CARBALLEDA","raul.carballeda@monsanto.com"},{
       			"RAUL HORACIO","DI GREGORIO","raul.horacio.di.gregorio@monsanto.com"},{
       			"RICARDO","DARIOZZI","ricardo.jose.dariozzi@monsanto.com"},{
       			"RICARDO","GUTIERREZ","ricardo.gutierrez@monsanto.com"},{
       			"RICARDO LUIS","RANEA","ricardo.ranea@monsanto.com"},{
       			"ROBERTO DANIEL ","ALGARBE","alberto.algarbe@monsanto.com"},{
       			"ROBERTO OSCAR","BUDANO","roberto.budano@monsanto.com"},{
       			"RODRIGO","ISIDRO","rodrigo.isidro@monsanto.com"},{
       			"RODRIGO","SALA","rodrigo.sala@monsanto.com"},{
       			"ROMAN","CARRAL","roman.carral@monsanto.com"},{
       			"RUBEN","MURATORI","ruben.muratori@monsanto.com"},{
       			"RUBEN ALFREDO","MANZOTTI","ruben.manzotti@monsanto.com"},{
       			"RUBEN FABIAN","ROSATO","ruben.rosato@monsanto.com"},{
       			"RUBEN HORACIO","CERVIGNI","ruben.cervigni@monsanto.com"},{
       			"RUBEN NICOLAS","CORONEL","ruben.coronel@monsanto.com"},{
       			"SANDRA LORENA","SCOTT","sandra.scott@monsanto.com"},{
       			"SANTIAGO","ASTORGA","santiago.astorga@monsanto.com"},{
       			"SANTIAGO","DIAZ VALDEZ","santiago.diaz.valdez@monsanto.com"},{
       			"SANTIAGO FEDERICO","VIANA BORDABERRY","santiago.viana.bordaberry@monsanto.com"},{
       			"SANTIAGO JUAN","PERKINS","santiago.perkins@monsanto.com"},{
       			"SANTIAGO RAMON","MAZZINI","santiago.mazzini@monsanto.com"},{
       			"SANTIAGO ROBERTO","MORDEGLIA","santiago.mordeglia@monsanto.com"},{
       			"SEBASTIAN","ARISNABARRETA","sebastian.arisnabarreta.dupuy@monsanto.com"},{
       			"SEBASTIAN","BASANTA","sebastian.basanta@monsanto.com"},{
       			"SEBASTIAN","BUSTILLO","sebastian.bustillo@monsanto.com"},{
       			"SEBASTIAN","FERRARI","sebastian.ferrari@monsanto.com"},{
       			"SEBASTIAN","TORRILLAS","sebastian.torrillas@monsanto.com"},{
       			"SERGIO DAMIAN","MORILLA","sdmoril@monsanto.com"},{
       			"SERGIO HORACIO","MANZOLINI","sergio.manzolini@monsanto.com"},{
       			"SERGIO RAUL","SELMI","sergio.raul.selmi@monsanto.com"},{
       			"SILVANA","ZAMPIERIN","silvana.m.zampierin@monsanto.com"},{
       			"SILVIA","CHANAME BUSTAMANTE","silvia.s.chaname@monsanto.com"},{
       			"SILVINA","SONEGO","silvina.sonego@monsanto.com"},{
       			"SILVINA BEATRIZ","LUSTIG","beatriz.silvina.lustig@monsanto.com"},{
       			"TODD","RANDS","tood.rands@monsanto.com"},{
       			"VALERIA","MARKIEWICZ","valeria.markiewicz@monsanto.com"},{
       			"VERONICA CRISTINA","TIBERI","veronica.tiberi@monsanto.com"},{
       			"VICTOR ALBERTO","CINQUEPALMI","victor.cinquepalmi@monsanto.com"},{
       			"VICTOR DANIEL","RUIZ","victor.ruiz@monsanto.com"},{
       			"VICTOR GASTON","ORTIZ DE ZARATE","victor.ortiz.de.zarate@monsanto.com"},{
       			"WALTER","POMAR","walter.pomar@monsanto.com"},{
       			"WALTER JAVIER","TAMBASCIO","walter.javier.tambascio@monsanto.com"},{
       			"WALTER LUIS","MADKUR","walter.luis.madkur@monsanto.com"},{
       			"YANINA DANIELA","LATORRE","Yanina.latorre@monsanto.com"},{
       			"Peter","Maremaa DEMO Chile","xmethol@cepasafedrive.com"},{
       			"Victor","Molina DEMO Chile","xmethol@cepasafedrive.com"},{
       			"BERNARDO","CALVO ISAZA","carolina.cotella@monsanto.com"},{
       			"OSCAR","GONZALEZ","oscar.alexander.gonzalez@monsanto.com"},{
       			"MARIA","HOURQUESCOS","maria.hourquescos@monsanto.com"},{
       			"LORENA","TRAVELLA","maria.l.travella@monsanto.com"},{
       			"LORENA","TRAVELLA","maria.l.travella@monsanto.com"},{
       			"MARIANO","CACERES","mariano.caceres@monsanto.com"}}
;

       	String[] valid = {"32f772d3ce64bfe4531ef31932b312c5",
       			"fe969a73f00bf238a4627d1418d77c6f",
       			"08b3145fff32f7dbdf37606cca1c3ad8",
       			"19d292280ebebe732002e196879f4186",
       			"cfdb296403cce5659b52432bac3fde52",
       			"8d1c94053c54b354074bf737cf6bf0f2",
       			"9922cb82ea0882c508fce3fb96516dad",
       			"23a54fec0f2e477425aeb6658344d06f",
       			"08211746671def8528abe5f1e901ab26",
       			"59fc09324ebe4d50e8b6bb0e08dd021c",
       			"f9386e8331f4ba4d2057f5bd09872684",
       			"1283a323878678f537ecfcd5240a9e10",
       			"4cf009cf4d16f318b9612376a42c1605",
       			"ec168be263eeb48795a86e6b09024559",
       			"f1c57bfae6ed21eb151bb4041d9e9bb2",
       			"dad5d2d19168145c2ce3e160d062f660",
       			"0bf947c7b7dec9f206391e7e942a4fc7",
       			"a8012c42a6826b923d0a65acd4a2540b",
       			"3d6098bb048b930236ac91f667da5c65",
       			"2da301e3be037b73d002c15315a45373",
       			"7262cdce14eb8df93f0526af69205634",
       			"90ad42ffd7de9ce5682260dbb30d95fa",
       			"3596080e567231130cabec2865251162",
       			"8f7d93a89e79d6a8b694a43495ee40b9",
       			"01d11794954f17a20fcbdebf6376e60a",
       			"2d682438e436f8cdbc57efbd7b08f611",
       			"c00c28540641e2ea89c32ad73aaf425f",
       			"e4e82bad07e6c4edc9fde6c96ca462aa",
       			"29292f005b57e65295d72305ba6cbb08",
       			"aa7b2e9ba7952b1301c6e515c6e3a112",
       			"e82b1123d3f2a02a0f93f41e30fa732a",
       			"2f7ae747b5ccc54aa458e4e0d5034c85",
       			"055a3a9a80f80e4e5fac4d2095ff3fa9",
       			"cb82de8cfa6930438323942785baf009",
       			"b2fdfeff8d9e7199543f780286e89cdf",
       			"94175b68f662ff22590e5fe8bd60cfb0",
       			"c0a84dd4a195753ac382e4c0a71841e8",
       			"5e5bb619c6bc77e843991be5d67cf267",
       			"7c82eb17f66a2a3136c63bb6a67194ab",
       			"4c293ee034a65fad0419e9e6137a84f8",
       			"e0a774c32e3f0be872d5b9d9d0e8204e",
       			"93c8b85a9110050319a24bfa18314ab9",
       			"26375f696c4c4f25d39f763dd9409266",
       			"aca3546e75075e85321be90e5c22d098",
       			"9fe5902e34123da25b090b107806d176",
       			"a28e0cfd9018afbf8997e6715ac960e2",
       			"9d11a06f7efde2fce376ef017ed617a1",
       			"08ef5a31ae999c8f2ae5eaedb14a2412",
       			"be10672d742a67b65a26dbd8a835d909",
       			"2122146d422c5246797e00c2f348c848"};
        File f = new File("C:/usersMonsantoArg161109.csv");
        FileOutputStream fos = null;
        try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException e1) {
			 //TODO Auto-generated catch block
			e1.printStackTrace();
		}
            
        MD5 md5 = new MD5();
    	for(int i = 0; i < users.length; i++) {
    		String email = users[i][2];

            boolean done = false;
        	while(!done) {
                try {
	        		String href = md5.encriptar(email);
	        		String login = md5.encriptar(href);
	        		
	        		String s = users[i][0]+" "+users[i][1]+";"+users[i][2]+";"+"http:da.cepasafedrive.com/assesment/index.jsp?login="+href+"\n";
	        		boolean exist = false;
	        		for(int ii = 0; ii < valid.length && !exist; ii++) {
	        			if(login.equals(valid[ii].trim())) {
	        				fos.write(s.getBytes());
	        				exist = true;
	        			}
	        		}
	        		if(!exist) {
	        			System.out.println(s);
	        		}
/*	        		String password = md5.encriptar(login);
	        		password = md5.encriptar(password);
        			String insert = "INSERT INTO users (loginname,firstname,lastname,language,email,password,role) VALUES ('"+login+"','"+users[i][0]+"','"+users[i][1]+"','es','"+email+"','"+password+"','systemaccess');\n";
	        		fos.write(insert.getBytes());
	        		insert = "INSERT INTO userassesments(assesment,loginname) VALUES (21,'"+login+"');\n";
	        		fos.write(insert.getBytes());
        			email = "federico.millan@cepasafedrive.com";
        			email = "juanyfla@adinet.com.uy";

        	        Properties properties = new Properties();
        	        properties.put("mail.smtp.host",SMTP);
        	        properties.put("mail.smtp.auth", "true");
        	        properties.put("mail.from",FROM_NAME);
        	        Session session = Session.getInstance(properties,null);
        	    
        	        InternetAddress[] address = {new InternetAddress(email,users[i][0]+" "+users[i][1])};
        			Message mensaje = new MimeMessage(session);
        			 Rellenar los atributos y el contenido
        			 Asunto
        			mensaje.setSubject("Invitación - Driver Assessment");
        			 Emisor del mensaje
        			mensaje.setFrom(new InternetAddress(FROM_DIR,FROM_NAME));
        			 Receptor del mensaje
        			mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress(email,users[i][0]+" "+users[i][1]));
        			 Crear un Multipart de tipo multipart/related
        			Multipart multipart = new MimeMultipart ("related");
        			 Leer el fichero HTML

        			 Rellenar el MimeBodyPart con el fichero e indicar que es un fichero HTML
        			BodyPart texto = new MimeBodyPart ();
        			texto.setContent(getMailRe("http:da.cepasafedrive.com/assesment/index.jsp?login="+href),"text/html");
        			multipart.addBodyPart(texto);

        			 Procesar la imagen
        			MimeBodyPart imagen = new MimeBodyPart();
        			imagen.attachFile(AssesmentData.FLASH_PATH+"/images/invitacion-mon-arg.jpg");
        			imagen.setHeader("Content-ID","<figura1>");
        			multipart.addBodyPart(imagen);
        			mensaje.setContent(multipart);
        			 Enviar el mensaje

        	        Transport transport = session.getTransport(address[0]);
        	        transport.connect(SMTP, FROM_DIR, PASSWORD);
        	        transport.sendMessage(mensaje,address);
*/
        	        done = true;
        	        System.out.println("ENVIADO "+login);        	        
        	        System.out.println("ENVIADO "+i+" -> "+email+" "+"http:da.cepasafedrive.com/assesment/index.jsp?login="+href);        	        
	            }catch (Exception e) {
	            	e.printStackTrace();
	    	        System.out.println("EXCEPCION "+email);
	            	// 	TODO: handle exception
	            }
    		}
    	}
	}
	
	private static String mail2() {
		String mail = "<html>" +
			"	<head>" +
			"	</head>" +
			"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
			"		<table>" +
			"			<tr>" +
			"				<td>" +
			"					<img src=\"cid:figura1\" alt=\"\">" +
			"				</td>" +
			"			</tr>" +
			"		</table>" +
			"	</body>" +
			"</html>";
		return mail;
	}
	
	private static String getMail(String href) {
		String mail = "<html>" +
			"	<head>" +
			"	</head>" +
			"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
			"		<table>" +
			"			<tr>" +
			"				<td>" +
			"					<img src=\"cid:figura1\" alt=\"\">" +
			"				</td>" +
			"			</tr>" +
			"			<tr>" +
			"				<td>" +
			"					<font size=\"-1\"><font face=\"Verdana\">" +
			"					<br>" +
			"					</font></font>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						Continuando con el proceso de mejoramiento continuo, lo invitamos a utilizar la herramienta web Driver Assessment.&nbsp;" +
			"						<br>" +
			"						El link a continuaci&oacute;n es privado y el mismo le servir&aacute; para ingresar a la herramienta cada vez que lo desee." +
			"						<br>" +
			"						Una vez termine todos los m&oacute;dulos, recibir&aacute; los reportes en esta misma cuenta de correo en formato PDF." +
			"					</span>" +
			"					<i>" +
			"						<span  style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<br>" +
			"						</span>" +
			"					</i>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br>" +
			"							<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
			"						<br>" +
			"						<small>" +
			"							<br>" +
			"							<font color=\"#666666\">" +
			"								<i><u>Importante:</u></i> En caso de no poder hacer click en el link por favor copie y pegue el link en un navegador." +
			"								<br>" +
			"							</font>" +
			"						</small>" +
			"					</span>" +
			"					<i>" +
			"						<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"							<br>" +
			"							Gracias por su participaci&oacute;n la misma es sumamente valiosa." +
			"						</span>" +
			"					</i>" +
			"					<br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br>" +
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						Por dudas y consultas favor comunicarse con" +
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b>" +
			"					</span>" +
			"					<br>" +
			"					<br>" +
			"					<br>" +
			"					<br>" +
			"				</td>" +
			"			</tr>" +
			"		</table>" +
			"	</body>" +
			"</html>";
		return mail;
	}


	private static String getMailRe(String href) {
		String mail = "<html>"+
				"<head>"+
				"</head>"+
				"<body bgcolor=\"#ffffff\" text=\"#000066\">"+
				"	<table>"+
				"		<tr>"+
				"			<td>"+
				"				<img src=\"cid:figura1\" alt=\"\">"+
				"			</td>"+
				"		</tr>"+
				"		<tr>"+
				"			<td>"+
				"				<font size=\"-1\"><font face=\"Verdana\">"+
				"				<br>"+
				"				</font></font>"+
				"				<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">"+
				"Estimados les recordamos la invitación a utilizar (o completar) la herramienta web Driver Assessment."+
				"<br>"+
				"<br>"+
				"El link a continuación está personalizado, contiene los módulos que debe completar, y el mismo le servirá para ingresar a la herramienta cada vez que lo desee. Una vez termine los módulos, recibirá un email con los reportes y/o certificado de la actividad."+
				"<br>"+
				"<br>"+
				"Gracias por su participación."+
				"				</span>"+
				"				<i>"+
				"					<span  style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">"+
				"					<br>"+
				"					</span>"+
				"				</i>"+
				"				<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">"+
				"					<br>"+
				"							<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
				"					<br>"+
				"				</span>"+
				"				<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">"+
				"					<br>"+
				"				</span>"+
				"				<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">"+
				"					<b>Importante:</b> algunos administradores de correos no permiten abrir link adjuntos, si es su caso, por favor copiar y pegar la dirección en su navegador."+
				"					<br>"+
				"					<br>"+
				"					Por dudas y consultas favor comunicarse con"+
				"				</span>"+
				"				<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">"+
				"					<b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b>"+
				"				</span>"+
				"				<br>"+
				"				<br>"+
				"				<br>"+
				"				<br>"+
				"			</td>"+
				"		</tr>"+
				"	</table>"+
				"</body>"+
				"</html>";
		return mail;
	}
}
