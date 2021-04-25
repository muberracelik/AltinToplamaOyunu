ALTIN TOPLAMA OYUNU 	Version 1.0	22/11/2020


PROJE TANIMI:
--------------------------------------------------------------------------------------
	JAVA dili ve swing GUI kullanarak farklı kısıtlara sahip arama
algoritmalarının birbirlerine karşı etkinliklerini gözlemlemek
--------------------------------------------------------------------------------------

GELİŞTİRME ORTAMI:
--------------------------------------------------------------------------------------
	Proje UBUNTU platformunda JAVA dili ve Swing kütüphanesi kullanılarak 
yazılmış olup; Netbeans ortamında, javac kullanılarak derlenip JVM aracılığı ile
test edilmiştir.
--------------------------------------------------------------------------------------

KULLANIM NOTLARI:
--------------------------------------------------------------------------------------

				    -             -HAZIRLIK--
0-	Uygulama jar dosyası ile çalıştırılır. Eğer bir geliştirme ortamı üzerinde
çalıştırılmak istenirse; Main metodu "Arayüz.java" sınıfının içinde olup,
çalıştırmak için bu sınıf "main class" olarak belirlenmelidir. 

									
				   --OYUN AYARLARINI BELİRLEME--	
1-	Uygulama çalıştırıldığında "Kullanıcı arayüzü" kullanıcıyı karşılar. Bu arayüzde 
oyun için gerekli parametre girişleri ve başlatma butonu bulunuyor. Kullanıcı oyunu
başlat butonuna basarak default ayarlarla başlatabilir veya istediği parametrelerin
değerlerini değiştirerek kendi ayarlarıyla oyunu başlatabilir.

								 --OYUN--
2- Oyun tam otomatiktir. Başlat butonuna basıldıktan sonra tüm işlemler kendiliğinden gerçekleşir.
Kullanıcı oyunu yeniden başlatmak isterse pencereyi kapatıp oyunu yeniden başlatmalıdır.

				  	- -OYUN SONU ÇIKTILARI--
3- Oyun tüm oyuncuların altını bittiğinde veya, oyunda hiç altın kalmadığında
beş saniye bekler ve gerekli bilgileri oyunun klasöründe bulunan cikti.txt dosyasına
yazar. Bu çıktıda oyuncuların adımları, toplam adım sayıları, harcadığı-topladığı
altın miktarları ve kasadaki altın miktarları özet olarak gösterilmiştir.


---------------------------------------------------------------------------------------


Müberra ÇELİK - muberraceliik@gmail.com
Taha Batuhan TÜRK - tbturkk@gmail.com