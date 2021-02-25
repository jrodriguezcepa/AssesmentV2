package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.question.QuestionData;

public class DatosEMEA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            File f = new File("C://emeaJJ.csv");
            FileOutputStream fos = new FileOutputStream(f);

            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();

        	Hashtable hash = new Hashtable();
        	hash.put("emea47_1556","Szatymaz Research Station");
        	hash.put("emea47_1557","Quasqai");
        	hash.put("emea50_1557","astra");
        	hash.put("emea51_1557","Indiquez le modle ici");
        	hash.put("emea60_1557","corsa 2 portes");
        	hash.put("emea61_1556","rja be a helyszint ide");
        	hash.put("emea67_1557","ASTRA");
        	hash.put("emea68_1557","Megane 1.6V");
        	hash.put("emea76_1557","Kangoo Pampa");
        	hash.put("emea73_1556","Mezhegyes");
        	hash.put("emea84_1557","ranger");
        	hash.put("emea83_1556","Dunntl");
        	hash.put("emea83_1557","Qashqai");
        	hash.put("emea89_1556","BRAILA");
        	hash.put("emea85_1556","Bcs-Kiskun megye");
        	hash.put("emea86_1556","Keszthely");
        	hash.put("emea94_1556","Szolnok");
        	hash.put("emea93_1557","Grand Vitara 2.0");
        	hash.put("emea96_1557","Ranger 4X4");
        	hash.put("emea98_1556","Braila");
        	hash.put("emea100_1556","Pcs");
        	hash.put("emea102_1556","Hajd-Bihar megye");
        	hash.put("emea103_1556","rja be a helyszint ide");
        	hash.put("emea106_1556","Slobozia");
        	hash.put("emea107_1556","rja be a helyszint ide");
        	hash.put("emea111_1556","Borsod Abaj-Zempln megye");
        	hash.put("emea112_1556","rja be a helyszint ide");
        	hash.put("emea115_1556","Pest megye");
        	hash.put("emea116_1556","Mtszalka");
        	hash.put("emea117_1556","rja be a helyszint ide");
        	hash.put("emea118_1557","c5");
        	hash.put("emea118_1556","strzelin");
        	hash.put("emea123_1558","Peugeot");
        	hash.put("emea123_1557","207 SW");
        	hash.put("emea122_1556","Strzelin");
        	hash.put("emea122_1557","307 SW");
        	hash.put("emea122_1558","Peugeot");
        	hash.put("emea121_1558","pegeout");
        	hash.put("emea121_1557","307");
        	hash.put("emea121_1556","Krzepice");
        	hash.put("emea129_1556","LLEBURGAZ-AYE ISLAH OFS");
        	hash.put("emea131_1556","rja be a helyszint ide");
        	hash.put("emea132_1556","rja be a helyszint ide");
        	hash.put("emea137_1556","IASI");
        	hash.put("emea144_1556","Lleburgaz SF Breeding Station");
        	hash.put("emea145_1557","Ranger");
        	hash.put("emea145_1556","Izmir");
        	hash.put("emea152_1556","Arad");
        	hash.put("emea157_1556","Trnava");
        	hash.put("emea159_1556","Craiova");
        	hash.put("emea160_1556","rja be a helyszint ide");
        	hash.put("emea163_1556","Bulgaria, Shumen");
        	hash.put("emea173_1556","Tulcea");
        	hash.put("emea174_1556","IASI");
        	hash.put("emea176_1556","trakya bolges");
        	hash.put("emea176_1557","astra");
        	hash.put("emea178_1556","iasi");
        	hash.put("emea179_1556","IASI");
        	hash.put("emea180_1556","Galati");
        	hash.put("emea181_1556","Braila");
        	hash.put("emea182_1557","Ranger");
        	hash.put("emea182_1556","Buraya tesis yaznz");
        	hash.put("emea185_1556","Vchodn Slovensko");
        	hash.put("emea199_1558","NISSAN");
        	hash.put("emea199_1557","NAVARA");
        	hash.put("emea201_1556","Vidk-Budapest");
        	hash.put("emea202_1556","barlad");
        	hash.put("emea204_1556","Szkesfehrvr");
        	hash.put("emea208_1556","");
        	hash.put("emea210_1557","SORENTO");
        	hash.put("emea210_1558","KIA");
        	hash.put("emea215_1557","Land Cruiser");
        	hash.put("emea231_1556","Buzau");
        	hash.put("emea233_1556","Prahova");
        	hash.put("emea234_1557","2.0I");
        	hash.put("emea235_1557","FREELANDER 2");
        	hash.put("emea235_1556","BRAILA");
        	hash.put("emea238_1556","Brezova pod Bradlom");
        	hash.put("emea238_1557","1.6 Ford Focus benzn");
        	hash.put("emea240_1556","Fejr megye");
        	hash.put("emea242_1556","Slobozia");
        	hash.put("emea243_1556","Escriba ciudad aqu");
        	hash.put("emea244_1557","Almera");
        	hash.put("emea244_1556","Adana Ofis");
        	hash.put("emea245_1556","IASI");
        	hash.put("emea245_1557","Dacia Logan");
        	hash.put("emea246_1556","Hajd-Bihar megye");
        	hash.put("emea249_1556","Pontevico");
        	hash.put("emea261_1556","Zaragoza");
        	hash.put("emea273_1556","borken");
        	hash.put("emea281_1556","  ");
        	hash.put("emea292_1557","Pathfinder");
        	hash.put("emea295_1556","Portugal");
        	hash.put("emea296_1557","Qashqai");
        	hash.put("emea296_1556","country-side");
        	hash.put("emea299_1556","Zapadne Slovensko");
        	hash.put("emea300_1556","BURGOS");
        	hash.put("emea303_1556","Escriba ciudad aqu");
        	hash.put("emea309_1556","Podaj nazw zakadu");
        	hash.put("emea310_1556","az egsz orszg");
        	hash.put("emea316_1556","Gttingen");
        	hash.put("emea320_1556","Szatymaz");
        	hash.put("emea319_1557","PATHFINDER");
        	hash.put("emea324_1556","Marbach");
        	hash.put("emea327_1556","rja be a helyszint ide");
        	hash.put("emea326_1556","Perisoru");
        	hash.put("emea329_1556","Deva");
        	hash.put("emea328_1556","Buraya tesis yaznz");
        	hash.put("emea331_1557","astra");
        	hash.put("emea331_1556","Buraya tesis yaznz");
        	hash.put("emea332_1556","Borken");
        	hash.put("emea335_1556","Borken");
        	hash.put("emea333_1556","Borken");
        	hash.put("emea338_1556","Anvers (Belgique)");
        	hash.put("emea340_1556","Borken");
        	hash.put("emea342_1556","Pontevico");
        	hash.put("emea343_1556","Adana");
        	hash.put("emea344_1557","Touran 2,0 TDI");
        	hash.put("emea345_1556","");
        	hash.put("emea346_1556","Tolna megye");
        	hash.put("emea348_1556","Pontevico");
        	hash.put("emea351_1556","Wrocaw");
        	hash.put("emea351_1557","C5");
        	hash.put("emea353_1556","Szabolcs-Szatmr-Bereg megye");
        	hash.put("emea354_1556","Schwarmstedt");
        	hash.put("emea356_1556","Gneydou Anadolu Blgesi");
        	hash.put("emea356_1557","Astra G 1.6");
        	hash.put("emea361_1556","Monsanto");
        	hash.put("emea362_1556","A Corua");
        	hash.put("emea365_1556","arad");
        	hash.put("emea366_1557","Ranger");
        	hash.put("emea366_1556","Magyarorszg");
        	hash.put("emea368_1556","Borken");
        	hash.put("emea368_1557","Passat Variant - 2.5 TDI Syncro");
        	hash.put("emea370_1556","Biozna 4");
        	hash.put("emea370_1557","Qashqai");
        	hash.put("emea373_1556","Braila");
        	hash.put("emea378_1557","407 SW");
        	hash.put("emea378_1558","peougeut");
        	hash.put("emea381_1556","Pontevico");
        	hash.put("emea380_1556","Inserire localit qui");
        	hash.put("emea383_1556","BYDGOSZCZ");
        	hash.put("emea384_1556","Podaj nazw zakadu");
        	hash.put("emea389_1556","IASI");
        	hash.put("emea391_1556","nord-ovest");
        	hash.put("emea392_1557","Golf Plus 1.9 TDI (105 cv)");
        	hash.put("emea392_1556","Valladolid");
        	hash.put("emea393_1556","");
        	hash.put("emea394_1557","pathfinder");
        	hash.put("emea399_1556","Nitra");
        	hash.put("emea400_1556","rja be a helyszint ide");
        	hash.put("emea401_1557","Avensis 2.2D");
        	hash.put("emea404_1556","Huesca");
        	hash.put("emea406_1556","tekirda");
        	hash.put("emea406_1557","ranger");
        	hash.put("emea408_1556","Gusow");
        	hash.put("emea409_1556","Somogy megye");
        	hash.put("emea411_1556","Inserire localit qui");
        	hash.put("emea414_1558","Volvo");
        	hash.put("emea414_1557","v70");
        	hash.put("emea417_1556","Buraya tesis yaznz");
        	hash.put("emea420_1556","Zaragoza");
        	hash.put("emea421_1556","Zagreb, Croatia");
        	hash.put("emea429_1556","Carei");
        	hash.put("emea432_1556","Friuli Venezia Giulia");
        	hash.put("emea438_1556","Morges CH");
        	hash.put("emea442_1556","Mainz");
        	hash.put("emea437_1556","Bahrdorf");
        	hash.put("emea437_1557","Golf Plus 2.0 TDI");
        	hash.put("emea446_1556","Wunstorf");
        	hash.put("emea450_1556","76831 Birkweiler");
        	hash.put("emea449_1556","Loxstedt");
        	hash.put("emea451_1556","Portugal Centro / Sul");
        	hash.put("emea452_1556","Peterborough");
        	hash.put("emea456_1556","Zagreb, Croatia");
        	hash.put("emea463_1556","Schwarmstedt");
        	hash.put("emea465_1557","2 l TDI");
        	hash.put("emea465_1556","Osnabrck");
        	hash.put("emea466_1556","Jterbog");
        	hash.put("emea467_1556","Ludwigshafen");
        	hash.put("emea474_1556","Berlin");
        	hash.put("emea473_1556","Neustadt");
        	hash.put("emea472_1557","Transporter");
        	hash.put("emea472_1556","borken");
        	hash.put("emea477_1556","Gttingen ");
        	hash.put("emea480_1556","Traventhal");
        	hash.put("emea486_1556","Cordoba");
        	hash.put("emea487_1556","Langendorf");
        	hash.put("emea490_1556","Eschwege");
        	hash.put("emea507_1556","Praha");
        	hash.put("emea508_1557","MAZDA 6");
        	hash.put("emea508_1558","Zapite zde vbr");
        	hash.put("emea508_1556","Prague");
        	hash.put("emea509_1556","Toledo");
        	hash.put("emea510_1556","Zde napite vai lokalitu");
        	hash.put("emea514_1556","Praha");
        	hash.put("emea518_1556","trakya bolges");
        	hash.put("emea518_1557","astra");
        	hash.put("emea522_1556","Rychnov nad Knnou");
        	hash.put("emea523_1556","Krmend");
        	hash.put("emea527_1556","Sedlany");
        	hash.put("emea528_1556","Kraj Vysoina");
        	hash.put("emea531_1556","esk Budjovice");
        	hash.put("emea532_1556","Svitavy");
        	hash.put("emea534_1556","Mlad Boleslav");
        	hash.put("emea537_1556","Kaposvr");
        	hash.put("emea536_1556","Bulgaria");
        	hash.put("emea538_1556","Bulgaria");
        	hash.put("emea541_1556","Jin echy");
        	hash.put("emea547_1556","8 megye s Budapest");
        	hash.put("emea548_1556","Somogy megye");
        	hash.put("emea549_1556","Stedoesk kraj");
        	hash.put("emea552_1556","Metz");
        	hash.put("emea555_1556","okres Pelhimov, Beneov, Jindichv Hradec");
        	hash.put("emea556_1556","Pcs");
        	hash.put("emea557_1556","Gielow");
        	hash.put("emea558_1556","Zde napite vai lokalitu");
        	hash.put("emea559_1556","giurgiu");
        	hash.put("emea560_1556","Zapadne Slovensko");
        	hash.put("emea564_1556","Arad");
        	hash.put("emea566_1556","Kosice");
        	hash.put("emea567_1557","Suzuki Grand Vitara 2.0");
        	hash.put("emea567_1556","");
        	hash.put("emea568_1556","Severn Morava,Slezko");
        	hash.put("emea569_1556","");
        	hash.put("emea571_1556","Napajedla");
        	hash.put("emea573_1556","Shumen, Bulgaria");
        	hash.put("emea577_1556","Szatymaz");
        	hash.put("emea586_1556","Mnchen");
        	hash.put("emea587_1556","TRAKYA BOLGESI");
        	hash.put("emea587_1557","ASTRA");
        	hash.put("emea593_1556","Tossiat");
        	hash.put("emea592_1556","Saerbeck");
        	hash.put("emea595_1556","Stockholm Sweden");
        	hash.put("emea595_1557","V70");
        	hash.put("emea595_1558","Volvo");
        	hash.put("emea596_1556","Vsters/Stockholm");
        	hash.put("emea599_1556","Denmark");
        	hash.put("emea600_1556","Denmark, Sor");
        	hash.put("emea600_1557","6");
        	hash.put("emea600_1558","Mazda");
        	hash.put("emea602_1556","Roman");
        	hash.put("emea44_1643","intake center and seed productions");
        	hash.put("emea44_1645","vhicule de fonction");
        	hash.put("emea44_1647","bon vhicule pour notre usage");
        	hash.put("emea47_1643","Production Research");
        	hash.put("emea47_1647","Az autot csak ideiglenesen bereljuk mig az uj Xtrail meg nem erkezik");
        	hash.put("emea51_1647","ASP install en option pour se sortir de chemins boueux");
        	hash.put("emea56_1644","CAROSERIE AC BREAK");
        	hash.put("emea59_1552","NON");
        	hash.put("emea59_1643","NON");
        	hash.put("emea59_1644","NON");
        	hash.put("emea59_1645","NON");
        	hash.put("emea59_1646","NON");
        	hash.put("emea59_1647","NON");
        	hash.put("emea60_1643","vehicule  de location");
        	hash.put("emea60_1644","2 portes");
        	hash.put("emea61_1643","Hetente 1-2x Budapest, a ht tbbi napjn Vidk: Termelk, Vetmagzemek, Labor, Raktrak stb.");
        	hash.put("emea61_1645","Vatmagminta szllts kb. hetente egyszer.");
        	hash.put("emea61_1647","Az X-trail nagy csomagtere nagyon hasznos a vetmagmintk, mintz felszerels, vetmag stb szlltsra.");
        	hash.put("emea68_1647","ok sk ehirler aras yollarda seyahat ediyorum. Aracmn uzun yollarda gvenli ve konforlu olmasn tercih ediyorum.");
        	hash.put("emea69_1643","Poste bas  Trbes, mais responsable France");
        	hash.put("emea82_1646","4500 km en forte priode d'acivit");
        	hash.put("emea86_1552","A kocsink jl felszerelt s hatrozottan knyelmesebb az eldhz kpest. A magam rszrl ersebb motorral (1,8 diesel), s tempomattal tudnm mg kiegszteni.");
        	hash.put("emea88_1552","Szvesen vlasztanm a krnyezetbart, hibrid zemels autt. ");
        	hash.put("emea88_1643","rtkests & Large seed Technical Development ");
        	hash.put("emea88_1645","Ksrleti vetmag szllts");
        	hash.put("emea91_1552","Hibrid (benzin+elektromos) -szvesen vlasztanm a krnyezetbart megoldst.Tempomat (ACC) -rendkvl hasznosnak tartom a sebessgkorltozs betartsa vgett.");
        	hash.put("emea93_1647","Subaru Forester 2008-2009)TD2,5 will be much better for Ukraine");
        	hash.put("emea96_1552","ok sk yk tamadm iin arazide kullanlabileceim kasasz, yk tayacam zaman araca rmork takmak suretiyle kullanabileceim bir ara daha uygun olacaktr");
        	hash.put("emea96_1645","Kullanmda yk tamaktan ziyade aracn yerden ykseklii ve eker zellii daha nemli");
        	hash.put("emea96_1647","Arata gvenlik zelliklerinden, n sis farlar, elektronik denge sistemi (ESP) ile yan ve perde hava yastklar yok");
        	hash.put("emea118_1552","Potrzebny jest samochd mogcy porusza si w terenie (4x4) wysoki przewit,oraz komfortowy do jazdy po normalnych nawierzchniach asfaltowych. Jeeli spdzamy 80-90 tys. km rocznie w samochodzie jest to nasz drugi dom.  ");
        	hash.put("emea118_1644","samochd tymczasowy do czasu ustalenia polityki samochodowej dla hodowli  ");
        	hash.put("emea118_1645","transport 5 osb, oraz jazda po polach");
        	hash.put("emea118_1646","rocznie 80-90 tys km");
        	hash.put("emea118_1647","samochd dobry na dobre drogi i autostrady kompletnie nie przydatny na drogi gruntowe (niski przewit)");
        	hash.put("emea123_1643","CDD");
        	hash.put("emea122_1552","Samochd dotychczasowy ma zamay przewit pod pojazdem.");
        	hash.put("emea121_1552","Niezb&#281;dny samochd o wy&#380;szym prze&#347;wicie pod pojazdem umo&#380;liwiaj&#261;cy poruszanie si&#281; po polnych drogach");
        	hash.put("emea125_1552","L'AUTOVETTURA IDEALE DOVREBBE AVERE ANCHE UN POSTO DI GUIDA RIALZATO TIPO Croma - Citroen c5");
        	hash.put("emea125_1645","l'uso principale  per lavoro 90 % ");
        	hash.put("emea125_1647","SAREBBE NECESSARIO AVERE PIU' POTENZA PER CORRERE MENO RISCHI NELLE MANOVRE DI SORPASSO");
        	hash.put("emea127_1552","pas forcement un 4x4 mais une garde au sol suffusante et un volume important  l'interieur");
        	hash.put("emea127_1647","vehicule non adapt a l'usage chemins et routes degrades ( garde au sol)");
        	hash.put("emea126_1645","Transport de semences de base traites");
        	hash.put("emea126_1646","Kilomtrage concentr durant la priode de culture (avril  octobre)");
        	hash.put("emea126_1647","Vhicule trop bas (surtout lors du transport de charges), risque de dgts des bas de caisse.Risque d'enlisement dans les chemins boueux, en bordure de champs");
        	hash.put("emea137_1647","Tractiune integrala permanent(3.8)");
        	hash.put("emea140_1552","Volkswagen tombe visiblement moins en panne de Renault, ce qui est,  mon sens, un moyen d'viter des pertes de temps, et donc un gain de travail.");
        	hash.put("emea140_1644","Monospace");
        	hash.put("emea140_1645","Semences transportes dans la voiture une  4 fois dans l'anne, entre Mars et Mai.");
        	hash.put("emea140_1646","Attelage de remorque serait necessaire pour transporter les matires phyto sanitaires et semences traite  l'exterieur du vhicule.");
        	hash.put("emea140_1647","Nouvelle gnration avec radard de recul, afin d'viter les petits accrochages serait plus qu'utile. Anti brouillard rarement necessaire par rapport  mon lieu gographique.");
        	hash.put("emea156_1643","sunflower research");
        	hash.put("emea157_1644","Combi");
        	hash.put("emea159_1552","Pentru sales masina trebui sa  aiba, pachet drumuri dificile si toate elementele de siguranta ABS,ESP,EBV,tractiune integrala.");
        	hash.put("emea159_1647","Tinad cont ca masina pe care o condu este totusi nou dar din punctul meu de vedere este o masina ne sigura ia find varianta ce mai slaba ne avnd  ESP , airbag cortina, airbag-uri laterale.Parerea mea ca sunt indispensabile pentru drumurile din Romania. ");
        	hash.put("emea163_1552","The vehicle which I am driving is great, it is big enough for my work, very comfortable.");
        	hash.put("emea174_1647","Autoturismul este cu tractiune integrala permanent.");
        	hash.put("emea197_1645","Transport de semences traites");
        	hash.put("emea197_1646","Transport de charges : 2 fois par anKilometrage mensuel : dpend le periode");
        	hash.put("emea204_1643","Terleti munka");
        	hash.put("emea210_1643","Departamento Investigacion de Produccion");
        	hash.put("emea210_1646","Distancia recorrida con remolque hasta 300 km");
        	hash.put("emea210_1647","El equipamiento de seguridad del vehculo actual no es completo");
        	hash.put("emea211_1552","Pas forcement 4X4 mais type 4X4");
        	hash.put("emea215_1644","4x4");
        	hash.put("emea215_1645","Transporte de funcionario en periodo de cultivo.");
        	hash.put("emea215_1646","Hago mas kilometros durante todo el periodo de cultivo (primavera, verano), con las visitas de campo.");
        	hash.put("emea234_1552","  ,      ,   .");
        	hash.put("emea239_1552","NECESITO UN COCHE SUV, CON COMPORTAMIENTO DE TODO TERRENO Y BUENA ESTABILIDAD EN CARRETERA,CON SUSPENSION SUAVE,CONSUMO BAJO Y ALTA POTENCIA(140 CV)Y NO MUY CARO,ESTE COCHE SE LLAMA HYUNDAI  SANTA FE");
        	hash.put("emea239_1643","conductor que hace  70000 km ao, 50% caminos de campo con baches y zonas de riego y 50 % carretera,autopistas.Problema de dolores de espalda y de cervicales.");
        	hash.put("emea239_1644","vehiculo muy bajo para los caminos,de suspension dirsima,que me ocasiones dolores de espalda y llegar muy cansado a mi destino. y con escaso motor (105 CV)");
        	hash.put("emea239_1647","COCHE CON POCO MOTOR Y EN DIAS DE VIENTO INESTABLE");
        	hash.put("emea246_1645","A ksrleti ven nap.magok kiszlltsa, ksrletekhez kark, tblk. Adott idszakban minden nap.");
        	hash.put("emea256_1645","Transport samanta forme parentale.;Transport probe oficiale din loturi semincere.;;;transport probe interne din loturi interne de samanta.");
        	hash.put("emea260_1552","NECESITO UN VEHICULO 4x4 QUE TENGA UNA AMORTIGUACION BUENA PARA CAMINOS Y CARRETERAS (TODO TERRENO O TODO CAMINO).BUEN MALETERO.;;;ALTURA SUFICIENTE AL SUELO PARA CIRCULAR POR CAMINOS Y CARRETERAS EN MAL ESTADO.");
        	hash.put("emea260_1643","TECNICO DE PRODUCCION CAMPO ");
        	hash.put("emea260_1644","VEHICULO MUY BAJO PARA CIRCULAR POR CARRETERAS MALAS Y CAMINOS.POCA FUNCIONALIDAD EN EL CAMPO EN CAMINOS DE TIERRA, ARENA Y BARRO.;AMORTIGUACIN MUY DURA EN CARRETERA Y CAMINOS.");
        	hash.put("emea260_1645","COCHE  NECESARIO PARA USO FAMILIAR.  TAMBIEN PARA TRANSPORTE DE FUNCIONARIOS PARA VISITA A CAMPOS.");
        	hash.put("emea260_1646","MEDIA DE KM AUNQUE EN CAMPAA HAY MESES CON KILOMETRAJES MAS ALTOS.");
        	hash.put("emea260_1647","VEHICULO POCO APROPIADO PARA LAS NECESIDADES DE 50% DE CAMINOS Y 50% DE CARRETERAS. ES MUY BAJO PARA LOS CAMINOS Y ES MUY DURO PARA LA CARRETERA (AMORTIGUACION NO APROPIADA).");
        	hash.put("emea266_1552","Renault scnic adapt, mais voiture basse (chemins de camapganes pas toujours accessibles)");
        	hash.put("emea266_1643","Fieldman");
        	hash.put("emea273_1552","4x4 is a safety equipment on wet, slippery roads");
        	hash.put("emea274_1647","Voiture neuve et rcente mais pas vraiment pratique pour accder  des chemins chaotiques de certaines parcelles. Scurit : manque juste l'ACC et surtout le Bluetooth.");
        	hash.put("emea275_1552","Interesante los modelos Todocaminos con una altura de chasis buena para circular por caminos y traccin a las 4 ruedas.");
        	hash.put("emea275_1647","El vehculo se vuelve ms inseguro cuando se tiene que mover con un remolque pesador (weight wagon)");
        	hash.put("emea280_1552","De los actuales que conozco el mas idonio es el citoen C5 break, que de seria trae el sistema de graduacion de altura para caminos, volviendo ala altura ideal en carretera y autopistas");
        	hash.put("emea280_1644","Veiculo muy bajo y muy rigido de amortiguacin");
        	hash.put("emea280_1647","COn el remolque tiene poca estabilidad, aun sin su carga maxima reglamentaria");
        	hash.put("emea292_1552","Debido a la carga de material no autorizado debemos llevar un coche espaciaso donde poder guardar o dejar semilla en las distintas localidades que pernoctamos.Caminos sinuosos y campo es imprescindible el 4WD.");
        	hash.put("emea292_1643","Field Frial Specialist");
        	hash.put("emea292_1644","perfecto por su espacio");
        	hash.put("emea299_1644","Kombi");
        	hash.put("emea303_1552","El vehiculo que tengo es adecuado para mis tareas, no obstante creo que es conveniente tener seguro a todo riesgo.");
        	hash.put("emea303_1644","El vehiculo es suficiente para mi trabajo");
        	hash.put("emea303_1645","El vehiculo solo lo usopara el trabajo. Y en vacaciones para uso personal");
        	hash.put("emea303_1646","El nmero de Kilometros mensuales depende de la zona de trabajo, en ocasiones se superan los 4500 Km / mes");
        	hash.put("emea303_1647","El trabajo del vehiculo no exige poner camioneta");
        	hash.put("emea307_1643","I'm a french having an assignment in Italy");
        	hash.put("emea307_1645","Utilisation du vehicule pour aller au bureau + dplacement prtofessionnel");
        	hash.put("emea307_1646","ne pas tenir compte de ma rponse sur le volume de charge=> je n'en sais absolument rien.");
        	hash.put("emea309_1645","Scale pads system transported in harvest time, seeds for trials transported 2 times a year.");
        	hash.put("emea310_1643","Kzti s szntfldi munka egytt");
        	hash.put("emea314_1552","Spare tire");
        	hash.put("emea314_1647","As a ATDM I don't need 4WD, since most of my transport is quite different from normale TDRs.");
        	hash.put("emea316_1645","Der Wagen ist nicht mit einer Kupplung ausgestattet, da dies damals nicht genehmigt worden ist.");
        	hash.put("emea319_1645","Herramientas para siembra de ensayos");
        	hash.put("emea324_1552","SUV (VW Tiguan) auch ok");
        	hash.put("emea324_1646","Zuladung ist incl. Fahrer nur 350 kg!!!!!!!!!!!");
        	hash.put("emea324_1647","Auf der Autobahn excellent! Fuer Familie oder technische Zwecke 100% untauglich (Zuladung in kg, Bodenfreiheit, Anhaengelast, Kofferraumgroesse).");
        	hash.put("emea327_1644","4x4-es hajtm");
        	hash.put("emea327_1647","kuplung besett, elektronika elszllt");
        	hash.put("emea328_1552","Kiralama irketi lastik deiimlerinde marka ve model konusunda seme hakk vermiyor.");
        	hash.put("emea331_1552","oto lastik seiminde daha titiz olunmal");
        	hash.put("emea338_1643","Je suis en Belgique base a Anvers, mais La Belgique n'est pas dans la liste.");
        	hash.put("emea340_1644","Fahre seit Januar 09 deutlich mehr km es werden ca 45 000 im Jahr werden");
        	hash.put("emea340_1645","Der Transport von Pflanzenschutzmittel, das ziehen von Anhngern ist sehr stark sesional scwankend von einmal in sechs Monaten bis 20 Tage im Monat ");
        	hash.put("emea340_1647","Ein Fahrzeug mit schlechtwege Fahrwerk ist ein wichtiges Aussatungsmekmal ");
        	hash.put("emea351_1552","Ze wzgldu na charakter wykonywanej pracy nizbdny jest mi samochd dostosowany do do jazdy terenie jak i po drogach publicznych");
        	hash.put("emea351_1644","auto zastpcze do czsu ustalenia nowej polityki samochodowej,z napapdem na przedni o.");
        	hash.put("emea351_1647","trudno mporusz si po drogach nieutwardzonych ");
        	hash.put("emea353_1552","Az ignynek bejellt gpjrm nem azt jelenti, hogy nem vagyok elgedett a mostani autmmal, csak nha sajnlom a kocsit arra hasznlni (ksrletek betakartsa szntfldn) amire szksges a munka elvgzse miatt!!!");
        	hash.put("emea353_1643","Simon Krisztin");
        	hash.put("emea354_1647","Feldtauglichkeit des jetzigen Fahrzeugs ist schlecht");
        	hash.put("emea359_1643","Country is Croatia, office in Zagreb. There is not Croatia to select. ");
        	hash.put("emea361_1643","Technical Development Representative");
        	hash.put("emea362_1552","con la nueva poltica de coches, hemos empeorado con respecto a la anterior. Sobre todo en seguridad");
        	hash.put("emea362_1647","el anterior era mejor");
        	hash.put("emea366_1647","Teheraut minsts gpjrm ");
        	hash.put("emea368_1645","Transport of seeds and equipment - Monthly");
        	hash.put("emea368_1647","At 3.8 The Passat Variant Syncro has permanent 4 wheel drive At 3.10 would need more dictance to the ground for in filed use");
        	hash.put("emea373_1552","Nu");
        	hash.put("emea373_1643","Nu");
        	hash.put("emea373_1644","Nu");
        	hash.put("emea373_1645","Nu");
        	hash.put("emea373_1646","Nu");
        	hash.put("emea373_1647","Nu");
        	hash.put("emea380_1647","il veicolo  altamente efficiente per il lavoro da svolgere. Soprattutto il cambio automatico  indispensabile, vista la guida in code, stradine a velocit basse, limita molto lo stress");
        	hash.put("emea383_1552","Samochody w dziale sprzeday powinny gwarantowa bezpieczestwo i uytczno a nowa polityka samochodowa w znacznym stopniu pogarsza te dwa aspekty");
        	hash.put("emea383_1644","TOYOTA Avensis 2.4D-4D 150 KM model PRESTIGE");
        	hash.put("emea383_1645","Transport materiaw reklamowych codziennie, ");
        	hash.put("emea383_1646","Uytkowanie w trudnych warunkach ");
        	hash.put("emea383_1647","obecnie uytkowane madele w nowej polityce zostan wymienione na modele mniejsze ktre w znaczny sposb pogorsz bezpieczestwo uytkowania oraz spowoduje mniejsz wydajno pracy ");
        	hash.put("emea384_1552","Obnienie standardu/parametrw  samochodu w pracy, w ktrej spdza si bezmaa 90 % czasu jest niemoralna.Jest to podstawowy warsztat pracy wobec czego musi by bezpieczny i wygodny.");
        	hash.put("emea384_1645","Transport materiaw reklamowych i wystawowych codziennie i w miar potrzeb.");
        	hash.put("emea384_1646","Zgodnie z now polityk samochodow firmynowe samochody bd mniejsze i o zdecydowanie gorszych parametrach co znacznie pogorszy bezpieczestwo i uzytkowo ");
        	hash.put("emea384_1647","Standard obecnie uytkowanego pojazdu pozwala mi czu sie bezpiecznie w pracy");
        	hash.put("emea385_1645","Napi munkavgzs nincs felajnlva.");
        	hash.put("emea386_1552","Se deberia autorizar la compra de coches con sistema integrado de telefono");
        	hash.put("emea386_1647","Ha bajado el nivel de seguridad con la nueva Politica de coches");
        	hash.put("emea393_1552","      Mitsubishi L-200");
        	hash.put("emea393_1645","   (,        )");
        	hash.put("emea393_1646"," -   - 5500(6-7 ) ;3000  .");
        	hash.put("emea394_1644","EXCELENTE VEHICULO PARA NUESTRO TRABAJO, GRAN CAPACIDAD");
        	hash.put("emea395_1552","Tres satisfait du vehicule actuel. ");
        	hash.put("emea401_1552","Pojazd ktry w tej chwili eksploatuj bdzie wymieniony niebawem na mniej funkcionalny, sabiej wyposaony pod ktem bezpieczestwa, sabszy silnik, niszy komfort");
        	hash.put("emea401_1643","Warszawa jest tylko lokalizacj biura");
        	hash.put("emea401_1645","ze wzgldu na bardzo czste pokazy polowe transportujemy na pola cae wyposaenie targowe typu namiot wystawowy, tablice, maszty itp...");
        	hash.put("emea401_1646","niestety jako drg po ktrych sie poruszam znacznie odbiega od standartw, brak autostrad");
        	hash.put("emea401_1647","obecnie uytkowany pojazd nie posiada trybu napdu na cztery koa niemniej ze wzgldu na jako drg oraz czeste wizyty na polach byoby to bardzo wskazane");
        	hash.put("emea417_1552","Uzun yolculukta va kt yol artlarnda araba kullanldndan dolay arabann subvasyonu yksek olmaldr.");
        	hash.put("emea417_1646","5 yllk ortalamya gre bu kadar km. yaplyor.");
        	hash.put("emea420_1552","Los Bluetooth siempre tendran que se integrados no accesorios (parrot).");
        	hash.put("emea421_1643","I am als o responsable for registration of PPP, trials, marketing");
        	hash.put("emea432_1645","Trasporto sementi una volta al mese");
        	hash.put("emea432_1647","Soddisfatto sulle strade asfaltate, non soddisfatto su strade di campagna");
        	hash.put("emea438_1552","Navigation   Erdgas: umweltschonend ");
        	hash.put("emea438_1643","Hauptschlich in CH/ A und D , ist aber nicht verfgbar ");
        	hash.put("emea438_1644","Bluemotion 1.9 TDI");
        	hash.put("emea438_1645","unter Pflanzenschutzmittel verstehe nur gebeiztes Saatgut (Gemse)");
        	hash.put("emea435_1643","As Regulatory Affairs was not part of the scroll down menue I choosed TecDev. Hope this is OK?");
        	hash.put("emea435_1645","No tow coupling so far, thus no trailer transport");
        	hash.put("emea435_1646","Annual leasing rate: 45.000 km");
        	hash.put("emea437_1552","Mittelarmlehne ");
        	hash.put("emea437_1643","WOSR");
        	hash.put("emea437_1644","4-trig");
        	hash.put("emea437_1645","Hauptschliche Nutzung fr Besichtigungen der Versuchsflchen");
        	hash.put("emea437_1646","Okt08-Mai09");
        	hash.put("emea449_1552","Wichtig ist mir fr die Zukunft ein PKW, mit hochwertiger Ausstattung, da wir sehr viel fahren. Besonders wichtig ist der Sitzkomfort, um die krperliche Gesundheit (rcken, bandscheibe) aufrechtzuerhalten.");
        	hash.put("emea449_1645","Leider keine Anhngekupplung, daher mssen Saatgut, Pflanzenschutzmittel im Auto transportiert werden.");
        	hash.put("emea449_1647","Die Ausstattung im Bereich von gesundheitsschonenden Sitzen mit Lendenwirbelsttze ist leider nicht vorhanden. Sitze sind nach Fahrtzeit von 74.000 km schon sehr weich. Dieses sollte unbedingt verbessert werden.");
        	hash.put("emea451_1552","A possibilidade de ter uma break com 2.0 TDI  importante pois para transporte de material, para dias de campo  muito util ter espao de transporte.");
        	hash.put("emea451_1643","Area Sales manager Portugal Centro / Sul");
        	hash.put("emea451_1644"," um modelo VW passat carro (no break)");
        	hash.put("emea451_1647","Excelente veculo. por vezes, no campo  um veculo muito baixo e com pouca adaptabilidade a trabalhos de campo.");
        	hash.put("emea452_1647","Lack of 4WD on current vehicle is unsatisfactory given that I am frequently off road.");
        	hash.put("emea456_1552","The current car (Ford Mondeo Wagon 2.0 TDCI (130 PS)) is very suitabble car for my everyday business tasks.");
        	hash.put("emea456_1646","Exact load volume is 886 kg");
        	hash.put("emea461_1643","CA / Public Affairs");
        	hash.put("emea466_1552","Navigationssystem !!!!!!");
        	hash.put("emea466_1645","nur Private Nutzung am WE gemeint");
        	hash.put("emea467_1552","Fahrzeug muss auf Autobahn und Feldwegen funktionieren SUV oder Kombi mit Allradantrieb");
        	hash.put("emea467_1645","Transport von Jungpflanzen (ca. 1 mal pro Woche) Transport von Erntegut (Zwiebeln, Gurken...) (ca. 1 mal im Monat)");
        	hash.put("emea467_1647","Kompromiss zwischen Langstrecke und Feldwegen; Eingeschrnkte Gelndegngigkeit auf Feldwegen/schlechten Strassen");
        	hash.put("emea480_1552","Anhngekupplung ist notwendig um chemisch gebeiztes Saatgut und Feldtagszubehr effizient ordnungsgem zu transportieren.");
        	hash.put("emea480_1645","zu 4250 km / Monat fr Firmenzwecke, dh. Kundenbesuche in der Sales Region. Transport von chemisch behandeltem Saatgut im Ladebereich. ");
        	hash.put("emea487_1552","Anhngerkupplung nicht fr einen Wohnwagen, sondern fr einen Anhger zum Saatguttransport.");
        	hash.put("emea487_1645","Insektizid gebeiztes Saatgut mu im Innenraum transportiert werden,Teils in Kisten teils frei im Kofferraum, weil keine Anhngerkupplung vorhanden ist.");
        	hash.put("emea490_1647","Anhgekupplung wnschenswert!");
        	hash.put("emea491_1643","Was fr Ort ist gemeint? was soll man darunter verstehen? Wenn ich einen anderen Ort eingebe, streikt das Programm");
        	hash.put("emea491_1644","Dieses programm ist schlecht. Es akzeptiert nicht das von mir eingeklickte Modell");
        	hash.put("emea491_1645","Transport von Saatgu Transport von Prsentationsmaterial(Leinwand; Prospektstnder Prospekte Givs...)");
        	hash.put("emea491_1646","z.T Feldwege; 50 % Autobahn");
        	hash.put("emea491_1647","ergonomische Sitzgestaltung erforderlich");
        	hash.put("emea503_1552","nejvhodnj pro moji funkci je typ vozu COMBI !!! Nutn peprava objemnch a tkch pedmt.");
        	hash.put("emea507_1643","Commercial Acceptance");
        	hash.put("emea508_1643","Lead, Regulatory Affairs, EMEA/East");
        	hash.put("emea508_1645","Car is used mainly for personal transport of Marin Velcev to airports and meetings");
        	hash.put("emea510_1552","opitmal combi karavan");
        	hash.put("emea523_1552","A jelenlegi aut nem 4x4 (3.8-as krds)");
        	hash.put("emea524_1552","mon vehicule break precedent etait plus polyvalent");
        	hash.put("emea524_1644","Voiture golf en remplassement,d'un break Passat,bonne tenue de route, habitacle relativement spacieux et places arrieres assez confortable. Probleme absence de coffre.");
        	hash.put("emea524_1645","transport de semences pour livraison aux multiplicateurs, et transport de divers petits materiel et outillage( clotures electriques,binettes...)");
        	hash.put("emea524_1646","Avec des pics suivant activite");
        	hash.put("emea524_1647","Manque de hauteur du vehicule,Manque de place pour nos equipements lors de deplacements a plusieurs");
        	hash.put("emea528_1552","Uvtal bych silnj vkon motoru.");
        	hash.put("emea528_1647","Nedostaten vkon motoru- akcelerace pi pedjdn a een koliznch situac!");
        	hash.put("emea531_1644","Combi");
        	hash.put("emea532_1643","Prodej konenm zkaznkm, poradenstv na farmch, pohyb v ternu");
        	hash.put("emea532_1644","Combi");
        	hash.put("emea534_1644","kombi");
        	hash.put("emea534_1646","Nevm jak mm max. zaten");
        	hash.put("emea536_1643","I'm hired by Monsanto Romania, but working as sales manager in Bulgaria");
        	hash.put("emea538_1643","Hired by Monsanto Romania, but working like sales manager in Bulgaria");
        	hash.put("emea549_1643","obchodn zstupce");
        	hash.put("emea550_1552","Por el resultado y caracteristicas de este coche yo lo incluiria en las opciones a elegir por los gerentes de ventas");
        	hash.put("emea550_1643","65.000 Km anuales");
        	hash.put("emea550_1644","Coche magnifico para el trabajo de ventas: fiable, seguro, comodo, consumo economico, y se puede circular incluso por pistas de tierra sin problemas");
        	hash.put("emea550_1645","Potencia suficiente para remolcar los remolques disponibles (menos 750 Kg: vagonetas de pesada de grano y carros de carga)con seguridad");
        	hash.put("emea550_1646","En su mayoria por autovia");
        	hash.put("emea550_1647","El 2 Audi 4 que tengo en Monsanto y si pudiera lo elegiria en la proxima ocasion");
        	hash.put("emea552_1552","4x4 pas indispensable mais fort utile La puissance du vhicule mini 120 cv indispensable pour la traction de remorques souvent lourdes");
        	hash.put("emea552_1644","type = monospace");
        	hash.put("emea555_1643","vytven poptvky po produktech Monsanta");
        	hash.put("emea555_1645","Tan pvs a peprava tanho pvsu s nkladem nepouvm, protoe automobil nem tan zazen,ale bylo;;; by poteba na pepravu vzork osiv,cedul a matril na poln dni.");
        	hash.put("emea555_1647","spokojenost by se zvila, kdyby byl automobil  vybaven tanm zazenm");
        	hash.put("emea560_1644","Combi");
        	hash.put("emea566_1645","preprava materialu na Dni pola");
        	hash.put("emea566_1646","Podla potreby a sezony");
        	hash.put("emea568_1644","KOMBI-montn");
        	hash.put("emea568_1645","Moen vzorky osiva  kukuice, epka");
        	hash.put("emea570_1552","Masinile ar fi de preferat,s-a fie dotate cu scut metalic!");
        	hash.put("emea570_1643","alexandria-tr.magurele-pitesti-videle-zimnicea-rosiori");
        	hash.put("emea570_1645","transport samanta tratata  fungicid-insecticidin perioada lunii matrie - aprilie");
        	hash.put("emea570_1646","Distanta parcursa este in functie de perioada.");
        	hash.put("emea574_1643","Commercial Lead");
        	hash.put("emea583_1643","I'm employed by Monsanto Romania, but work as sales manager in Bulgaria");
        	hash.put("emea592_1643","80% Sales und Sales Management + 20% TD - Arbeit");
        	hash.put("emea592_1645","Transport von Pflanzen und Saatgut. Hauptzweck sind aber Fahrten von Kunde zu Kunde oder zur Firma.");
        	hash.put("emea592_1647","hhere Sitzposition und mehr Bodenfreiheit wre sinnvoll");
        	hash.put("emea595_1643","The car is driven in Sweden. But as it is compulsory to select a country and Sweden is not among the alternatives Poland is chosen. ");
        	hash.put("emea596_1643","Country is NOT Poland but Sweden and site is Vsters / Stockholm ");
        	hash.put("emea601_1643","Regulatory");
        	hash.put("emea601_1645","Conduccin diaria a oficina");
        	hash.put("emea601_1647","Echo de menos el sensor de obstculos o asistencia en el aparcamiento que aumenta la seguridad en los desplazamientos marcha atrs.");
        	hash.put("emea603_1643",":)");

            String sql = "SELECT ua.loginname, ad.*, mo.answer AS multi " +
            		"FROM useranswers ua " +
            		"JOIN answerdata ad ON ad.id = ua.answer " +
            		"LEFT JOIN multioptions mo ON mo.id = ad.id " +
            		"WHERE assesment = "+AssesmentData.MONSANTO_EMEA+
            		" ORDER BY loginname, question";
        	ResultSet set = st.executeQuery(sql);
        	
        	Hashtable values = new Hashtable();
        	while(set.next()) {
        		String login = set.getString("loginname");
        		String question = set.getString("question");
        		if(values.containsKey(login)) {
        			Hashtable answers = (Hashtable)values.get(login);
    				if(set.getString("multi") != null) {
        				answers.put(set.getString("multi"),getValue(hash,login,question,set,st2));
    				}else {
        				answers.put(question,getValue(hash,login,question,set,st2));
    				}
        		}else {
        			Hashtable answers = new Hashtable();
    				if(set.getString("multi") != null) {
        				answers.put(set.getString("multi"),getValue(hash,login,question,set,st2));
    				}else {
        				answers.put(question,getValue(hash,login,question,set,st2));
    				}
        			values.put(login,answers);
        		}
        	}
        	
        	String[] ids = {"1492","1555","1530","1531","1532","1554","1533","1534","1535","1536","6582","6040","6041","6042","6043","6044","6045","6046","6047","6048","6049","6050","6938","6939","6051","6052","6053","6054","6057","6055","6056","6061","6059","6060","6058","6039","6583","6581","6062","1649","6063","1650","6064","1651","6065","1652","6066","1653","6067","1654","6270","1655","1656","1539","6074","6073","6076","6075","1542","1543","1544","6096","6092","6091","6090","7084","7083","7082","6094","6095","6093","1546","1547","1548","1549","1550","6134","6585","6584","6148","6147","6146","6145","6144","6143","6142","6141","6140","6139","6138","6137","6136","6135","6586","6133","6132","6131","6130","6129","6128","6127","6126","7080","7081","1546","1547","1548","1549","1550","6145","6133","6134","6132","6128","6148","6143","6142","6136","6138","6140","6129","6135","6139","6147","6127","6144","6130","6131","6137","6141","6146","6126","6584","6585","6586","7080","7081","1552","1556","1557","1558","1643","1644","1645","1646","1647"};
        	String[] texts = {"1.1 Country","1.2 Site","1.3 Group Function","2.1 Make","2.2 Model","2.3 Year","2.4 Odometer reading","2.5 Category / Type","2.6 Engine","2.7 Fuel","Truck bed cargo hauling","Electronic Stability Control (ESC)","Obstacle Sensor ","Immobiliser","Alloy Wheels","Power Windows","Light Sensor","Fog Lamps","Traction Control System (TR) ","Trailer Hitch & Connectors ","Automatic Transmission","Alarm","Ground clearance > 170mm","Curtain Airbags ","Truck bed Liner","Roll Bar","Air Conditioning","Rain Sensor","Adaptive Cruise Control  (ACC)","Antilock Brake System (ABS)","4WD","Driver Airbag ","Bluetooth Communication System","Central Locking","Parking Assistance","Power-Assisted Steering","Side Airbags ","Front Passenger Airbag","Transport Petrochemicals","Frequency of transport activity (check all that apply)","Transport Personnel","Frequency of transport activity (check all that apply)","Transport Phytosanitary","Frequency of transport activity (check all that apply)","Pulling A Weigh Wagon","Frequency of transport activity (check all that apply)","Personal - Transport Family","Frequency of transport activity (check all that apply)","Transport Chemicals ","Frequency of transport activity (check all that apply)","Pulling A Trailer with Equipment/Seed/Chemicals","Frequency of transport activity (check all that apply)","3.2 Load Volume","3.3 Average distance travelled monthly.","Paved Rural Roads","Highway/Motorway/Expressway","Unpaved/Dirt/Gravel Roads","Poorly Maintained Rural Roads","3.5 Considering time-distance travelled (in percentage), regularity the vehicle is driven on unpaved and/or poorly maintained rural roads","3.6 Importance of having a pickup truck","3.7 Importance of having a 4WD vehicle","In Fog","On Muddy Fields During Winter ","In Fields","Never","On Ice","On Snow","On Muddy Trails","On Winding Roads ","In the Rain ","Pulling A Weigh Wagon","3.9 Satisfaction with current vehicle performance ","3.10 Satisfaction with current vehicle reliability and safety","4.1 Most suitable vehicle class for jour job assignment","4.2 Engine","4.3 Fuel","Traction Control System (TR) ","Truck bed cargo hauling","Front Passenger Airbag","Driver Airbag ","Central Locking","Bluetooth Communication System","Parking Assistance","Adaptive Cruise Control (ACC)","4WD","Antilock Brake System (ABS)","Rain Sensor","Air Conditioning","Roll Bar","Truck Bed Liner","Alarm","Automatic Transmission","Trailer Hitch & Connectors ","Side Airbags ","Fog Lamps","Light Sensor","Power Windows","Alloy Wheels","Immobiliser","Obstacle Sensor ","Electronic Stability Control (ESC)","Power-Assisted Steering","Ground clearance > 170mm","Curtain Airbags ","3.9 Satisfaction with current vehicle performance ","3.10 Satisfaction with current vehicle reliability and safety","4.1 Most suitable vehicle class for jour job assignment","4.2 Engine","4.3 Fuel","Parking Assistance","Fog Lamps","Traction Control System (TR) ","Light Sensor","Obstacle Sensor ","Driver Airbag ","4WD","Antilock Brake System (ABS)","Automatic Transmission","Truck Bed Liner","Air Conditioning","Immobiliser","Trailer Hitch & Connectors ","Roll Bar","Central Locking","Electronic Stability Control (ESC)","Adaptive Cruise Control (ACC)","Alloy Wheels","Power Windows","Alarm","Rain Sensor","Bluetooth Communication System","Power-Assisted Steering","Front Passenger Airbag","Truck bed cargo hauling","Side Airbags ","Ground clearance > 170mm","Curtain Airbags ","5.0 Additional Comments","Other City","Other Model","Other Trade","Additional Comments","Additional Comments","Additional Comments","Additional Comments","Additional Comments"};
        	
        	// loginname:
        	//fos.write(("1.0 Login Name;").getBytes());
        	
        	for(int i = 0; i < ids.length; i++) {
        		fos.write(new String(texts[i]+";").getBytes());
        	}
        	fos.write("\n".getBytes());
        	

        	Enumeration keys = values.keys();
        	while(keys.hasMoreElements()) {
        		String key = (String)keys.nextElement();
        		// loginname:
        		//fos.write(new String(key+";").getBytes());;
        		
    			Hashtable answers = (Hashtable)values.get(key);
            	for(int i = 0; i < ids.length; i++) {
            		if(answers.containsKey(ids[i])) {
            			fos.write(new String(answers.get(ids[i])+";").getBytes());
            		}else {
            			fos.write(new String(" ;").getBytes());
            		}
            	}
            	fos.write("\n".getBytes());
        	}
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}

	private static Object getValue(Hashtable hash,String login,String question, ResultSet set, Statement st) throws Exception {
		if(set.getString("answer") != null) {
        	ResultSet set2 = st.executeQuery("SELECT text FROM generalmessages WHERE language = 'en' AND labelkey IN ('question"+question+".answer"+set.getString("answer")+".text','question"+question+",answer"+set.getString("answer")+".text')");
        	if(set2.next()) {
        		return set2.getString("text");
        	}else {
        		System.out.println("error question"+question+".answer"+set.getString("answer")+".text");        		
        	}
		}else if(set.getString("text") != null) {
			if(hash.containsKey(login+"_"+question)) {
				return hash.get(login+"_"+question);
			}
			return set.getString("text").replace('\n', ' ');
		}else if(set.getString("distance") != null) {
			return set.getString("distance") + " " +set.getString("unit");
		}else if(set.getString("multi") != null) {
			Collection elements = new LinkedList();
        	ResultSet set2 = st.executeQuery("SELECT text FROM generalmessages WHERE language = 'en' AND labelkey = 'question"+question+".answer"+set.getString("multi")+".text'");
        	if(set2.next()) {
        		return set2.getString("text");
        	}
		}
		return "";
	}
	
}
