
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lenovo
 */
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

    Oyuncu oyuncuA = new Oyuncu();
    Oyuncu oyuncuB = new Oyuncu();
    Oyuncu oyuncuC = new Oyuncu();
    Oyuncu oyuncuD = new Oyuncu();

    public Arayüz() {
        initComponents();
        EkranX = (int) kit.getScreenSize().width; //Ekran boyutunun genişliğini alıyoruz...
        EkranY = (int) kit.getScreenSize().height;//Ekran boyutunun yüksekliğini alıyoruz...
        this.setLocation((EkranX - 1050) / 2, (EkranY - 800) / 2);  // Görünüm olarak açılan pencerenin ekranın tam ortasında çıkması için
        this.setSize(1050, 800);

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
            // System.out.println(((tahtaXSayisi * tahtaYSayisi) * 20) / 100);
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
        oyuncuA.mevcutAltinMiktari = Integer.parseInt(mevcutAltin.getText());
        oyuncuA.AktifKonumu[0] = 0;
        oyuncuA.AktifKonumu[1] = 0;
        oyuncuA.hedefAltinKonum[0] = -1;
        oyuncuA.hedefAltinKonum[1] = -1;
        oyuncuA.adimSayisi = 0;
        oyuncuA.harcananAltinMiktari = 0;
        oyuncuA.kasadakiAltinMiktari = 0;
        oyuncuA.toplananAltinMiktari = 0;

        JButtonKare[0][tahtaXSayisi - 1].setBackground(Color.ORANGE);
        JButtonKare[0][tahtaXSayisi - 1].setText("B");
        oyuncuB.ad = "B";
        oyuncuB.mevcutAltinMiktari = Integer.parseInt(mevcutAltin.getText());
        oyuncuB.AktifKonumu[0] = 0;
        oyuncuB.AktifKonumu[1] = tahtaXSayisi - 1;
        oyuncuB.hedefAltinKonum[0] = -1;
        oyuncuB.hedefAltinKonum[1] = -1;
        oyuncuB.adimSayisi = 0;
        oyuncuB.harcananAltinMiktari = 0;
        oyuncuB.kasadakiAltinMiktari = 0;
        oyuncuB.toplananAltinMiktari = 0;

        JButtonKare[tahtaYSayisi - 1][tahtaXSayisi - 1].setBackground(Color.BLUE);
        JButtonKare[tahtaYSayisi - 1][tahtaXSayisi - 1].setText("C");
        oyuncuC.ad = "C";
        oyuncuC.mevcutAltinMiktari = Integer.parseInt(mevcutAltin.getText());
        oyuncuC.AktifKonumu[0] = tahtaYSayisi - 1;
        oyuncuC.AktifKonumu[1] = tahtaXSayisi - 1;
        oyuncuC.hedefAltinKonum[0] = -1;
        oyuncuC.hedefAltinKonum[1] = -1;
        oyuncuC.adimSayisi = 0;
        oyuncuC.harcananAltinMiktari = 0;
        oyuncuC.kasadakiAltinMiktari = 0;
        oyuncuC.toplananAltinMiktari = 0;

        JButtonKare[tahtaYSayisi - 1][0].setBackground(Color.GREEN);
        JButtonKare[tahtaYSayisi - 1][0].setText("D");
        oyuncuD.ad = "D";
        oyuncuD.mevcutAltinMiktari = Integer.parseInt(mevcutAltin.getText());
        oyuncuD.AktifKonumu[0] = tahtaYSayisi - 1;
        oyuncuD.AktifKonumu[1] = 0;
        oyuncuD.hedefAltinKonum[0] = -1;
        oyuncuD.hedefAltinKonum[1] = -1;
        oyuncuD.adimSayisi = 0;
        oyuncuD.harcananAltinMiktari = 0;
        oyuncuD.kasadakiAltinMiktari = 0;
        oyuncuD.toplananAltinMiktari = 0;

    }

    public void AOyna() {
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
                    System.out.println(enkAdim + "ennkkAdim");
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
            hedefMaliyeti = Integer.parseInt(AHedefMaaliyet1.getText());
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
        System.out.println(oyuncuA.hedefAltinKonum[0] + " x hedeflenen" + oyuncuA.hedefAltinKonum[1] + "y hedeflenen");
        JButtonKare[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].setText("<html>" + kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].altinMiktari + "<br>" + "X" + "</html>");

        adim = hareketEttir(xuzaklik, yuzaklik, adim, oyuncuA, "A", Color.RED);

        //System.out.println(oyuncuA.AktifKonumu[0]+"m "+oyuncuA.AktifKonumu[1]+"u "+oyuncuA.hedefAltinKonum[0]+" b"+oyuncuA.hedefAltinKonum[1]);
        oyuncuA.harcananAltinMiktari += Integer.parseInt(AHamleMaaliyet.getText()) * adim + hedefMaliyeti;
        oyuncuA.mevcutAltinMiktari = oyuncuA.mevcutAltinMiktari - oyuncuA.harcananAltinMiktari;
        oyuncuA.kasadakiAltinMiktari += oyuncuA.harcananAltinMiktari;
        if (oyuncuA.AktifKonumu[0] == oyuncuA.hedefAltinKonum[0] && oyuncuA.AktifKonumu[1] == oyuncuA.hedefAltinKonum[1]) {
            oyuncuA.mevcutAltinMiktari += kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].altinMiktari;
            oyuncuA.toplananAltinMiktari += kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].altinMiktari;

        }
        if (Math.abs(yuzaklik) + Math.abs(xuzaklik) <= 3) {//eğer altın a kullanıcısının adımları içerisinde ise altını alacağı için listeden silmemiz gerekir
            altinlar.remove(silinecekAltinIndex);
            kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].altinMiktari = 0;
            kareler[oyuncuA.hedefAltinKonum[0]][oyuncuA.hedefAltinKonum[1]].gizli = false;
            oyuncuA.hedefAltinKonum[0] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
            oyuncuA.hedefAltinKonum[1] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok

        }
        System.out.println("harcanan:" + oyuncuA.harcananAltinMiktari + " kasadakiAltın" + oyuncuA.kasadakiAltinMiktari + " Mevcut altın" + oyuncuA.mevcutAltinMiktari + "toplanan altın" + oyuncuA.toplananAltinMiktari);

    }

    public void BOyna() {
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
            System.out.println("hedef yok");
            for (int i = 0; i < altinlar.size(); i++) {
                int tempxuzaklik, tempyuzaklik;
                tempxuzaklik = oyuncuB.AktifKonumu[0] - altinlar.get(i).konum[0];
                tempyuzaklik = oyuncuB.AktifKonumu[1] - altinlar.get(i).konum[1];

                int kar = (altinlar.get(i).altinMiktari - (((Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik)) * Integer.parseInt(BHamleMaaliyet1.getText())) + Integer.parseInt(BHedefMaaliyet.getText())));
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
            System.out.println("hedef var");
            for (int i = 0; i < altinlar.size(); i++) {
                if (altinlar.get(i).konum[0] == oyuncuB.hedefAltinKonum[0] && altinlar.get(i).konum[1] == oyuncuB.hedefAltinKonum[1]) {
                    silinecekAltinIndex = i;
                }
            }
            xuzaklik = oyuncuB.AktifKonumu[0] - oyuncuB.hedefAltinKonum[0];
            yuzaklik = oyuncuB.AktifKonumu[1] - oyuncuB.hedefAltinKonum[1];
            hedefMaliyeti = 0;
        }
        System.out.println(xuzaklik + " b için" + yuzaklik);
        int adim = 0;
        System.out.println(oyuncuB.hedefAltinKonum[0] + " x hedeflenen" + oyuncuB.hedefAltinKonum[1] + "y hedeflenen");
        JButtonKare[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].setText("<html>" + kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].altinMiktari + "<br>" + "X" + "</html>");

        adim = hareketEttir(xuzaklik, yuzaklik, adim, oyuncuB, "B", Color.ORANGE);
        System.out.println("adim gibi adim: " + adim);

        //System.out.println(oyuncuB.AktifKonumu[0]+"m "+oyuncuB.AktifKonumu[1]+"u "+oyuncuB.hedefAltinKonum[0]+" b"+oyuncuB.hedefAltinKonum[1]);
        oyuncuB.harcananAltinMiktari += Integer.parseInt(BHamleMaaliyet1.getText()) * adim + hedefMaliyeti;
        oyuncuB.mevcutAltinMiktari = oyuncuB.mevcutAltinMiktari - oyuncuB.harcananAltinMiktari;
        oyuncuB.kasadakiAltinMiktari += oyuncuB.harcananAltinMiktari;
        if (oyuncuB.AktifKonumu[0] == oyuncuB.hedefAltinKonum[0] && oyuncuB.AktifKonumu[1] == oyuncuB.hedefAltinKonum[1]) {
            oyuncuB.mevcutAltinMiktari += kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].altinMiktari;
            oyuncuB.toplananAltinMiktari += kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].altinMiktari;

        }
        if (Math.abs(yuzaklik) + Math.abs(xuzaklik) <= 3) {//eğer altın a kullanıcısının adımları içerisinde ise altını alacağı için listeden silmemiz gerekir
            altinlar.remove(silinecekAltinIndex);
            kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].altinMiktari = 0;
            kareler[oyuncuB.hedefAltinKonum[0]][oyuncuB.hedefAltinKonum[1]].gizli = false;
            oyuncuB.hedefAltinKonum[0] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
            oyuncuB.hedefAltinKonum[1] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
        }

        System.out.println(
                Integer.parseInt(CHamleMaaliyet1.getText()) * adim + hedefMaliyeti + "harcanan:" + oyuncuB.harcananAltinMiktari + " kasadakiAltın" + oyuncuB.kasadakiAltinMiktari + " Mevcut altın" + oyuncuB.mevcutAltinMiktari + "toplanan altın" + oyuncuB.toplananAltinMiktari);

    }

    public void COyna() {
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
                int kar = (altinlar.get(i).altinMiktari - (((Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik)) * Integer.parseInt(CHamleMaaliyet1.getText())) + Integer.parseInt(CHedefMaaliyet.getText())));
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
        System.out.println(enkMaliyet + "enk");
        System.out.println(xuzaklik + " C için" + yuzaklik);

        int adim = 0;
        System.out.println(oyuncuC.hedefAltinKonum[0] + " x hedeflenen" + oyuncuC.hedefAltinKonum[1] + "y hedeflenen");
        JButtonKare[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].setText("<html>" + kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].altinMiktari + "<br>" + "X" + "</html>");

        adim = hareketEttir(xuzaklik, yuzaklik, adim, oyuncuC, "C", Color.BLUE);

        //System.out.println(oyuncuC.AktifKonumu[0]+"m "+oyuncuC.AktifKonumu[1]+"u "+oyuncuC.hedefAltinKonum[0]+" b"+oyuncuC.hedefAltinKonum[1]);
        oyuncuC.harcananAltinMiktari += Integer.parseInt(CHamleMaaliyet1.getText()) * adim + hedefMaliyet;
        oyuncuC.mevcutAltinMiktari = oyuncuC.mevcutAltinMiktari - oyuncuC.harcananAltinMiktari;
        oyuncuC.kasadakiAltinMiktari += oyuncuC.harcananAltinMiktari;
        if (oyuncuC.AktifKonumu[0] == oyuncuC.hedefAltinKonum[0] && oyuncuC.AktifKonumu[1] == oyuncuC.hedefAltinKonum[1]) {
            oyuncuC.mevcutAltinMiktari += kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].altinMiktari;
            oyuncuC.toplananAltinMiktari += kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].altinMiktari;

        }
        if (Math.abs(yuzaklik) + Math.abs(xuzaklik) <= 3) {//eğer altın a kullanıcısının adımları içerisinde ise altını alacağı için listeden silmemiz gerekir
            altinlar.remove(silinecekAltinIndex);
            kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].altinMiktari = 0;
            kareler[oyuncuC.hedefAltinKonum[0]][oyuncuC.hedefAltinKonum[1]].gizli = false;
            oyuncuC.hedefAltinKonum[0] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
            oyuncuC.hedefAltinKonum[1] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
        }

        System.out.println(
                "harcanan:" + oyuncuC.harcananAltinMiktari + " kasadakiAltın" + oyuncuC.kasadakiAltinMiktari + " Mevcut altın" + oyuncuC.mevcutAltinMiktari + "toplanan altın" + oyuncuC.toplananAltinMiktari);

    }

    public void DOyna() {

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
                    System.out.println(enkAdim + "ennkkAdim");
                    hedefAltinx = altinlar.get(i).konum[0];
                    hedefAltiny = altinlar.get(i).konum[1];
                    xuzaklik = tempxuzaklik;
                    yuzaklik = tempyuzaklik;
                    silinecekAltinIndex = i;
                }
            }
            int aUzaklik = Math.abs(oyuncuA.AktifKonumu[0] - hedefAltinx) + Math.abs(oyuncuA.AktifKonumu[1] - hedefAltiny);
            int dUzaklik = Math.abs(oyuncuD.AktifKonumu[0] - hedefAltinx) + Math.abs(oyuncuD.AktifKonumu[1] - hedefAltiny);
            if (dUzaklik > aUzaklik && dUzaklik > 3) {
                dAltinlar.remove(altinlar.get(silinecekAltinIndex));
            }
        } else {// a nın hedefi varsa
            int aUzaklik = Math.abs(oyuncuA.AktifKonumu[0] - oyuncuA.hedefAltinKonum[0]) + Math.abs(oyuncuA.AktifKonumu[1] - oyuncuA.hedefAltinKonum[1]);
            int dUzaklik = Math.abs(oyuncuD.AktifKonumu[0] - oyuncuA.hedefAltinKonum[0]) + Math.abs(oyuncuD.AktifKonumu[1] - oyuncuA.hedefAltinKonum[1]);
            if (dUzaklik > aUzaklik && dUzaklik > 3) {
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

                int kar = (altinlar.get(i).altinMiktari - (((Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik)) * Integer.parseInt(BHamleMaaliyet1.getText())) + Integer.parseInt(BHedefMaaliyet.getText())));
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
            if (dUzaklik > bUzaklik && dUzaklik > 3) {
                dAltinlar.remove(altinlar.get(silinecekAltinIndex));
            }
        } else {// B nın hedefi varsa
            int bUzaklik = Math.abs(oyuncuB.AktifKonumu[0] - oyuncuB.hedefAltinKonum[0]) + Math.abs(oyuncuB.AktifKonumu[1] - oyuncuB.hedefAltinKonum[1]);
            int dUzaklik = Math.abs(oyuncuD.AktifKonumu[0] - oyuncuB.hedefAltinKonum[0]) + Math.abs(oyuncuD.AktifKonumu[1] - oyuncuB.hedefAltinKonum[1]);
            if (dUzaklik > bUzaklik && dUzaklik > 3) {
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
                int kar = (altinlar.get(i).altinMiktari - (((Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik)) * Integer.parseInt(CHamleMaaliyet1.getText())) + Integer.parseInt(CHedefMaaliyet.getText())));
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
            if (dUzaklik > cUzaklik && dUzaklik > 3) {
                dAltinlar.remove(altinlar.get(silinecekAltinIndex));
            }
        } else {// C nın hedefi varsa
            int cUzaklik = Math.abs(oyuncuC.AktifKonumu[0] - oyuncuC.hedefAltinKonum[0]) + Math.abs(oyuncuC.AktifKonumu[1] - oyuncuC.hedefAltinKonum[1]);
            int dUzaklik = Math.abs(oyuncuD.AktifKonumu[0] - oyuncuC.hedefAltinKonum[0]) + Math.abs(oyuncuD.AktifKonumu[1] - oyuncuC.hedefAltinKonum[1]);
            if (dUzaklik > cUzaklik && dUzaklik > 3) {
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
            System.out.println("hedef yok");
            for (int i = 0; i < dAltinlar.size(); i++) {
                int tempxuzaklik, tempyuzaklik;
                tempxuzaklik = oyuncuD.AktifKonumu[0] - dAltinlar.get(i).konum[0];
                tempyuzaklik = oyuncuD.AktifKonumu[1] - dAltinlar.get(i).konum[1];

                int kar = (dAltinlar.get(i).altinMiktari - (((Math.abs(tempxuzaklik) + Math.abs(tempyuzaklik)) * Integer.parseInt(BHamleMaaliyet1.getText())) + Integer.parseInt(BHedefMaaliyet.getText())));
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
            System.out.println("hedef var");
            for (int i = 0; i < dAltinlar.size(); i++) {
                if (dAltinlar.get(i).konum[0] == oyuncuD.hedefAltinKonum[0] && dAltinlar.get(i).konum[1] == oyuncuD.hedefAltinKonum[1]) {
                    silinecekAltinIndex = i;
                }
            }
            xuzaklik = oyuncuD.AktifKonumu[0] - oyuncuD.hedefAltinKonum[0];
            yuzaklik = oyuncuD.AktifKonumu[1] - oyuncuD.hedefAltinKonum[1];
            hedefMaliyeti = 0;
        }
        System.out.println(xuzaklik + " D için" + yuzaklik);
        int adim = 0;
        System.out.println(oyuncuD.hedefAltinKonum[0] + " x hedeflenen" + oyuncuD.hedefAltinKonum[1] + "y hedeflenen");
        JButtonKare[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].setText("<html>" + kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].altinMiktari + "<br>" + "X" + "</html>");

        adim = hareketEttir(xuzaklik, yuzaklik, adim, oyuncuD, "D", Color.GREEN);
        System.out.println("adim gibi adim: " + adim);

        //System.out.println(oyuncuD.AktifKonumu[0]+"m "+oyuncuD.AktifKonumu[1]+"u "+oyuncuD.hedefAltinKonum[0]+" b"+oyuncuD.hedefAltinKonum[1]);
        oyuncuD.harcananAltinMiktari += Integer.parseInt(BHamleMaaliyet1.getText()) * adim + hedefMaliyeti;
        oyuncuD.mevcutAltinMiktari = oyuncuD.mevcutAltinMiktari - oyuncuD.harcananAltinMiktari;
        oyuncuD.kasadakiAltinMiktari += oyuncuD.harcananAltinMiktari;
        if (oyuncuD.AktifKonumu[0] == oyuncuD.hedefAltinKonum[0] && oyuncuD.AktifKonumu[1] == oyuncuD.hedefAltinKonum[1]) {
            oyuncuD.mevcutAltinMiktari += kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].altinMiktari;
            oyuncuD.toplananAltinMiktari += kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].altinMiktari;

        }
        if (Math.abs(yuzaklik) + Math.abs(xuzaklik) <= 3) {//eğer altın a kullanıcısının adımları içerisinde ise altını alacağı için listeden silmemiz gerekir
            altinlar.remove(dAltinlar.get(silinecekAltinIndex));
            kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].altinMiktari = 0;
            kareler[oyuncuD.hedefAltinKonum[0]][oyuncuD.hedefAltinKonum[1]].gizli = false;
            oyuncuD.hedefAltinKonum[0] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
            oyuncuD.hedefAltinKonum[1] = -1;    //oyuncu hedefini tmamladığı için artık bir hedefi yok
        }

        System.out.println(
                Integer.parseInt(CHamleMaaliyet1.getText()) * adim + hedefMaliyeti + "harcanan:" + oyuncuD.harcananAltinMiktari + " kasadakiAltın" + oyuncuD.kasadakiAltinMiktari + " Mevcut altın" + oyuncuD.mevcutAltinMiktari + "toplanan altın" + oyuncuD.toplananAltinMiktari);

    }

    public int hareketEttir(int xuzaklik, int yuzaklik, int adim, Oyuncu oyuncu, String oyuncuIsmi, Color renk) {
        if (xuzaklik < 0 && yuzaklik < 0) {
            for (int i = oyuncu.AktifKonumu[0]; i < oyuncu.hedefAltinKonum[0]; i++) {
                if (adim < 3) {

                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] + 1; // A için x in konumunu arttırdık veya azalttık.
                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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

                                tmpKonumx1 = tmpKonumx1 + 1; // A için x in konumunu arttırdık veya azalttık.
                                //System.out.println(oyuncu.AktifKonumu[0]+"cc");
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
                if (adim < 3) {
                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] + 1;

                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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

        if (xuzaklik < 0 && yuzaklik == 0) {
            for (int i = oyuncu.AktifKonumu[0]; i < oyuncu.hedefAltinKonum[0]; i++) {
                if (adim < 3) {

                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] + 1; // A için x in konumunu arttırdık veya azalttık.
                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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

                                tmpKonumx1 = tmpKonumx1 + 1; // A için x in konumunu arttırdık veya azalttık.
                                //System.out.println(oyuncu.AktifKonumu[0]+"cc");
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

            System.out.println("kalbim sende");
            for (int i = oyuncu.AktifKonumu[0]; i > oyuncu.hedefAltinKonum[0]; i--) {
                if (adim < 3) {

                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] - 1; // A için x in konumunu arttırdık veya azalttık.
                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
                                tmpKonumx1 = tmpKonumx1 - 1; // A için x in konumunu arttırdık veya azalttık.
                                //System.out.println(oyuncu.AktifKonumu[0]+"cc");
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
                if (adim < 3) {
                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] + 1;

                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
                if (adim < 3) {

                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] - 1; // A için x in konumunu arttırdık veya azalttık.
                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
                                tmpKonumx1 = tmpKonumx1 - 1; // A için x in konumunu arttırdık veya azalttık.
                                //System.out.println(oyuncu.AktifKonumu[0]+"cc");
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
                if (adim < 3) {

                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] + 1; // A için x in konumunu arttırdık veya azalttık.
                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
                                tmpKonumx1 = tmpKonumx1 + 1; // A için x in konumunu arttırdık veya azalttık.
                                //System.out.println(oyuncu.AktifKonumu[0]+"cc");
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
                if (adim < 3) {
                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] - 1;

                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
                if (adim < 3) {

                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] - 1; // A için x in konumunu arttırdık veya azalttık.
                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
                                tmpKonumx1 = tmpKonumx1 - 1; // A için x in konumunu arttırdık veya azalttık.
                                //System.out.println(oyuncu.AktifKonumu[0]+"cc");
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
                if (adim < 3) {
                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] - 1;

                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
                if (adim < 3) {
                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] - 1;

                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
                if (adim < 3) {

                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[0] = oyuncu.AktifKonumu[0] + 1; // A için x in konumunu arttırdık veya azalttık.
                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
                                tmpKonumx1 = tmpKonumx1 + 1; // A için x in konumunu arttırdık veya azalttık.
                                //System.out.println(oyuncu.AktifKonumu[0]+"cc");
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
                if (adim < 3) {
                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] + 1;

                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
                if (adim < 3) {
                    adim++;
                    int sleep = adim * 750;

                    int tmpKonumx = oyuncu.AktifKonumu[0];
                    int tmpKonumy = oyuncu.AktifKonumu[1];
                    oyuncu.AktifKonumu[1] = oyuncu.AktifKonumu[1] + 1;

                    System.out.println("x=" + oyuncu.AktifKonumu[0] + " y=" + oyuncu.AktifKonumu[1]);
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
        AHedefMaaliyet1 = new javax.swing.JTextField();
        AHamleMaaliyet = new javax.swing.JTextField();
        BHamleMaaliyet1 = new javax.swing.JTextField();
        CHamleMaaliyet1 = new javax.swing.JTextField();
        dikdötgenBoyutText = new javax.swing.JLabel();
        mevcutAltinText = new javax.swing.JLabel();
        altinOraniText = new javax.swing.JLabel();
        gizliAltinOraniText = new javax.swing.JLabel();
        adimSayisiText = new javax.swing.JLabel();
        hedef = new javax.swing.JLabel();
        hamle1 = new javax.swing.JLabel();
        boynaa = new javax.swing.JButton();
        aoynaa = new javax.swing.JButton();
        coynaa = new javax.swing.JButton();
        doynaa = new javax.swing.JButton();

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
        tahtaX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tahtaXActionPerformed(evt);
            }
        });
        kontrolAlani.add(tahtaX);
        tahtaX.setBounds(120, 0, 50, 30);

        tahtaY.setText("800");
        tahtaY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tahtaYActionPerformed(evt);
            }
        });
        kontrolAlani.add(tahtaY);
        tahtaY.setBounds(200, 0, 50, 30);

        basla.setText("BAŞLA");
        basla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baslaActionPerformed(evt);
            }
        });
        kontrolAlani.add(basla);
        basla.setBounds(90, 540, 54, 28);

        kareKenar.setText("20");
        kareKenar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                kareKenarKeyTyped(evt);
            }
        });
        kontrolAlani.add(kareKenar);
        kareKenar.setBounds(130, 40, 40, 30);

        carpiText.setText("X");
        kontrolAlani.add(carpiText);
        carpiText.setBounds(180, 10, 30, 18);

        kareBoyutText.setText("Karenin Boyutu:");
        kontrolAlani.add(kareBoyutText);
        kareBoyutText.setBounds(10, 50, 130, 18);

        mevcutAltin.setText("200");
        kontrolAlani.add(mevcutAltin);
        mevcutAltin.setBounds(120, 80, 50, 30);

        altinOrani.setText("20");
        kontrolAlani.add(altinOrani);
        altinOrani.setBounds(120, 120, 40, 30);

        gizliAltinOrani.setText("10");
        gizliAltinOrani.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gizliAltinOraniActionPerformed(evt);
            }
        });
        kontrolAlani.add(gizliAltinOrani);
        gizliAltinOrani.setBounds(120, 160, 40, 30);

        adimSayisi.setText("3");
        adimSayisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adimSayisiActionPerformed(evt);
            }
        });
        kontrolAlani.add(adimSayisi);
        adimSayisi.setBounds(100, 200, 30, 30);

        DHedefMaaliyet.setText("20");
        kontrolAlani.add(DHedefMaaliyet);
        DHedefMaaliyet.setBounds(10, 480, 120, 35);

        DHamleMaaliyet.setText("5");
        kontrolAlani.add(DHamleMaaliyet);
        DHamleMaaliyet.setBounds(130, 480, 120, 35);

        BHedefMaaliyet.setText("10");
        kontrolAlani.add(BHedefMaaliyet);
        BHedefMaaliyet.setBounds(10, 400, 120, 35);

        CHedefMaaliyet.setText("15");
        kontrolAlani.add(CHedefMaaliyet);
        CHedefMaaliyet.setBounds(10, 440, 120, 35);

        AHedefMaaliyet1.setText("5");
        kontrolAlani.add(AHedefMaaliyet1);
        AHedefMaaliyet1.setBounds(10, 360, 120, 35);

        AHamleMaaliyet.setText("5");
        kontrolAlani.add(AHamleMaaliyet);
        AHamleMaaliyet.setBounds(130, 360, 120, 35);

        BHamleMaaliyet1.setText("5");
        kontrolAlani.add(BHamleMaaliyet1);
        BHamleMaaliyet1.setBounds(130, 400, 120, 35);

        CHamleMaaliyet1.setText("5");
        kontrolAlani.add(CHamleMaaliyet1);
        CHamleMaaliyet1.setBounds(130, 440, 120, 35);

        dikdötgenBoyutText.setText("Tahta Boyutu :");
        kontrolAlani.add(dikdötgenBoyutText);
        dikdötgenBoyutText.setBounds(10, 10, 110, 18);

        mevcutAltinText.setText("Mevcut Altın");
        kontrolAlani.add(mevcutAltinText);
        mevcutAltinText.setBounds(10, 90, 100, 18);

        altinOraniText.setText("Altın Oranı");
        kontrolAlani.add(altinOraniText);
        altinOraniText.setBounds(10, 130, 90, 18);

        gizliAltinOraniText.setText("Gizli Altın Oranı");
        kontrolAlani.add(gizliAltinOraniText);
        gizliAltinOraniText.setBounds(10, 170, 110, 18);

        adimSayisiText.setText("Adım Sayısı");
        kontrolAlani.add(adimSayisiText);
        adimSayisiText.setBounds(10, 210, 100, 18);

        hedef.setText("Hedef");
        kontrolAlani.add(hedef);
        hedef.setBounds(40, 320, 49, 18);

        hamle1.setText("Hamle");
        kontrolAlani.add(hamle1);
        hamle1.setBounds(160, 320, 49, 18);

        boynaa.setText("b Oyna");
        boynaa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boynaaActionPerformed(evt);
            }
        });
        kontrolAlani.add(boynaa);
        boynaa.setBounds(40, 640, 150, 28);

        aoynaa.setText("a Oyna");
        aoynaa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aoynaaActionPerformed(evt);
            }
        });
        kontrolAlani.add(aoynaa);
        aoynaa.setBounds(30, 600, 150, 28);

        coynaa.setText("c Oyna");
        coynaa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coynaaActionPerformed(evt);
            }
        });
        kontrolAlani.add(coynaa);
        coynaa.setBounds(40, 680, 150, 28);

        doynaa.setText("d Oyna");
        doynaa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doynaaActionPerformed(evt);
            }
        });
        kontrolAlani.add(doynaa);
        doynaa.setBounds(50, 720, 150, 28);

        getContentPane().add(kontrolAlani);
        kontrolAlani.setBounds(800, 0, 250, 800);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tahtaYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tahtaYActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tahtaYActionPerformed

    private void tahtaXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tahtaXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tahtaXActionPerformed

    private void kareKenarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kareKenarKeyTyped

    }//GEN-LAST:event_kareKenarKeyTyped

    private void gizliAltinOraniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gizliAltinOraniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gizliAltinOraniActionPerformed

    private void adimSayisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adimSayisiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adimSayisiActionPerformed

    private void baslaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baslaActionPerformed
        oyunOlustur();

    }//GEN-LAST:event_baslaActionPerformed

    private void boynaaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boynaaActionPerformed
        BOyna();
    }//GEN-LAST:event_boynaaActionPerformed

    private void aoynaaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aoynaaActionPerformed
        AOyna();
    }//GEN-LAST:event_aoynaaActionPerformed

    private void coynaaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coynaaActionPerformed
        COyna();
    }//GEN-LAST:event_coynaaActionPerformed

    private void doynaaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doynaaActionPerformed
        DOyna();
    }//GEN-LAST:event_doynaaActionPerformed

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
                new Arayüz().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AHamleMaaliyet;
    private javax.swing.JTextField AHedefMaaliyet1;
    private javax.swing.JTextField BHamleMaaliyet1;
    private javax.swing.JTextField BHedefMaaliyet;
    private javax.swing.JTextField CHamleMaaliyet1;
    private javax.swing.JTextField CHedefMaaliyet;
    private javax.swing.JTextField DHamleMaaliyet;
    private javax.swing.JTextField DHedefMaaliyet;
    private javax.swing.JTextField adimSayisi;
    private javax.swing.JLabel adimSayisiText;
    private javax.swing.JTextField altinOrani;
    private javax.swing.JLabel altinOraniText;
    private javax.swing.JButton aoynaa;
    private javax.swing.JButton basla;
    private javax.swing.JButton boynaa;
    private javax.swing.JLabel carpiText;
    private javax.swing.JButton coynaa;
    private javax.swing.JLabel dikdötgenBoyutText;
    private javax.swing.JButton doynaa;
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
