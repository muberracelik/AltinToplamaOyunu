import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Arayüz extends javax.swing.JFrame {

    public static Toolkit kit = Toolkit.getDefaultToolkit();
    public static int EkranX;
    public static int EkranY;
    public static JButton[][] JButtonKare;
    public static int tahtaXSayisi;
    public static int tahtaYSayisi;
    ButtonHandler buttonHandler = new ButtonHandler();
    public static Kare[][] kareler;
    public static ArrayList<Kare> altinlar = new ArrayList<>();
    public int hareketBeklemeSuresi = 1000;
    Oyuncu oyuncuA = new Oyuncu();
    Oyuncu oyuncuB = new Oyuncu();
    Oyuncu oyuncuC = new Oyuncu();
    Oyuncu oyuncuD = new Oyuncu();
    int beklemeSuresi = 0;

    public static BufferedWriter bufferedWriter = null;

    public Arayüz() {
        initComponents();
        EkranX = (int) kit.getScreenSize().width; //Ekran boyutunun genişliğini alıyoruz...
        EkranY = (int) kit.getScreenSize().height;//Ekran boyutunun yüksekliğini alıyoruz...
        this.setLocation((EkranX - 1050) / 2, (EkranY - 800) / 2);  // Görünüm olarak açılan pencerenin ekranın tam ortasında çıkması için
        this.setSize(1050, 800);

    }

    public void parayaz() {
        System.out.println("A: " + oyuncuA.kasadakiAltinMiktari);
        System.out.println("B" + oyuncuB.kasadakiAltinMiktari);
        System.out.println("C: " + oyuncuC.kasadakiAltinMiktari);
        System.out.println("D: " + oyuncuD.kasadakiAltinMiktari);
        System.out.println("");
        if ((oyuncuA.kasadakiAltinMiktari <= 0 && oyuncuB.kasadakiAltinMiktari <= 0 && oyuncuC.kasadakiAltinMiktari <= 0 && oyuncuD.kasadakiAltinMiktari <= 0) || altinlar.size() == 0) {
            System.out.println("A: toplanan ,harcanan, kasadaki altın " + oyuncuA.toplananAltinMiktari + oyuncuA.harcananAltinMiktari + oyuncuA.kasadakiAltinMiktari);
            System.out.println("B: toplanan ,harcanan, kasadaki altın " + oyuncuB.toplananAltinMiktari + oyuncuB.harcananAltinMiktari + oyuncuB.kasadakiAltinMiktari);
            System.out.println("C: toplanan ,harcanan, kasadaki altın " + oyuncuC.toplananAltinMiktari + oyuncuC.harcananAltinMiktari + oyuncuC.kasadakiAltinMiktari);
            System.out.println("D: toplanan ,harcanan, kasadaki altın " + oyuncuD.toplananAltinMiktari + oyuncuD.harcananAltinMiktari + oyuncuD.kasadakiAltinMiktari);
            try {//oyun bitince x saniye bekle
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Arayüz.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {

                bufferedWriter.write("\n\n----------------------------------------------------------------------------------------------------------------------------\n\n");
                bufferedWriter.write("Toplam Adim Sayisi:                             " + oyuncuA.adimSayisi + "                       " + oyuncuB.adimSayisi + "                       " + oyuncuC.adimSayisi + "                     " + oyuncuD.adimSayisi);
                bufferedWriter.write("\nHarcanan Altin Miktarı:                        " + oyuncuA.harcananAltinMiktari + "                      " + oyuncuB.harcananAltinMiktari + "                      " + oyuncuC.harcananAltinMiktari + "                     " + oyuncuD.harcananAltinMiktari);
                bufferedWriter.write("\nToplanan Altin Miktarı:                        " + oyuncuA.toplananAltinMiktari + "                      " + oyuncuB.toplananAltinMiktari + "                      " + oyuncuC.toplananAltinMiktari + "                     " + oyuncuD.toplananAltinMiktari);
                bufferedWriter.write("\nKasadaki Altin Miktarı:                       " + oyuncuA.kasadakiAltinMiktari + "                     " + oyuncuB.kasadakiAltinMiktari + "                     " + oyuncuC.kasadakiAltinMiktari + "                     " + oyuncuD.kasadakiAltinMiktari);

            } catch (IOException ex) {
                Logger.getLogger(Arayüz.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bufferedWriter.flush();
            } catch (IOException ex) {
                Logger.getLogger(Arayüz.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }
    }

    public void oyunOlustur() {
        if (Integer.parseInt(tahtaX.getText()) < EkranX - 250 && Integer.parseInt(tahtaY.getText()) < EkranY && !kareKenar.getText().isEmpty()) {
            Random rnd = new Random();
            altinlar.clear();
            oyunAlani.removeAll();
            boyutOrantıla();
            oyunAlani.setSize(Integer.parseInt(tahtaX.getText()), Integer.parseInt(tahtaY.getText()) - 40);
            kontrolAlani.setSize(250, Integer.parseInt(tahtaY.getText()) - 40);
            this.setSize(250 + Integer.parseInt(tahtaX.getText()), Integer.parseInt(tahtaY.getText()));
            kontrolAlani.setLocation(Integer.parseInt(tahtaX.getText()), 0);
            this.setLocation((EkranX - Integer.parseInt(tahtaX.getText()) - 250) / 2, (EkranY - Integer.parseInt(tahtaY.getText())) / 2);
            oyunAlani.setLayout(new GridLayout(tahtaYSayisi, tahtaXSayisi));
            JButtonKare = new JButton[tahtaYSayisi][tahtaXSayisi];
            kareler = new Kare[tahtaYSayisi][tahtaXSayisi];

            for (int i = 0; i < tahtaYSayisi; i++) {
                for (int j = 0; j < tahtaXSayisi; j++) {
                    JButtonKare[i][j] = new JButton();
                    JButtonKare[i][j].setBackground(new java.awt.Color(240, 240, 240));
                    kareler[i][j] = new Kare();
                    oyunAlani.add(JButtonKare[i][j], BorderLayout.CENTER);
                }

            }
            oyuncuOlustur();
            int x, y, rndAltinMiktari;
            int uretilenaltin = 0;
            int gizliAltin = tahtaXSayisi * tahtaYSayisi * Integer.parseInt(altinOrani.getText()) / 100 * Integer.parseInt(gizliAltinOrani.getText()) / 100;
            if (gizliAltin == 0) {
                gizliAltin = 1;
            }
            while (uretilenaltin < tahtaXSayisi * tahtaYSayisi * Integer.parseInt(altinOrani.getText()) / 100) {
                x = rnd.nextInt(tahtaYSayisi);
                y = rnd.nextInt(tahtaXSayisi);
                rndAltinMiktari = 5 + rnd.nextInt(4) * 5;
                if (gizliAltin != 0 && ((x != 0 && y != 0) && (x != tahtaYSayisi - 1 && y != 0) && (x != 0 && y != tahtaXSayisi - 1) && (x != tahtaYSayisi - 1 && y != tahtaXSayisi)) && kareler[x][y].altinMiktari == 0) { // gizli altınlar için
                    gizliAltin--;
                    uretilenaltin++;
                    kareler[x][y].altinMiktari = rndAltinMiktari;
                    JButtonKare[x][y].setText(String.valueOf(kareler[x][y].altinMiktari));
                    kareler[x][y].gizli = true;
                    kareler[x][y].konum[0] = x;
                    kareler[x][y].konum[1] = y;
                    altinlar.add(kareler[x][y]);
                    JButtonKare[x][y].setBackground(Color.pink);
                    continue;
                }
                if (((x != 0 && y != 0) && (x != tahtaYSayisi - 1 && y != 0) && (x != 0 && y != tahtaXSayisi - 1) && (x != tahtaYSayisi - 1 && y != tahtaXSayisi)) && kareler[x][y].altinMiktari == 0) { // normal altınlar için

                    JButtonKare[x][y].setBackground(Color.yellow);
                    kareler[x][y].altinMiktari = rndAltinMiktari;
                    JButtonKare[x][y].setText(String.valueOf(kareler[x][y].altinMiktari));
                    kareler[x][y].konum[0] = x;
                    kareler[x][y].konum[1] = y;
                    altinlar.add(kareler[x][y]);
                    uretilenaltin++;
                }
            }
            setResizable(false);
            setVisible(true);
        }
    }

    public void boyutOrantıla() {
        tahtaX.setText(String.valueOf(Integer.parseInt(tahtaX.getText()) / Integer.parseInt(kareKenar.getText()) * Integer.parseInt(kareKenar.getText())));
        tahtaY.setText(String.valueOf(Integer.parseInt(tahtaY.getText()) / Integer.parseInt(kareKenar.getText()) * Integer.parseInt(kareKenar.getText())));
        tahtaXSayisi = Integer.parseInt(tahtaX.getText()) / Integer.parseInt(kareKenar.getText());
        tahtaYSayisi = Integer.parseInt(tahtaY.getText()) / Integer.parseInt(kareKenar.getText());

    }

    public void oyuncuOlustur() {
        JButtonKare[0][0].setBackground(Color.red);
        JButtonKare[0][0].setText("A");
        oyuncuA.ad = "A";
        oyuncuA.kasadakiAltinMiktari = Integer.parseInt(mevcutAltin.getText());
        oyuncuA.AktifKonumu[0] = 0;
        oyuncuA.AktifKonumu[1] = 0;
        oyuncuA.hedefAltinKonum[0] = -1;
        oyuncuA.hedefAltinKonum[1] = -1;
        oyuncuA.adimSayisi = 0;
        oyuncuA.harcananAltinMiktari = 0;
        oyuncuA.toplananAltinMiktari = 0;

        JButtonKare[0][tahtaXSayisi - 1].setBackground(Color.ORANGE);
        JButtonKare[0][tahtaXSayisi - 1].setText("B");
        oyuncuB.ad = "B";
        oyuncuB.kasadakiAltinMiktari = Integer.parseInt(mevcutAltin.getText());
        oyuncuB.AktifKonumu[0] = 0;
        oyuncuB.AktifKonumu[1] = tahtaXSayisi - 1;
        oyuncuB.hedefAltinKonum[0] = -1;
        oyuncuB.hedefAltinKonum[1] = -1;
        oyuncuB.adimSayisi = 0;
        oyuncuB.harcananAltinMiktari = 0;
        oyuncuB.toplananAltinMiktari = 0;

        JButtonKare[tahtaYSayisi - 1][tahtaXSayisi - 1].setBackground(Color.BLUE);
        JButtonKare[tahtaYSayisi - 1][tahtaXSayisi - 1].setText("C");
        oyuncuC.ad = "C";
        oyuncuC.kasadakiAltinMiktari = Integer.parseInt(mevcutAltin.getText());
        oyuncuC.AktifKonumu[0] = tahtaYSayisi - 1;
        oyuncuC.AktifKonumu[1] = tahtaXSayisi - 1;
        oyuncuC.hedefAltinKonum[0] = -1;
        oyuncuC.hedefAltinKonum[1] = -1;
        oyuncuC.adimSayisi = 0;
        oyuncuC.harcananAltinMiktari = 0;
        oyuncuC.toplananAltinMiktari = 0;

        JButtonKare[tahtaYSayisi - 1][0].setBackground(Color.GREEN);
        JButtonKare[tahtaYSayisi - 1][0].setText("D");
        oyuncuD.ad = "D";
        oyuncuD.kasadakiAltinMiktari = Integer.parseInt(mevcutAltin.getText());
        oyuncuD.AktifKonumu[0] = tahtaYSayisi - 1;
        oyuncuD.AktifKonumu[1] = 0;
        oyuncuD.hedefAltinKonum[0] = -1;
        oyuncuD.hedefAltinKonum[1] = -1;
        oyuncuD.adimSayisi = 0;
        oyuncuD.harcananAltinMiktari = 0;
        oyuncuD.toplananAltinMiktari = 0;

    }

    public void AOyna() {
        parayaz();
        if (oyuncuA.kasadakiAltinMiktari <= 0) {
            BOyna();
            JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setBackground(new java.awt.Color(240, 240, 240));
            JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setText("");
        } else {
            int enkAdim = Integer.MAX_VALUE;
            int xuzaklik = 0, yuzaklik = 0;
            int silinecekAltinIndex = 0;
            int hedefMaliyeti = 0;
            int hedefAltinx = 0, hedefAltiny = 0;
            if (oyuncuA.hedefAltinKonum[0] != -1) {
                if (kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].altinMiktari == 0) {
                    oyuncuA.hedefAltinKonum[0] = -1;
                    oyuncuA.hedefAltinKonum[1] = -1;
                }
            }
            if (oyuncuA.hedefAltinKonum[0] == -1 && oyuncuA.hedefAltinKonum[1] == -1) {//oyuncu hedefi yoksa
                for (int i = 0; i < altinlar.size(); i++) {
                    int tempxuzaklik, tempyuzaklik;
                    tempxuzaklik = oyuncuA.AktifKonumu[0] - altinlar.get(i).konum[0];
                    tempyuzaklik = oyuncuA.AktifKonumu[1] - altinlar.get(i).konum[1];

                    if (Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik) < enkAdim && altinlar.get(i).gizli == false) {
                        enkAdim = Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik);
                        hedefAltinx = altinlar.get(i).konum[0];
                        hedefAltiny = altinlar.get(i).konum[1];
                        xuzaklik = tempxuzaklik;
                        yuzaklik = tempyuzaklik;
                        silinecekAltinIndex = i;
                    }
                }
                if (hedefAltinx == 0 && hedefAltiny == 0) {//eğer oyuncu herhangi bir altını hedefleyemiyorsa
                    return;
                }
                oyuncuA.hedefAltinKonum[0] = hedefAltinx;
                oyuncuA.hedefAltinKonum[1] = hedefAltiny;
                hedefMaliyeti = Integer.parseInt(AHedefMaaliyet.getText());
            } else {//oyuncunun hedefi varsa silinecek altının arraylistteki yeri tespit edilir
                for (int i = 0; i < altinlar.size(); i++) {
                    if (altinlar.get(i).konum[0] == oyuncuA.hedefAltinKonum[0] && altinlar.get(i).konum[1] == oyuncuA.hedefAltinKonum[1]) {
                        silinecekAltinIndex = i;
                    }
                }
                xuzaklik = oyuncuA.AktifKonumu[0] - oyuncuA.hedefAltinKonum[0];
                yuzaklik = oyuncuA.AktifKonumu[1] - oyuncuA.hedefAltinKonum[1];
                hedefMaliyeti = 0;
            }

            int adim = 0;
            JButtonKare[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].setText("<html>" + kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].altinMiktari + "<br>" + "X" + "</html>");

            adim = hareketEttir(xuzaklik, yuzaklik, adim, oyuncuA, "A", Color.RED);
            beklemeSuresi = adim * 1000 + 500;
            new Thread() {
                @Override
                public void run() {
                    try {

                        Thread.sleep(beklemeSuresi);
                        BOyna();

                    } catch (InterruptedException e) {
                    }

                }
            }.start();
            oyuncuA.harcananAltinMiktari += Integer.parseInt(AHamleMaaliyet.getText()) + hedefMaliyeti;
            oyuncuA.kasadakiAltinMiktari -= Integer.parseInt(AHamleMaaliyet.getText()) + hedefMaliyeti;

            if (oyuncuA.AktifKonumu[0] == oyuncuA.hedefAltinKonum[0] && oyuncuA.AktifKonumu[1] == oyuncuA.hedefAltinKonum[1]) {//altını aldıysa altının miktarın ekliyoruz
                oyuncuA.kasadakiAltinMiktari += kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].altinMiktari;
                oyuncuA.toplananAltinMiktari += kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].altinMiktari;

            }
            if (Math.abs(yuzaklik) + Math.abs(xuzaklik) <= Integer.valueOf(adimSayisi.getText())) {//eğer altın a kullanıcısının adımları içerisinde ise altını alacağı için listeden silmemiz gerekir
                altinlar.remove(silinecekAltinIndex);
                kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].altinMiktari = 0;
                kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].gizli = false;
                oyuncuA.hedefAltinKonum[0] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
                oyuncuA.hedefAltinKonum[1] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok

            }
        }
    }

    public void BOyna() {
        parayaz();
        if (oyuncuB.kasadakiAltinMiktari <= 0) {
            COyna();
            JButtonKare[oyuncuB.AktifKonumu[0]][oyuncuB.AktifKonumu[1]].setBackground(new java.awt.Color(240, 240, 240));
            JButtonKare[oyuncuB.AktifKonumu[0]][oyuncuB.AktifKonumu[1]].setText("");
        } else {
            int enkMaliyet = Integer.MIN_VALUE;
            int xuzaklik = 0, yuzaklik = 0;
            int silinecekAltinIndex = 0;
            int hedefMaliyeti = 0;
            int hedefAltinx = 0, hedefAltiny = 0;
            if (oyuncuB.hedefAltinKonum[0] != -1) {
                if (kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].altinMiktari == 0) {
                    oyuncuB.hedefAltinKonum[0] = -1;
                    oyuncuB.hedefAltinKonum[1] = -1;
                }
            }
            if (oyuncuB.hedefAltinKonum[0] == -1 && oyuncuB.hedefAltinKonum[1] == -1) {//oyuncu hedefi yoksa
                for (int i = 0; i < altinlar.size(); i++) {
                    int tempxuzaklik, tempyuzaklik;
                    tempxuzaklik = oyuncuB.AktifKonumu[0] - altinlar.get(i).konum[0];
                    tempyuzaklik = oyuncuB.AktifKonumu[1] - altinlar.get(i).konum[1];

                    int kar = (altinlar.get(i).altinMiktari - (((Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik)) * Integer.parseInt(BHamleMaaliyet.getText())) + Integer.parseInt(BHedefMaaliyet.getText())));
                    if (kar > enkMaliyet && altinlar.get(i).gizli == false) {
                        enkMaliyet = kar;
                        hedefAltinx = altinlar.get(i).konum[0];
                        hedefAltiny = altinlar.get(i).konum[1];
                        xuzaklik = tempxuzaklik;
                        yuzaklik = tempyuzaklik;
                        silinecekAltinIndex = i;

                    }
                }
                if (hedefAltinx == 0 && hedefAltiny == 0) {//eğer oyuncu herhangi bir altını hedefleyemiyorsa
                    return;
                }
                oyuncuB.hedefAltinKonum[0] = hedefAltinx;
                oyuncuB.hedefAltinKonum[1] = hedefAltiny;
                hedefMaliyeti = Integer.parseInt(BHedefMaaliyet.getText());
            } else {
                for (int i = 0; i < altinlar.size(); i++) {
                    if (altinlar.get(i).konum[0] == oyuncuB.hedefAltinKonum[0] && altinlar.get(i).konum[1] == oyuncuB.hedefAltinKonum[1]) {
                        silinecekAltinIndex = i;
                    }
                }
                xuzaklik = oyuncuB.AktifKonumu[0] - oyuncuB.hedefAltinKonum[0];
                yuzaklik = oyuncuB.AktifKonumu[1] - oyuncuB.hedefAltinKonum[1];
                hedefMaliyeti = 0;
            }
            int adim = 0;
            JButtonKare[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].setText("<html>" + kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].altinMiktari + "<br>" + "X" + "</html>");

            adim = hareketEttir(xuzaklik, yuzaklik, adim, oyuncuB, "B", Color.ORANGE);
            beklemeSuresi = adim * 1000 + 500;
            new Thread() {
                @Override
                public void run() {
                    try {

                        Thread.sleep(beklemeSuresi);
                        COyna();

                    } catch (InterruptedException e) {
                    }

                }
            }.start();
            oyuncuB.harcananAltinMiktari += Integer.parseInt(BHamleMaaliyet.getText()) + hedefMaliyeti;
            oyuncuB.kasadakiAltinMiktari -= Integer.parseInt(BHamleMaaliyet.getText()) + hedefMaliyeti;

            if (oyuncuB.AktifKonumu[0] == oyuncuB.hedefAltinKonum[0] && oyuncuB.AktifKonumu[1] == oyuncuB.hedefAltinKonum[1]) {
                oyuncuB.kasadakiAltinMiktari += kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].altinMiktari;
                oyuncuB.toplananAltinMiktari += kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].altinMiktari;

            }
            if (Math.abs(yuzaklik) + Math.abs(xuzaklik) <= Integer.valueOf(adimSayisi.getText())) {//eğer altın a kullanıcısının adımları içerisinde ise altını alacağı için listeden silmemiz gerekir
                altinlar.remove(silinecekAltinIndex);
                kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].altinMiktari = 0;
                kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].gizli = false;
                oyuncuB.hedefAltinKonum[0] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
                oyuncuB.hedefAltinKonum[1] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
            }

        }
    }

    public void COyna() {
        parayaz();
        if (oyuncuC.kasadakiAltinMiktari <= 0) {
            DOyna();
            JButtonKare[oyuncuC.AktifKonumu[0]][oyuncuC.AktifKonumu[1]].setBackground(new java.awt.Color(240, 240, 240));
            JButtonKare[oyuncuC.AktifKonumu[0]][oyuncuC.AktifKonumu[1]].setText("");
        } else {
            int enkMaliyet = Integer.MIN_VALUE;
            int xuzaklik = 0, yuzaklik = 0;
            int silinecekAltinIndex = 0;
            int hedefMaliyet = 0;
            int hedefAltinx = 0, hedefAltiny = 0;
            if (oyuncuC.hedefAltinKonum[0] != -1) {
                if (kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].altinMiktari == 0) {
                    oyuncuC.hedefAltinKonum[0] = -1;
                    oyuncuC.hedefAltinKonum[1] = -1;
                }
            }
            if (oyuncuC.hedefAltinKonum[0] == -1 && oyuncuC.hedefAltinKonum[1] == -1) {//oyuncu hedefi yoksa
                for (int i = 0; i < altinlar.size(); i++) {
                    int tempxuzaklik, tempyuzaklik;
                    tempxuzaklik = oyuncuC.AktifKonumu[0] - altinlar.get(i).konum[0];
                    tempyuzaklik = oyuncuC.AktifKonumu[1] - altinlar.get(i).konum[1];
                    if (altinlar.get(i).gizli == true) {
                        JButtonKare[altinlar.get(i).konum[0]][altinlar.get(i).konum[1]].setBackground(Color.yellow);
                        altinlar.get(i).gizli = false;
                    }
                    int kar = (altinlar.get(i).altinMiktari - (((Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik)) * Integer.parseInt(CHamleMaaliyet.getText())) + Integer.parseInt(CHedefMaaliyet.getText())));
                    if (kar > enkMaliyet && altinlar.get(i).gizli == false) {
                        enkMaliyet = kar;
                        hedefAltinx = altinlar.get(i).konum[0];
                        hedefAltiny = altinlar.get(i).konum[1];
                        xuzaklik = tempxuzaklik;
                        yuzaklik = tempyuzaklik;
                        silinecekAltinIndex = i;
                    }
                }
                if (hedefAltinx == 0 && hedefAltiny == 0) {//eğer oyuncu herhangi bir altını hedefleyemiyorsa
                    return;
                }
                oyuncuC.hedefAltinKonum[0] = hedefAltinx;
                oyuncuC.hedefAltinKonum[1] = hedefAltiny;
                hedefMaliyet = Integer.parseInt(CHedefMaaliyet.getText());
            } else {
                for (int i = 0; i < altinlar.size(); i++) {
                    if (altinlar.get(i).konum[0] == oyuncuC.hedefAltinKonum[0] && altinlar.get(i).konum[1] == oyuncuC.hedefAltinKonum[1]) {
                        silinecekAltinIndex = i;
                    }
                }
                xuzaklik = oyuncuC.AktifKonumu[0] - oyuncuC.hedefAltinKonum[0];
                yuzaklik = oyuncuC.AktifKonumu[1] - oyuncuC.hedefAltinKonum[1];
                hedefMaliyet = 0;
            }
            int adim = 0;
            JButtonKare[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].setText("<html>" + kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].altinMiktari + "<br>" + "X" + "</html>");

            adim = hareketEttir(xuzaklik, yuzaklik, adim, oyuncuC, "C", Color.BLUE);
            beklemeSuresi = adim * 1000 + 500;
            new Thread() {
                @Override
                public void run() {
                    try {

                        Thread.sleep(beklemeSuresi);
                        DOyna();

                    } catch (InterruptedException e) {
                    }

                }
            }.start();
            oyuncuC.harcananAltinMiktari += Integer.parseInt(CHamleMaaliyet.getText()) + hedefMaliyet;
            oyuncuC.kasadakiAltinMiktari -= Integer.parseInt(CHamleMaaliyet.getText()) + hedefMaliyet;

            if (oyuncuC.AktifKonumu[0] == oyuncuC.hedefAltinKonum[0] && oyuncuC.AktifKonumu[1] == oyuncuC.hedefAltinKonum[1]) {
                oyuncuC.kasadakiAltinMiktari += kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].altinMiktari;
                oyuncuC.toplananAltinMiktari += kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].altinMiktari;

            }
            if (Math.abs(yuzaklik) + Math.abs(xuzaklik) <= Integer.valueOf(adimSayisi.getText())) {//eğer altın a kullanıcısının adımları içerisinde ise altını alacağı için listeden silmemiz gerekir
                altinlar.remove(silinecekAltinIndex);
                kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].altinMiktari = 0;
                kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].gizli = false;
                oyuncuC.hedefAltinKonum[0] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
                oyuncuC.hedefAltinKonum[1] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
            }
        }
    }

    public void DOyna() {
        parayaz();
        if (oyuncuD.kasadakiAltinMiktari <= 0) {
            AOyna();
            JButtonKare[oyuncuD.AktifKonumu[0]][oyuncuD.AktifKonumu[1]].setBackground(new java.awt.Color(240, 240, 240));
            JButtonKare[oyuncuD.AktifKonumu[0]][oyuncuD.AktifKonumu[1]].setText("");
        } else {
            ArrayList<Kare> dAltinlar = (ArrayList<Kare>) altinlar.clone();
            if (oyuncuA.hedefAltinKonum[0] == -1) { //anın hedefi yoksa
                int enkAdim = Integer.MAX_VALUE;
                int xuzaklik = 0, yuzaklik = 0;
                int silinecekAltinIndex = 0;
                int hedefMaliyeti = 0;
                int hedefAltinx = 0, hedefAltiny = 0;
                for (int i = 0; i < altinlar.size(); i++) {
                    int tempxuzaklik, tempyuzaklik;
                    tempxuzaklik = oyuncuA.AktifKonumu[0] - altinlar.get(i).konum[0];
                    tempyuzaklik = oyuncuA.AktifKonumu[1] - altinlar.get(i).konum[1];

                    if (Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik) < enkAdim && altinlar.get(i).gizli == false) {
                        enkAdim = Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik);
                        hedefAltinx = altinlar.get(i).konum[0];
                        hedefAltiny = altinlar.get(i).konum[1];
                        xuzaklik = tempxuzaklik;
                        yuzaklik = tempyuzaklik;
                        silinecekAltinIndex = i;
                    }
                }
                int aUzaklik = Math.abs(oyuncuA.AktifKonumu[0] - hedefAltinx) + Math.abs(oyuncuA.AktifKonumu[1] - hedefAltiny);
                int dUzaklik = Math.abs(oyuncuD.AktifKonumu[0] - hedefAltinx) + Math.abs(oyuncuD.AktifKonumu[1] - hedefAltiny);
                if (dUzaklik > aUzaklik && dUzaklik > Integer.valueOf(adimSayisi.getText())) {
                    dAltinlar.remove(altinlar.get(silinecekAltinIndex));
                }
            } else {// a nın hedefi varsa
                int aUzaklik = Math.abs(oyuncuA.AktifKonumu[0] - oyuncuA.hedefAltinKonum[0]) + Math.abs(oyuncuA.AktifKonumu[1] - oyuncuA.hedefAltinKonum[1]);
                int dUzaklik = Math.abs(oyuncuD.AktifKonumu[0] - oyuncuA.hedefAltinKonum[0]) + Math.abs(oyuncuD.AktifKonumu[1] - oyuncuA.hedefAltinKonum[1]);
                if (dUzaklik > aUzaklik && dUzaklik > Integer.valueOf(adimSayisi.getText())) {
                    for (int k = 0; k < altinlar.size(); k++) { // a nın hedefi varsa yine uzaklıkları karşılaştır a daha önce ulaşıyorsa karşılaştırıp dAltinlar dan sil
                        if (Arrays.equals(altinlar.get(k).konum, oyuncuA.hedefAltinKonum)) {
                            dAltinlar.remove(altinlar.get(k));
                        }
                    }
                }
            }

            if (oyuncuB.hedefAltinKonum[0] == -1) {
                int enkMaliyet = Integer.MIN_VALUE;
                int xuzaklik = 0, yuzaklik = 0;
                int silinecekAltinIndex = 0;
                int hedefMaliyeti = 0;
                int hedefAltinx = 0, hedefAltiny = 0;
                for (int i = 0; i < altinlar.size(); i++) {
                    int tempxuzaklik, tempyuzaklik;
                    tempxuzaklik = oyuncuB.AktifKonumu[0] - altinlar.get(i).konum[0];
                    tempyuzaklik = oyuncuB.AktifKonumu[1] - altinlar.get(i).konum[1];

                    int kar = (altinlar.get(i).altinMiktari - (((Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik)) * Integer.parseInt(BHamleMaaliyet.getText())) + Integer.parseInt(BHedefMaaliyet.getText())));
                    if (kar > enkMaliyet && altinlar.get(i).gizli == false) {
                        enkMaliyet = kar;
                        hedefAltinx = altinlar.get(i).konum[0];
                        hedefAltiny = altinlar.get(i).konum[1];
                        xuzaklik = tempxuzaklik;
                        yuzaklik = tempyuzaklik;
                        silinecekAltinIndex = i;

                    }
                }
                int bUzaklik = Math.abs(oyuncuB.AktifKonumu[0] - hedefAltinx) + Math.abs(oyuncuB.AktifKonumu[1] - hedefAltiny);
                int dUzaklik = Math.abs(oyuncuD.AktifKonumu[0] - hedefAltinx) + Math.abs(oyuncuD.AktifKonumu[1] - hedefAltiny);
                if (dUzaklik > bUzaklik && dUzaklik > Integer.valueOf(adimSayisi.getText())) {
                    dAltinlar.remove(altinlar.get(silinecekAltinIndex));
                }
            } else {// B nın hedefi varsa
                int bUzaklik = Math.abs(oyuncuB.AktifKonumu[0] - oyuncuB.hedefAltinKonum[0]) + Math.abs(oyuncuB.AktifKonumu[1] - oyuncuB.hedefAltinKonum[1]);
                int dUzaklik = Math.abs(oyuncuD.AktifKonumu[0] - oyuncuB.hedefAltinKonum[0]) + Math.abs(oyuncuD.AktifKonumu[1] - oyuncuB.hedefAltinKonum[1]);
                if (dUzaklik > bUzaklik && dUzaklik > Integer.valueOf(adimSayisi.getText())) {
                    for (int k = 0; k < altinlar.size(); k++) { // b nın hedefi varsa yine uzaklıkları karşılaştır b daha önce ulaşıyorsa karşılaştırıp dAltinlar dan sil
                        if (Arrays.equals(altinlar.get(k).konum, oyuncuB.hedefAltinKonum)) {
                            dAltinlar.remove(altinlar.get(k));
                        }
                    }
                }
            }

            if (oyuncuC.hedefAltinKonum[0] == -1) {
                int enkMaliyet = Integer.MIN_VALUE;
                int xuzaklik = 0, yuzaklik = 0;
                int silinecekAltinIndex = 0;
                int hedefMaliyeti = 0;
                int hedefAltinx = 0, hedefAltiny = 0;
                for (int i = 0; i < altinlar.size(); i++) {
                    int tempxuzaklik, tempyuzaklik;
                    tempxuzaklik = oyuncuC.AktifKonumu[0] - altinlar.get(i).konum[0];
                    tempyuzaklik = oyuncuC.AktifKonumu[1] - altinlar.get(i).konum[1];
                    if (altinlar.get(i).gizli == true) {
                        JButtonKare[altinlar.get(i).konum[0]][altinlar.get(i).konum[1]].setBackground(Color.yellow);
                        altinlar.get(i).gizli = false;
                    }
                    int kar = (altinlar.get(i).altinMiktari - (((Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik)) * Integer.parseInt(CHamleMaaliyet.getText())) + Integer.parseInt(CHedefMaaliyet.getText())));
                    if (kar > enkMaliyet && altinlar.get(i).gizli == false) {
                        enkMaliyet = kar;
                        hedefAltinx = altinlar.get(i).konum[0];
                        hedefAltiny = altinlar.get(i).konum[1];
                        xuzaklik = tempxuzaklik;
                        yuzaklik = tempyuzaklik;
                        silinecekAltinIndex = i;
                    }
                }
                int cUzaklik = Math.abs(oyuncuC.AktifKonumu[0] - hedefAltinx) + Math.abs(oyuncuC.AktifKonumu[1] - hedefAltiny);
                int dUzaklik = Math.abs(oyuncuD.AktifKonumu[0] - hedefAltinx) + Math.abs(oyuncuD.AktifKonumu[1] - hedefAltiny);
                if (dUzaklik > cUzaklik && dUzaklik > Integer.valueOf(adimSayisi.getText())) {
                    dAltinlar.remove(altinlar.get(silinecekAltinIndex));
                }
            } else {// C nın hedefi varsa
                int cUzaklik = Math.abs(oyuncuC.AktifKonumu[0] - oyuncuC.hedefAltinKonum[0]) + Math.abs(oyuncuC.AktifKonumu[1] - oyuncuC.hedefAltinKonum[1]);
                int dUzaklik = Math.abs(oyuncuD.AktifKonumu[0] - oyuncuC.hedefAltinKonum[0]) + Math.abs(oyuncuD.AktifKonumu[1] - oyuncuC.hedefAltinKonum[1]);
                if (dUzaklik > cUzaklik && dUzaklik > Integer.valueOf(adimSayisi.getText())) {
                    for (int k = 0; k < altinlar.size(); k++) { // c nın hedefi varsa yine uzaklıkları karşılaştır c daha önce ulaşıyorsa karşılaştırıp dAltinlar dan sil
                        if (Arrays.equals(altinlar.get(k).konum, oyuncuC.hedefAltinKonum)) {
                            dAltinlar.remove(altinlar.get(k));
                        }
                    }
                }
            }

            int enkMaliyet = Integer.MIN_VALUE;
            int xuzaklik = 0, yuzaklik = 0;
            int silinecekAltinIndex = 0;
            int hedefMaliyeti = 0;
            int hedefAltinx = 0, hedefAltiny = 0;
            if (oyuncuD.hedefAltinKonum[0] != -1) {
                if (kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].altinMiktari == 0) {
                    oyuncuD.hedefAltinKonum[0] = -1;
                    oyuncuD.hedefAltinKonum[1] = -1;
                }
            }
            if (oyuncuD.hedefAltinKonum[0] == -1 && oyuncuD.hedefAltinKonum[1] == -1) {//oyuncu hedefi yoksa
                for (int i = 0; i < dAltinlar.size(); i++) {
                    int tempxuzaklik, tempyuzaklik;
                    tempxuzaklik = oyuncuD.AktifKonumu[0] - dAltinlar.get(i).konum[0];
                    tempyuzaklik = oyuncuD.AktifKonumu[1] - dAltinlar.get(i).konum[1];

                    int kar = (dAltinlar.get(i).altinMiktari - (((Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik)) * Integer.parseInt(BHamleMaaliyet.getText())) + Integer.parseInt(BHedefMaaliyet.getText())));
                    if (kar > enkMaliyet && dAltinlar.get(i).gizli == false) {
                        enkMaliyet = kar;
                        hedefAltinx = dAltinlar.get(i).konum[0];
                        hedefAltiny = dAltinlar.get(i).konum[1];
                        xuzaklik = tempxuzaklik;
                        yuzaklik = tempyuzaklik;
                        silinecekAltinIndex = i;

                    }
                }
                if (hedefAltinx == 0 && hedefAltiny == 0) {//eğer oyuncu herhangi bir altını hedefleyemiyorsa
                    return;
                }
                oyuncuD.hedefAltinKonum[0] = hedefAltinx;
                oyuncuD.hedefAltinKonum[1] = hedefAltiny;
                hedefMaliyeti = Integer.parseInt(BHedefMaaliyet.getText());
            } else {
                for (int i = 0; i < dAltinlar.size(); i++) {
                    if (dAltinlar.get(i).konum[0] == oyuncuD.hedefAltinKonum[0] && dAltinlar.get(i).konum[1] == oyuncuD.hedefAltinKonum[1]) {
                        silinecekAltinIndex = i;
                    }
                }
                xuzaklik = oyuncuD.AktifKonumu[0] - oyuncuD.hedefAltinKonum[0];
                yuzaklik = oyuncuD.AktifKonumu[1] - oyuncuD.hedefAltinKonum[1];
                hedefMaliyeti = 0;
            }
            int adim = 0;
            JButtonKare[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].setText("<html>" + kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].altinMiktari + "<br>" + "X" + "</html>");

            adim = hareketEttir(xuzaklik, yuzaklik, adim, oyuncuD, "D", Color.GREEN);
            beklemeSuresi = adim * 1000 + 500;
            new Thread() {
                @Override
                public void run() {
                    try {

                        Thread.sleep(beklemeSuresi);
                        AOyna();

                    } catch (InterruptedException e) {
                    }

                }
            }.start();
            oyuncuD.harcananAltinMiktari += Integer.parseInt(BHamleMaaliyet.getText()) + hedefMaliyeti;
            oyuncuD.kasadakiAltinMiktari -= Integer.parseInt(BHamleMaaliyet.getText()) + hedefMaliyeti;

            if (oyuncuD.AktifKonumu[0] == oyuncuD.hedefAltinKonum[0] && oyuncuD.AktifKonumu[1] == oyuncuD.hedefAltinKonum[1]) {
                oyuncuD.kasadakiAltinMiktari += kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].altinMiktari;
                oyuncuD.toplananAltinMiktari += kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].altinMiktari;

            }
            if (Math.abs(yuzaklik) + Math.abs(xuzaklik) <= Integer.valueOf(adimSayisi.getText())) {//eğer altın a kullanıcısının adımları içerisinde ise altını alacağı için listeden silmemiz gerekir
                altinlar.remove(dAltinlar.get(silinecekAltinIndex));
                kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].altinMiktari = 0;
                kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].gizli = false;
                oyuncuD.hedefAltinKonum[0] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
                oyuncuD.hedefAltinKonum[1] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
            }

        }
    }

    public int hareketEttir(int xuzaklik, int yuzaklik, int adim, Oyuncu oyuncu, String oyuncuIsmi, Color renk) {
        int tmpAdimlar[][] = new int[Integer.valueOf(adimSayisi.getText())][2];
        if (xuzaklik < 0 && yuzaklik < 0) {
            for (int i = oyuncu.AktifKonumu[0]; i < oyuncu.hedefAltinKonum[0]; i++) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {

                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] + 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx1 = tmpKonumx;
                            int tmpKonumy1 = tmpKonumy;
                            try {

                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx1 && oyuncuA.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx1 && oyuncuB.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx1 && oyuncuC.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx1 && oyuncuD.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("D");
                                } else {
                                    if (kareler[tmpKonumx1][tmpKonumy1].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setText("");
                                    } else {
                                        if (kareler[tmpKonumx1][tmpKonumy1].gizli == true) {
                                            kareler[tmpKonumx1][tmpKonumy1].gizli = false;
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        }
                                    }
                                }

                                tmpKonumx1 = tmpKonumx1 + 1;

                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }

            }
            for (int i = oyuncu.AktifKonumu[1]; i < oyuncu.hedefAltinKonum[1]; i++) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {
                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] + 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx2 = tmpKonumx;
                            int tmpKonumy2 = tmpKonumy;
                            try {
                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx2 && oyuncuA.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx2 && oyuncuB.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx2 && oyuncuC.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx2 && oyuncuD.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("D");
                                } else {
                                    if (kareler[tmpKonumx2][tmpKonumy2].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setText("");
                                    } else {
                                        if (kareler[tmpKonumx2][tmpKonumy2].gizli == true) {
                                            kareler[tmpKonumx2][tmpKonumy2].gizli = false;
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        }
                                    }
                                }

                                tmpKonumy2 = tmpKonumy2 + 1;

                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }
            }
        }

        if (xuzaklik < 0 && yuzaklik == 0) {
            for (int i = oyuncu.AktifKonumu[0]; i < oyuncu.hedefAltinKonum[0]; i++) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {

                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] + 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx1 = tmpKonumx;
                            int tmpKonumy1 = tmpKonumy;
                            try {

                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx1 && oyuncuA.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx1 && oyuncuB.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx1 && oyuncuC.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx1 && oyuncuD.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("D");
                                } else {
                                    if (kareler[tmpKonumx1][tmpKonumy1].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setText("");
                                    } else {
                                        if (kareler[tmpKonumx1][tmpKonumy1].gizli == true) {
                                            kareler[tmpKonumx1][tmpKonumy1].gizli = false;
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        }
                                    }
                                }

                                tmpKonumx1 = tmpKonumx1 + 1;

                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }

            }

        }
        if (xuzaklik > 0 && yuzaklik < 0) {
            for (int i = oyuncu.AktifKonumu[0]; i > oyuncu.hedefAltinKonum[0]; i--) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {

                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] - 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx1 = tmpKonumx;
                            int tmpKonumy1 = tmpKonumy;
                            try {

                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx1 && oyuncuA.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx1 && oyuncuB.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx1 && oyuncuC.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx1 && oyuncuD.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("D");
                                } else {
                                    if (kareler[tmpKonumx1][tmpKonumy1].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setText("");
                                    } else {
                                        if (kareler[tmpKonumx1][tmpKonumy1].gizli == true) {
                                            kareler[tmpKonumx1][tmpKonumy1].gizli = false;
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumx1 = tmpKonumx1 - 1;
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }

            }
            for (int i = oyuncu.AktifKonumu[1]; i < oyuncu.hedefAltinKonum[1]; i++) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {
                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] + 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx2 = tmpKonumx;
                            int tmpKonumy2 = tmpKonumy;
                            try {
                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx2 && oyuncuA.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx2 && oyuncuB.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx2 && oyuncuC.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx2 && oyuncuD.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("D");
                                } else {
                                    if (kareler[tmpKonumx2][tmpKonumy2].altinMiktari == 0) {

                                        JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setText("");
                                    } else {
                                        if (kareler[tmpKonumx2][tmpKonumy2].gizli == true) {
                                            kareler[tmpKonumx2][tmpKonumy2].gizli = false;
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumy2 = tmpKonumy2 + 1; // A için x in konumunu arttırdık veya azalttık.

                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }
            }
        }

        if (xuzaklik > 0 && yuzaklik == 0) {
            for (int i = oyuncu.AktifKonumu[0]; i > oyuncu.hedefAltinKonum[0]; i--) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {

                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] - 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx1 = tmpKonumx;
                            int tmpKonumy1 = tmpKonumy;
                            try {

                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx1 && oyuncuA.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx1 && oyuncuB.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx1 && oyuncuC.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx1 && oyuncuD.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("D");
                                } else {
                                    if (kareler[tmpKonumx1][tmpKonumy1].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setText("");
                                    } else {
                                        if (kareler[tmpKonumx1][tmpKonumy1].gizli == true) {
                                            kareler[tmpKonumx1][tmpKonumy1].gizli = false;
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumx1 = tmpKonumx1 - 1;

                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }

            }

        }
        ///////
        if (yuzaklik > 0 && xuzaklik < 0) {
            for (int i = oyuncu.AktifKonumu[0]; i < oyuncu.hedefAltinKonum[0]; i++) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {

                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] + 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx1 = tmpKonumx;
                            int tmpKonumy1 = tmpKonumy;
                            try {

                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx1 && oyuncuA.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx1 && oyuncuB.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx1 && oyuncuC.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx1 && oyuncuD.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("D");
                                } else {
                                    if (kareler[tmpKonumx1][tmpKonumy1].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setText("");
                                    } else {
                                        if (kareler[tmpKonumx1][tmpKonumy1].gizli == true) {
                                            kareler[tmpKonumx1][tmpKonumy1].gizli = false;
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumx1 = tmpKonumx1 + 1;
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                    continue;
                }

            }
            for (int i = oyuncu.AktifKonumu[1]; i > oyuncu.hedefAltinKonum[1]; i--) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {
                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] - 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx2 = tmpKonumx;
                            int tmpKonumy2 = tmpKonumy;
                            try {
                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx2 && oyuncuA.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx2 && oyuncuB.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx2 && oyuncuC.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx2 && oyuncuD.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("D");
                                } else {
                                    if (kareler[tmpKonumx2][tmpKonumy2].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setText("");
                                    } else {
                                        if (kareler[tmpKonumx2][tmpKonumy2].gizli == true) {
                                            kareler[tmpKonumx2][tmpKonumy2].gizli = false;
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumy2 = tmpKonumy2 - 1; // A için x in konumunu arttırdık veya azalttık.

                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                    continue;
                }
            }
        }
        if (yuzaklik > 0 && xuzaklik > 0) {
            for (int i = oyuncu.AktifKonumu[0]; i > oyuncu.hedefAltinKonum[0]; i--) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {

                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] - 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx1 = tmpKonumx;
                            int tmpKonumy1 = tmpKonumy;
                            try {

                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx1 && oyuncuA.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx1 && oyuncuB.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx1 && oyuncuC.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx1 && oyuncuD.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("D");
                                } else {
                                    if (kareler[tmpKonumx1][tmpKonumy1].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setText("");
                                    } else {
                                        if (kareler[tmpKonumx1][tmpKonumy1].gizli == true) {
                                            kareler[tmpKonumx1][tmpKonumy1].gizli = false;
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumx1 = tmpKonumx1 - 1;
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }

            }
            for (int i = oyuncu.AktifKonumu[1]; i > oyuncu.hedefAltinKonum[1]; i--) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {
                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] - 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx2 = tmpKonumx;
                            int tmpKonumy2 = tmpKonumy;
                            try {
                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx2 && oyuncuA.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx2 && oyuncuB.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx2 && oyuncuC.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx2 && oyuncuD.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("D");
                                } else {
                                    if (kareler[tmpKonumx2][tmpKonumy2].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setText("");
                                    } else {
                                        if (kareler[tmpKonumx2][tmpKonumy2].gizli == true) {
                                            kareler[tmpKonumx2][tmpKonumy2].gizli = false;
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumy2 = tmpKonumy2 - 1; // A için x in konumunu arttırdık veya azalttık.

                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }
            }
        }
        if (yuzaklik > 0 && xuzaklik == 0) {
            for (int i = oyuncu.AktifKonumu[1]; i > oyuncu.hedefAltinKonum[1]; i--) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {
                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] - 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx2 = tmpKonumx;
                            int tmpKonumy2 = tmpKonumy;
                            try {
                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx2 && oyuncuA.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx2 && oyuncuB.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx2 && oyuncuC.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx2 && oyuncuD.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("D");
                                } else {
                                    if (kareler[tmpKonumx2][tmpKonumy2].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setText("");
                                    } else {
                                        if (kareler[tmpKonumx2][tmpKonumy2].gizli == true) {
                                            kareler[tmpKonumx2][tmpKonumy2].gizli = false;
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumy2 = tmpKonumy2 - 1; // A için x in konumunu arttırdık veya azalttık.

                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }
            }

        }
        if (yuzaklik < 0 && xuzaklik < 0) {
            for (int i = oyuncu.AktifKonumu[0]; i < oyuncu.hedefAltinKonum[0]; i++) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {

                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] + 1;

                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx1 = tmpKonumx;
                            int tmpKonumy1 = tmpKonumy;
                            try {

                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx1 && oyuncuA.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx1 && oyuncuB.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx1 && oyuncuC.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx1 && oyuncuD.AktifKonumu[1] == tmpKonumy1) {
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx1][tmpKonumy1].setText("D");
                                } else {
                                    if (kareler[tmpKonumx1][tmpKonumy1].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx1][tmpKonumy1].setText("");
                                    } else {
                                        if (kareler[tmpKonumx1][tmpKonumy1].gizli == true) {
                                            kareler[tmpKonumx1][tmpKonumy1].gizli = false;
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx1][tmpKonumy1].setText(String.valueOf(kareler[tmpKonumx1][tmpKonumy1].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumx1 = tmpKonumx1 + 1;
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setBackground(renk);
                                JButtonKare[tmpKonumx1][tmpKonumy1].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }

            }
            for (int i = oyuncu.AktifKonumu[1]; i < oyuncu.hedefAltinKonum[1]; i++) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {
                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] + 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx2 = tmpKonumx;
                            int tmpKonumy2 = tmpKonumy;
                            try {
                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx2 && oyuncuA.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx2 && oyuncuB.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx2 && oyuncuC.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx2 && oyuncuD.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("D");
                                } else {
                                    if (kareler[tmpKonumx2][tmpKonumy2].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setText("");
                                    } else {
                                        if (kareler[tmpKonumx2][tmpKonumy2].gizli == true) {
                                            kareler[tmpKonumx2][tmpKonumy2].gizli = false;
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumy2 = tmpKonumy2 + 1; // A için x in konumunu arttırdık veya azalttık.

                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }
            }
        }

        if (yuzaklik < 0 && xuzaklik == 0) {

            for (int i = oyuncu.AktifKonumu[1]; i < oyuncu.hedefAltinKonum[1]; i++) {
                if (adim < Integer.valueOf(adimSayisi.getText())) {
                    adim++;
                    int sleep = adim * hareketBeklemeSuresi;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] + 1;
                    tmpAdimlar[adim - 1] = oyuncu.AktifKonumu.clone();
                    new Thread() {
                        @Override
                        public void run() {
                            int tmpKonumx2 = tmpKonumx;
                            int tmpKonumy2 = tmpKonumy;
                            try {
                                Thread.sleep(sleep);

                                if (oyuncuA.AktifKonumu[0] == tmpKonumx2 && oyuncuA.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.RED);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("A");
                                } else if (oyuncuB.AktifKonumu[0] == tmpKonumx2 && oyuncuB.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.ORANGE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("B");
                                } else if (oyuncuC.AktifKonumu[0] == tmpKonumx2 && oyuncuC.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.BLUE);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("C");
                                } else if (oyuncuD.AktifKonumu[0] == tmpKonumx2 && oyuncuD.AktifKonumu[1] == tmpKonumy2) {
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.GREEN);
                                    JButtonKare[tmpKonumx2][tmpKonumy2].setText("D");
                                } else {
                                    if (kareler[tmpKonumx2][tmpKonumy2].altinMiktari == 0) {
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(new java.awt.Color(240, 240, 240));
                                        JButtonKare[tmpKonumx2][tmpKonumy2].setText("");
                                    } else {
                                        if (kareler[tmpKonumx2][tmpKonumy2].gizli == true) {
                                            kareler[tmpKonumx2][tmpKonumy2].gizli = false;
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        } else {
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(Color.YELLOW);
                                            JButtonKare[tmpKonumx2][tmpKonumy2].setText(String.valueOf(kareler[tmpKonumx2][tmpKonumy2].altinMiktari));
                                        }
                                    }
                                }
                                tmpKonumy2 = tmpKonumy2 + 1; // A için x in konumunu arttırdık veya azalttık.

                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setBackground(renk);
                                JButtonKare[tmpKonumx2][tmpKonumy2].setText(oyuncuIsmi);
                            } catch (InterruptedException e) {
                            }

                        }
                    }.start();
                }
            }
        }

        if (oyuncuA.kasadakiAltinMiktari <= 0) {
            JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setBackground(new java.awt.Color(240, 240, 240));
            JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setText("");
        }
        if (oyuncuB.kasadakiAltinMiktari <= 0) {
            JButtonKare[oyuncuB.AktifKonumu[0]][oyuncuB.AktifKonumu[1]].setBackground(new java.awt.Color(240, 240, 240));
            JButtonKare[oyuncuB.AktifKonumu[0]][oyuncuB.AktifKonumu[1]].setText("");
        }
        if (oyuncuC.kasadakiAltinMiktari <= 0) {
            JButtonKare[oyuncuC.AktifKonumu[0]][oyuncuC.AktifKonumu[1]].setBackground(new java.awt.Color(240, 240, 240));
            JButtonKare[oyuncuC.AktifKonumu[0]][oyuncuC.AktifKonumu[1]].setText("");
        }
        if (oyuncuD.kasadakiAltinMiktari <= 0) {
            JButtonKare[oyuncuD.AktifKonumu[0]][oyuncuD.AktifKonumu[1]].setBackground(new java.awt.Color(240, 240, 240));
            JButtonKare[oyuncuD.AktifKonumu[0]][oyuncuD.AktifKonumu[1]].setText("");
        }
        try {
            if (oyuncu.equals(oyuncuA)) {
                bufferedWriter.write("                                          ");
            }
            System.out.println(adim);
            for (int i = 0; i < adim - 1; i++) {
                bufferedWriter.write(tmpAdimlar[i][0] + "-" + tmpAdimlar[i][1] + ",");
            }
            bufferedWriter.write(tmpAdimlar[adim - 1][0] + "-" + tmpAdimlar[adim - 1][1] + "             ");
            if (oyuncu.equals(oyuncuD)) {
                bufferedWriter.write("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(Arayüz.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            bufferedWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(Arayüz.class.getName()).log(Level.SEVERE, null, ex);
        }
        oyuncu.adimSayisi += adim;
        return adim;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        oyunAlani = new javax.swing.JPanel();
        kontrolAlani = new javax.swing.JPanel();
        tahtaX = new javax.swing.JTextField();
        tahtaY = new javax.swing.JTextField();
        basla = new javax.swing.JButton();
        kareKenar = new javax.swing.JTextField();
        carpiText = new javax.swing.JLabel();
        kareBoyutText = new javax.swing.JLabel();
        mevcutAltin = new javax.swing.JTextField();
        altinOrani = new javax.swing.JTextField();
        gizliAltinOrani = new javax.swing.JTextField();
        adimSayisi = new javax.swing.JTextField();
        DHedefMaaliyet = new javax.swing.JTextField();
        DHamleMaaliyet = new javax.swing.JTextField();
        BHedefMaaliyet = new javax.swing.JTextField();
        CHedefMaaliyet = new javax.swing.JTextField();
        AHedefMaaliyet = new javax.swing.JTextField();
        AHamleMaaliyet = new javax.swing.JTextField();
        BHamleMaaliyet = new javax.swing.JTextField();
        CHamleMaaliyet = new javax.swing.JTextField();
        dikdötgenBoyutText = new javax.swing.JLabel();
        mevcutAltinText = new javax.swing.JLabel();
        altinOraniText = new javax.swing.JLabel();
        gizliAltinOraniText = new javax.swing.JLabel();
        adimSayisiText = new javax.swing.JLabel();
        hedef = new javax.swing.JLabel();
        hamle1 = new javax.swing.JLabel();
        dText = new javax.swing.JLabel();
        aText = new javax.swing.JLabel();
        bText = new javax.swing.JLabel();
        cText = new javax.swing.JLabel();
        baslik1 = new javax.swing.JLabel();
        baslik0 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(800, 800));
        getContentPane().setLayout(null);

        oyunAlani.setBackground(new java.awt.Color(255, 102, 102));
        oyunAlani.setLayout(null);
        getContentPane().add(oyunAlani);
        oyunAlani.setBounds(0, 0, 800, 800);

        kontrolAlani.setBackground(new java.awt.Color(102, 255, 153));
        kontrolAlani.setLayout(null);

        tahtaX.setText("800");
        kontrolAlani.add(tahtaX);
        tahtaX.setBounds(120, 160, 50, 30);

        tahtaY.setText("800");
        kontrolAlani.add(tahtaY);
        tahtaY.setBounds(200, 160, 50, 30);

        basla.setText("BAŞLA");
        basla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baslaActionPerformed(evt);
            }
        });
        kontrolAlani.add(basla);
        basla.setBounds(50, 690, 140, 28);

        kareKenar.setText("20");
        kontrolAlani.add(kareKenar);
        kareKenar.setBounds(120, 200, 50, 30);

        carpiText.setText("X");
        kontrolAlani.add(carpiText);
        carpiText.setBounds(180, 160, 30, 30);

        kareBoyutText.setText("Karenin Boyutu:");
        kontrolAlani.add(kareBoyutText);
        kareBoyutText.setBounds(10, 200, 130, 30);

        mevcutAltin.setText("200");
        kontrolAlani.add(mevcutAltin);
        mevcutAltin.setBounds(120, 240, 50, 30);

        altinOrani.setText("20");
        kontrolAlani.add(altinOrani);
        altinOrani.setBounds(120, 280, 50, 30);

        gizliAltinOrani.setText("10");
        kontrolAlani.add(gizliAltinOrani);
        gizliAltinOrani.setBounds(120, 320, 50, 30);

        adimSayisi.setText("3");
        kontrolAlani.add(adimSayisi);
        adimSayisi.setBounds(120, 360, 50, 30);

        DHedefMaaliyet.setText("20");
        kontrolAlani.add(DHedefMaaliyet);
        DHedefMaaliyet.setBounds(40, 630, 70, 35);

        DHamleMaaliyet.setText("5");
        kontrolAlani.add(DHamleMaaliyet);
        DHamleMaaliyet.setBounds(150, 630, 70, 35);

        BHedefMaaliyet.setText("10");
        kontrolAlani.add(BHedefMaaliyet);
        BHedefMaaliyet.setBounds(40, 550, 70, 35);

        CHedefMaaliyet.setText("15");
        kontrolAlani.add(CHedefMaaliyet);
        CHedefMaaliyet.setBounds(40, 590, 70, 35);

        AHedefMaaliyet.setText("5");
        kontrolAlani.add(AHedefMaaliyet);
        AHedefMaaliyet.setBounds(40, 510, 70, 35);

        AHamleMaaliyet.setText("5");
        kontrolAlani.add(AHamleMaaliyet);
        AHamleMaaliyet.setBounds(150, 510, 70, 35);

        BHamleMaaliyet.setText("5");
        kontrolAlani.add(BHamleMaaliyet);
        BHamleMaaliyet.setBounds(150, 550, 70, 35);

        CHamleMaaliyet.setText("5");
        kontrolAlani.add(CHamleMaaliyet);
        CHamleMaaliyet.setBounds(150, 590, 70, 35);

        dikdötgenBoyutText.setText("Tahta Boyutu :");
        kontrolAlani.add(dikdötgenBoyutText);
        dikdötgenBoyutText.setBounds(10, 160, 110, 30);

        mevcutAltinText.setText("Mevcut Altın:");
        kontrolAlani.add(mevcutAltinText);
        mevcutAltinText.setBounds(10, 240, 100, 30);

        altinOraniText.setText("Altın Oranı:");
        kontrolAlani.add(altinOraniText);
        altinOraniText.setBounds(10, 280, 90, 30);

        gizliAltinOraniText.setText("Gizli Altın(%)");
        kontrolAlani.add(gizliAltinOraniText);
        gizliAltinOraniText.setBounds(10, 320, 110, 30);

        adimSayisiText.setText("Adım Sayısı:");
        kontrolAlani.add(adimSayisiText);
        adimSayisiText.setBounds(10, 360, 100, 30);

        hedef.setText("Hedef Maliyet");
        kontrolAlani.add(hedef);
        hedef.setBounds(30, 470, 110, 18);

        hamle1.setText("Hamle Maliyet");
        kontrolAlani.add(hamle1);
        hamle1.setBounds(140, 470, 110, 18);

        dText.setText("D");
        kontrolAlani.add(dText);
        dText.setBounds(10, 630, 30, 30);

        aText.setText("A");
        kontrolAlani.add(aText);
        aText.setBounds(10, 510, 30, 30);

        bText.setText("B");
        kontrolAlani.add(bText);
        bText.setBounds(10, 550, 30, 30);

        cText.setText("C");
        kontrolAlani.add(cText);
        cText.setBounds(10, 590, 30, 30);

        baslik1.setFont(new java.awt.Font("Noto Serif CJK KR", 0, 18)); // NOI18N
        baslik1.setText("TOPLAMA OYUNU");
        kontrolAlani.add(baslik1);
        baslik1.setBounds(40, 40, 240, 80);

        baslik0.setFont(new java.awt.Font("Noto Serif CJK KR", 0, 18)); // NOI18N
        baslik0.setText("ALTIN");
        kontrolAlani.add(baslik0);
        baslik0.setBounds(90, 0, 90, 80);

        getContentPane().add(kontrolAlani);
        kontrolAlani.setBounds(800, 0, 250, 800);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void baslaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baslaActionPerformed
        oyunOlustur();
        AOyna();

    }//GEN-LAST:event_baslaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Arayüz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Arayüz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Arayüz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Arayüz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                File f = new File("cikti.txt"); // yol belirtmeyip sadece dosya ismi belirttiğimiz zaman otomatik olarak bulunduğu klasöre göre işlem yapar.
                if (!f.exists()) { // eğer dosya yoksa
                    System.out.println("Dosya bulunamadığından silinemedi");
                } else {
                    f.delete();
                } // eğer dosyamız varsa.. // silme işlemi gerçekleştirir.
                new Arayüz().setVisible(true);
                String path = ".//cikti.txt";// Oluşturulacak dosyanın yolu.
                File file = new File(path);

                try {

                    boolean sonuc = file.createNewFile();
                    // bu metod bir exception firlatabileceğinden
                    // editörümüz bunu try -catch içine almamızı istiyor.

                    System.out.println("İzin var");

                    if (sonuc) {

                        System.out.println("Belirtilen dosya başarı ile oluştruldu");
                    } else {

                        System.out.println("Bu dosya zaten önceden oluşturulmuş");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                FileWriter fileWriter;
                try {
                    fileWriter = new FileWriter(file, true);
                    bufferedWriter = new BufferedWriter(fileWriter);
                } catch (IOException ex) {
                    Logger.getLogger(Arayüz.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    bufferedWriter.write("                                             OyuncuA               OyuncuB               OyuncuC               OyuncuD\n");
                    bufferedWriter.write("----------------------------------------------------------------------------------------------------------------------\n");
                } catch (IOException ex) {
                    Logger.getLogger(Arayüz.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    bufferedWriter.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Arayüz.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("file write Success");

            }
        }
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AHamleMaaliyet;
    private javax.swing.JTextField AHedefMaaliyet;
    private javax.swing.JTextField BHamleMaaliyet;
    private javax.swing.JTextField BHedefMaaliyet;
    private javax.swing.JTextField CHamleMaaliyet;
    private javax.swing.JTextField CHedefMaaliyet;
    private javax.swing.JTextField DHamleMaaliyet;
    private javax.swing.JTextField DHedefMaaliyet;
    private javax.swing.JLabel aText;
    private javax.swing.JTextField adimSayisi;
    private javax.swing.JLabel adimSayisiText;
    private javax.swing.JTextField altinOrani;
    private javax.swing.JLabel altinOraniText;
    private javax.swing.JLabel bText;
    private javax.swing.JButton basla;
    private javax.swing.JLabel baslik0;
    private javax.swing.JLabel baslik1;
    private javax.swing.JLabel cText;
    private javax.swing.JLabel carpiText;
    private javax.swing.JLabel dText;
    private javax.swing.JLabel dikdötgenBoyutText;
    private javax.swing.JTextField gizliAltinOrani;
    private javax.swing.JLabel gizliAltinOraniText;
    private javax.swing.JLabel hamle1;
    private javax.swing.JLabel hedef;
    private javax.swing.JLabel kareBoyutText;
    private javax.swing.JTextField kareKenar;
    private javax.swing.JPanel kontrolAlani;
    private javax.swing.JTextField mevcutAltin;
    private javax.swing.JLabel mevcutAltinText;
    private javax.swing.JPanel oyunAlani;
    private javax.swing.JTextField tahtaX;
    private javax.swing.JTextField tahtaY;
    // End of variables declaration//GEN-END:variables
}
