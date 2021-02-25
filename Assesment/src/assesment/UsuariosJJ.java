package assesment;

import java.io.File;
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

public class UsuariosJJ {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";


	public static void main(String[] args) {
       	String[][] users = {{"aaceved2@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=FGI2uO3mghFwS7OYXzH3a1hiimq42WLtl&id=911","es"},
       			{"aaleite@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=yAqDTYrGOTlKFrXJdL63DSE2wO1jS8Hl3&id=933","pt"},
       			{"jlaro@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=XupIAulb65yPyr34qSWwbf4XbuyhyhcZd&id=909","en"},
       			{"acampo20@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=Y4MfpkOKEuTSRRckw5d4Dh82Mojxa6iXH&id=899","es"},
       			{"acanosa@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=pFX8ZCgLoHf2OeO7neBJfrO4whhU9XNw6&id=924","es"},
       			{"adiaz3@jnjve.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=2tjFZct64PPVu78jDERXzTFEXx6uW3mOt&id=837","es"},
       			{"alevin@itn.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=YCPC3dETlOnJ8hmjMQkf1fuNF24701k5v&id=826","es"},
       			{"amerino1@janmx.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=bSzp9cuBlFG8LwhpXyPBGgdgMITkEWQqX&id=822","es"},
       			{"amontini@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=rAZRfoICEO3uxcPZN9kSeqUTu8hjjyHmy&id=838","es"},
       			{"apinto@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=FpF7f4DTInfWo0oCz6b6BxW16gB0fqPkt&id=855","pt"},
       			{"atelles1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=JoQe3VuF8IcxU6sS1nGAqi2uK1udRDJsD&id=829","pt"},
       			{"atorre@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=7wNdCiAAUrwgkRwtWccyPx03b1m3clUtB&id=849","es"},
       			{"aviegas1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=caJSQP0t1y6VSWzw7ck5Xr6u9cmw7DHMs&id=872","es"},
       			{"aviegas@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=z6mLepVb3R3RdsX1I5AApN2KogzKc0KKH&id=955","pt"},
       			{"bgirldo@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=rP6ICGjnCOS2WQSaTcISeemAxjvpLxMyS&id=965","es"},
       			{"bgonzale@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=bM0mtqUL57nKWmBsBzXkD51jpkJ8GYzI8&id=836","es"},
       			{"cbastid1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=gFUwyK1fdQKRIWfP0fyXgHjs7hygLEzqV&id=885","es"},
       			{"cdanin@medbr.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=WdWgyfoBEYj0dDw4zn2vDAFNn37ojXJ9R&id=876","pt"},
       			{"cesantos@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=dxtCyYFy4KRqMNswCpJ52KJv8PjKqoRdz&id=861","pt"},
       			{"cmorales@jnjve.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=5m2ibssE24TMUvuoDFWdkLB29WyhHk3Jv&id=936","es"},
       			{"cnemocon@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=2RUxO1ypO0QbJsT0pjdZU3poUL8Nj2Gs2&id=930","es"},
       			{"cpiccapi@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=5Oj6RgP6eheBAzPSLvCtoscrVI8LSt7bP&id=929","es"},
       			{"cpuglies@medar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=7g2H0vYDlfQA2wS4u4eMM0L4rciFsaL5F&id=815","es"},
       			{"csilva60@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=pKmTfiAGj2mOMMgFCVp2r5pktEBwZ3fu0&id=857","pt"},
       			{"ctriana@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=VeCTmVmsG8oFOzElXhu4sqx7RlIQiQ0kB&id=900","es"},
       			{"curibe@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=87JSQ74Ski8zx6TS1cnD7ZXKZM5BKxXpX&id=884","es"},
       			{"dianne_canadell@hotmail.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=LJATXzJFooomVUNNpxRGN87wEwqlXGBr0&id=858","es"},
       			{"dmelis@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=nBbeJnD408dwBO0m1eVO1b0eDfv68T5gq&id=906","es"},
       			{"ealmeid2@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=shCMqteVjHb85VIHJKkbLJACqnY7GJ3LJ&id=961","pt"},
       			{"ecabas@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=oYiQXAA4GdhlViMeuTsVt8mTAu7jy6r8X&id=902","es"},
       			{"eescuder@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=Jl83wVUKWBRtht8cHbB6mVeYisfQlnkLR&id=828","es"},
       			{"eflores5@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=CGmfxdzKfLm6NZisAKGfZss18bOzmcsth&id=832","es"},
       			{"egarci10@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=K2ZMz2OYGLFwZ4ZKrRqSZv1MwZewOdHwF&id=931","es"},
       			{"egomes18@conbr.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=JuhVxsT4xnYKd7nu3fAOyQvKf12RiHi6s&id=934","pt"},
       			{"epalumb1@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=KDQjPSluLnoLhr6ycSzIB0DOhfVxTeBrd&id=878","es"},
       			{"evbustam@jnjve.jnj.ve","http://elearning.cepasafedrive.com/loadAssesment.php?key=gN6AlGX0edfhYRCrunbo9IVbDGG4uaGRh&id=935","es"},
       			{"evivas1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=5bALPmqtEpQJQdWPJpQ5ywBEZwMHCRnnw&id=840","es"},
       			{"ezabala@jnjve.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=zVc8UBtrUAljW6W6JWf3dcUtHyzoATC3x&id=854","es"},
       			{"fandrad1@conbr.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=4PeBmYvd6wXZ6MGkcbHNmwGJavlU8mbAt&id=842","pt"},
       			{"farangel@jnjve.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=bVFck8nuGwDkf4ViXSTCToJbHjfcqrth3&id=850","es"},
       			{"fguerra1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=217U11gmverkgNDUoSCXhRH1me4jLm2eg&id=881","pt"},
       			{"fmayo_73@hotmail.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=A0TePxvk6J77qCS7pNrj30uRvK4lEDAuO&id=834","es"},
       			{"fstraval@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=J7ocdQyNfUxENh4vpUrBrzN1qBYLKyKDS&id=821","es"},
       			{"gdibiass@its.com.ar","http://elearning.cepasafedrive.com/loadAssesment.php?key=slqULyiw7VgUM5ZBwqw1wyKw96syihT1s&id=913","es"},
       			{"hjaime@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=HpXG0vS5L8kzp0NGzoNuSLjsspiAlUUqB&id=874","es"},
       			{"hmendoz2@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=rlVdoC4CUHFjRVAvOxvdttlykYBNeuutU&id=896","es"},
       			{"hpadill1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=CUZYIKCLTdYzqrmMNk47twtMRvpPGqHy6&id=921","es"},
       			{"hroye@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=NuZx0rjISJBuQ18kFYyou0ww1Xf7h94t2&id=904","es"},
       			{"jaimekike@hotmail.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=NKpOL1SPghyi1K0KfLZjzs8Gp2BWJYWiG&id=897","es"},
//       			{"javierperez2009","http://elearning.cepasafedrive.com/loadAssesment.php?key=SwFdt548t7HyfylGch4G3qgEsLjX4JyIP&id=887","es"},
       			{"jcguevara_3@hotmail.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=LUj2x36uG6A3Mg8ds0meF2830Z46Fly5h&id=816","es"},
       			{"jcividan@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=wPUqevRIEppRXUNuaOSzpfqv6ffVt7Z76&id=841","pt"},
       			{"jfernand@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=lLtAULTgp0NZRhngWedXuBFzH0OCZfpqP&id=814","es"},
       			{"jgarci21@its.jnj.co","http://elearning.cepasafedrive.com/loadAssesment.php?key=7Ol5SnpZcewfq8tCKYP3QBLVe8JyRVGcE&id=891","es"},
       			{"jgomez9@its.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=fa8xegxeeUvy5B6ZrLJA8iQ8mYyiHXpfb&id=895","es"},
       			{"jmelelli@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=O4DYCBUdY7SfI6DBPViX1ReJqOG84qFub&id=853","es"},
       			{"jogrelugon@cantv.net","http://elearning.cepasafedrive.com/loadAssesment.php?key=0mJerKoQlXlvneAsaiqw5sZ1ih800jmrX&id=845","es"},
       			{"jrodri29@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=KOTmzE6lrEh1GYfmdBNbuowbpy4mBidES&id=966","es"},
       			{"jsole1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=l7hx2vUOxdX5MzjgMRI8vNXJczwERDpqA&id=866","es"},
       			{"jsoto@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=fe0unP4IG6BoVZQFIfEShKIqoNSjvbY3M&id=825","es"},
       			{"lcastel3@jnjve.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=8GkHZznDmzZZyiE91cgpZf1HuDDlijLpC&id=848","es"},
       			{"lervilha@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=c54HQNJI4xTAZxJ4xmUrzRyo4XA7Cnv0W&id=865","pt"},
       			{"lfredes@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=ujeqrwrICKLNMBjzeswGZPO3zD9WvZaEv&id=880","es"},
       			{"ljove@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=xMbJ8GMZOwJIjb0E4rylzp1ixQ2Hi6bP4&id=908","en"},
       			{"lmaria1@conbr.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=Y5zgA0QU3SMKpZropE87ESrg3li48sAQc&id=867","pt"},
       			{"lmendez@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=LtBGIfm5Qb82p0FmcjuEVJb7TKjD75ijO&id=893","es"},
       			{"lriderel@conar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=eaJZOs19hKY0HNCCcwewwI5pYp1wfW1Sh&id=919","es"},
       			{"mabreut@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=usYouiptmBqbGHjMRnebASKsogDtABjZD&id=894","es"},
       			{"maolivei@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=kTCwNueSWkWWBWE7mRhDZOOTlIgMR8dDB&id=856","pt"},
       			{"masuarez@jnjve.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=hCvd0TtpKbbvO9tdkxN6e1egcL5UOVXbg&id=843","es"},
       			{"mavila3@jnjve.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=8QtVIf9cMnuDoipGpk4CmHDm3mx0nhxXS&id=852","es"},
       			{"mbenite1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=BSE6nBmvASUlaGmcDEneqOtyExJMyfeR2&id=920","es"},
       			{"mbrandao@its.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=GIDdsJdxt7IxUizWg4McuEyr1djJ4kFZs&id=923","es"},
       			{"mcampos5@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=cWrGvI1KhbjjFKDZzpP0A7Hlu4b2wQOvl&id=937","es"},
       			{"mforbeti@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=DuqoRPhmYbweoIBlWkVDt8mPU0umUwrFI&id=928","es"},
       			{"mfrugoni@janr.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=rTQtNlqOnldbxOMY8JRnvuwqg3fuRt4dw&id=871","es"},
       			{"mgdeibe@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=RpVUtCikqmcgz2ClmWdGjYTxGXtP3XbMR&id=914","es"},
       			{"mmessuti@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=dHUlvbCjG9rlc8jm2pN52YNKDJ44D3ndk&id=910","es"},
       			{"mpacheco@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=Ml2ss9ODCHJQiqxVYn4fy2B6moi2eY5fQ&id=864","pt"},
       			{"mtravass@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=OeyXANva5K3i7xQVsuGRFzN8MpX0Eh1a9&id=873","pt"},
       			{"muribe@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=udcFbQLB4LgD4QUeIdf0bC6EhuVGQ1CPD&id=883","es"},
       			{"mveland1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=TXmvdvjSoxxhP0fgNVc7fcc1GR2oHJOO6&id=905","es"},
       			{"myanacul@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=SYMfl4oRQ7FW9gxMjG4e6j0OReCXPQfQD&id=917","es"},
       			{"ncgarcia@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=XqP0yAOkudJmL6fDFHbtWBfkEDVtXQYiY&id=847","es"},
       			{"nmoraut@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=5iGUABSk1t0R9gbftXOxNflUjOW18dCJe&id=820","es"},
       			{"nsuarez1@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=lPx0mbxN1VU6EdRe3KVdLi4X60LfOeNkg&id=819","es"},
       			{"ogabri1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=BNJCAatw6A4bxykTEZgc4NelPV2cSVWKS&id=958","pt"},
       			{"ossantan@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=LR1fDlr6peIpv1TTItkWWj4n5kFnb56kZ&id=859","es"},
       			{"perra@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=74ZExBEq4Qi6hpWhR6gyPAPTc616EQWco&id=827","es"},
       			{"pfernand@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=eBK5B2H7ACTvbmowQx6XTSDmO1hMPj6ja&id=870","es"},
       			{"phollan3@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=AMksQCfhjKfP0GIOa17XzU0FyGds7SInP&id=868","pt"},
       			{"prodrigu@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=CFu1D7FULFkpxOggesmzGMXrVQD8oLNuM&id=926","es"},
       			{"razanza@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=UxNUBtbkDM4Yc32YIuIumuACUeczOvq1c&id=892","es"},
       			{"rcabana1@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=FoL5LhqBujzyoGyk4DeJiMAmYI07xyrIU&id=882","es"},
       			{"rcarbon2@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=zZb8PSYisrZQbdWAuIQ1hMKqpFZveRUXF&id=907","en"},
       			{"rcavicch@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=ySIdMT0D17yNshVTJDUTrMydc6uuHenQg&id=831","pt"},
       			{"rmoraes1@janbr.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=eVTH5g95lv5zhsU7YHcL7LxCCWSWSmpQj&id=960","pt"},
       			{"rnascim6@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=8z2eQYnmvDcVhQ7M4z07HqF5kVWizPUhN&id=956","pt"},
       			{"rnunez@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=oVNYEuuOJ4QMzyIhkHESVy0MotTGrmOyM&id=879","es"},
       			{"rrodri22@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=1vjlgySa8J0m9dHzbV7sp7cssQ4V3Evfq&id=888","es"},
       			{"rsobrinh@conbr.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=RTVIQnscgbJRMF8oOJlPk300Wd8LH16nn&id=862","pt"},
       			{"rzotta@janar.jnj. com","http://elearning.cepasafedrive.com/loadAssesment.php?key=B4Hr0vkubcpiCdybAmBiDFp4vgVgKCzr4&id=839","es"},
       			{"scampoo@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=bIxyKtgYfwmZ7K05borF0QQKIpDATmw1B&id=918","es"},
       			{"slezica@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=RxDAMu5qPRtevMBqDwCu4rXFTPAiANknR&id=915","es"},
       			{"spalanca@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=Aiz8G7Vsj7OjwDDRE3QzOGhDzubGpBLuF&id=830","es"},
       			{"spaula1@its.jnj.com.br","http://elearning.cepasafedrive.com/loadAssesment.php?key=tN6BNpBXhOA3X4zGpIbCHCBP60iJZiJUa&id=860","pt"},
       			{"tarcal@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=NijIxRyLLWOIi6CXk4N33SHQLkHJe4kcQ&id=922","es"},
       			{"tcordido30@yahoo.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=WMMR6N3lORHdu4fkAhanMDsd8NESyKqLq&id=851","es"},
       			{"vbraga@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=W7tirniWf8Hhnvm0jCIwg4qxKndOJFc84&id=863","pt"},
       			{"vdavila@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=sKYl3AQfzPxwHP3n8ScivGgS2LHpPeHw7&id=950","pt"},
       			{"vekimura@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=VEq2kIsovcJycwRWqpnEfl01UDD3ovHXj&id=953","pt"},
       			{"visanabr@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=DrvDmFA2zCbA1t2N4Y601rAyJDZHA4KD6&id=898","es"},
       			{"vlago@medbr.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=w6A7Wx8hOGyE9wYJbHS0QY2QT7BJygMD7&id=932","pt"},
       			{"volivari@janar.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=9VSnPjzZdYr54fqTHiQrhyR7feux7U18H&id=846","es"},
       			{"vriehn@its.jnj.com","http://elearning.cepasafedrive.com/loadAssesment.php?key=W3u10dmwbO2vlsAnjOhGIbe8wJhzRwIde&id=818","es"}};

//        File f = new File("C://usersMonsantoArg.sql");
//        FileOutputStream fos = new FileOutputStream(f);
            
        MD5 md5 = new MD5();
    	for(int i = 88; i < users.length; i++) {
    		String email = users[i][0];
    		//email = "juanyfla@adinet.com.uy";
    		String href = users[i][1];
    		String language = users[i][2];

            boolean done = false;
        	while(!done) {
                try {

        	        Properties properties = new Properties();
        	        properties.put("mail.smtp.host",SMTP);
        	        properties.put("mail.smtp.auth", "true");
        	        properties.put("mail.from",FROM_NAME);
        	        Session session = Session.getInstance(properties,null);
        	    
        	        InternetAddress[] address = {new InternetAddress(email,email)};
        	        MimeMessage mensaje = new  MimeMessage(session);
        			// Rellenar los atributos y el contenido
        			// Asunto
        			mensaje.setSubject("Online e-Learning/Driver Assessment");
        			// Emisor del mensaje
        			mensaje.setFrom(new InternetAddress(FROM_DIR,FROM_NAME));
        			// Receptor del mensaje
        			mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress(email,email));
        			// Crear un Multipart de tipo multipart/related
        			mensaje.setContent(getMail(language,href),"text/html");

        			// Enviar el mensaje

        	        Transport transport = session.getTransport(address[0]);
        	        transport.connect(SMTP, FROM_DIR, PASSWORD);
        	        transport.sendMessage(mensaje,address);
        	        
        	        done = true;
        	        System.out.println("ENVIADO "+email);        	        
	            }catch (Exception e) {
	            	e.printStackTrace();
	    	        System.out.println("EXCEPCION "+email);
	            	// 	TODO: handle exception
	            }
    		}
    	}
	}
	
	
	private static String getMail(String language,String href) {
		String mail = "<html>" +
			"	<head>" +
			"	</head>" +
			"	<body bgcolor=\"#ffffff\" text=\"#000066\">";
		if(language.equals("es")) {
			mail +=	"		<table>" +
			"			<tr>" +
			"				<td>" +
			"					<font size=\"-1\"><font face=\"Verdana\">" +
			"					<br>" +
			"					</font></font>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<i>Estimados les recordamos la invitaci&oacute;n a utilizar (o completar) la herramienta web e-learning.&nbsp;" +
			"						<br>" +
			"						<br>" +
			"						El link a continuaci&oacute;n est&aacute; personalizado, contiene los m&oacute;dulos que debe completar, y el mismo le servir&aacute; para ingresar a la herramienta cada vez que lo desee. Una vez termine los m&oacute;dulos, recibir&aacute; un email con el Certificado del curso e-Learning/Assessment. " +
			"						<br>" +
			"						<br>" +
			"						Gracias por su participaci&oacute;n.<i>" +
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
			"						<br>" +
			"						<font color=\"#666666\">" +
			"							<i><u>Importante:</u> algunos administradores de correos no permiten abrir link adjuntos, si es su caso por favor copiar y pegar la direcci&oacute;n en su navegador.</i>" +
			"							<br>" +
			"						</font>" +
			"					</span>" +
			"					<br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br>" +
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						Por dudas y consultas favor comunicarse con: " +
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b>" +
			"					</span>";
		} else if(language.equals("en")) {
			mail +=	"		<table>" +
			"			<tr>" +
			"				<td>" +
			"					<font size=\"-1\"><font face=\"Verdana\">" +
			"					<br>" +
			"					</font></font>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<i>Dear users.&nbsp;" +
			"						<br>" +
			"						<br>" +
			"						We would like to remind you of the invitation to use (or complete) the online e-learning tool. " +
			"						<br>" +
			"						<br>" +
			"						The following link has been tailored and contains the modules you will need to complete. It should be used to access the site. After completing the modules you will receive an e-mail with your e-Learning/Assessment Completion Certificate. " +
			"						<br>" +
			"						<br>" +
			"						Thanks for your participation.</i>" +
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
			"						<br>" +
			"						<br>" +
			"						<font color=\"#666666\">" +
			"							<i><u>Important:</u> some e-mail account administrators do not allow access to attached links. If this is your case, please copy and paste the link to your browser’s address bar (URL bar).</i>" +
			"							<br>" +
			"						</font>" +
			"					</span>" +
			"					<br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br>" +
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<i>For any question comunicate with</i>" +
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b>" +
			"					</span>";
		}else {
			mail +=	"		<table>" +
			"			<tr>" +
			"				<td>" +
			"					<font size=\"-1\"><font face=\"Verdana\">" +
			"					<br>" +
			"					</font></font>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<i>Prezados usu&aacute;rios&nbsp;" +
			"						<br>" +
			"						<br>" +
			"						Recordamos o convite para utilizar (ou concluir) a ferramenta web e-learning. " +
			"						<br>" +
			"						<br>" +
			"						O link a seguir foi personalizado e cont&eacute;m os m&oacute;dulos a serem completados. O mesmo dever&aacute; ser usado para ingressar no site. Ap&oacute;s concluir os m&oacute;dulos voc&ecirc; receber&aacute; um e-mail com o Certificado de conclus&atilde;o do curso e-Learning/ Assessment.  " +
			"						<br>" +
			"						<br>" +
			"						Obrigado por sua participa&ccedil;&atilde;o.</i>" +
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
			"						<br>" +
			"						<font color=\"#666666\">" +
			"							<i><u>Importante:</u> alguns administradores de correio n&atilde;o permitem a abertura de links anexados. Se este for o caso, favor copiar e colar o link na barra de endere&ccedil;os do seu navegador.<i>" +
			"							<br>" +
			"						</font>" +
			"					</span>" +
			"					<br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br>" +
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<i>Em caso de d&uacute;vidas ou consultas favor entrar em contato com </i>" +
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<i><b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b></i>" +
			"					</span>";
		}
		mail +=	"					<br>" +
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
