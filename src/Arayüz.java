
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.LineBorder;

import javax.swing.border.LineBorder;

import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;


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

        Random rnd = new Random();
        if (Integer.parseInt(tahtaX.getText()) < EkranX - 250 && Integer.parseInt(tahtaY.getText()) < EkranY && !kareKenar.getText().isEmpty()) {
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
                    System.out.println(x + "mb " + y);
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
                    System.out.println(x + "mb " + y);
                }
            }
            setResizable(false);
            setVisible(true);
        }
        AOyna();

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

        JButtonKare[0][tahtaXSayisi - 1].setBackground(Color.black);
        JButtonKare[0][tahtaXSayisi - 1].setText("B");
        oyuncuB.ad = "B";
        oyuncuB.mevcutAltinMiktari = Integer.parseInt(mevcutAltin.getText());

        JButtonKare[tahtaYSayisi - 1][tahtaXSayisi - 1].setBackground(Color.blue);
        JButtonKare[tahtaYSayisi - 1][tahtaXSayisi - 1].setText("C");
        oyuncuC.ad = "C";
        oyuncuC.mevcutAltinMiktari = Integer.parseInt(mevcutAltin.getText());

        JButtonKare[tahtaYSayisi - 1][0].setBackground(Color.GREEN);
        JButtonKare[tahtaYSayisi - 1][0].setText("D");
        oyuncuD.ad = "D";
        oyuncuD.mevcutAltinMiktari = Integer.parseInt(mevcutAltin.getText());

    }

    public void AOyna() {
        int enkAdim = Integer.MAX_VALUE;
        int xuzaklik = 0, yuzaklik = 0;
        
        for (int i = 0; i < altinlar.size(); i++) {
            System.out.println(altinlar.get(i).konum[0] + " " + altinlar.get(i).konum[1]);
            int tempxuzaklik, tempyuzaklik;
            tempxuzaklik = oyuncuA.AktifKonumu[0] - altinlar.get(i).konum[0];
            tempyuzaklik = oyuncuA.AktifKonumu[1] - altinlar.get(i).konum[1];

            if (Math.abs(tempxuzaklik + tempyuzaklik) < enkAdim) {
                enkAdim = Math.abs(tempxuzaklik + tempyuzaklik);
                xuzaklik = tempxuzaklik;
                yuzaklik = tempyuzaklik;
            }
        }
        System.out.println(xuzaklik + " " + yuzaklik + " " + enkAdim);
        int adim = 0;

        Timer timer = null;
        for (int i = 0; i < Math.abs(xuzaklik); i++) {
            if (adim < 3) {

                adim++;
                int eksilecek = Integer.signum(xuzaklik);

                timer = new Timer(2000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setBackground(Color.WHITE);
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setText("");
                        oyuncuA.AktifKonumu[0] = oyuncuA.AktifKonumu[0] - eksilecek; // A için x in konumunu arttırdık veya azalttık.
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setBackground(Color.RED);
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setText("A");
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setBackground(Color.RED);
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setText("A");
                        repaint();
                    }
                    
                });

                timer.setRepeats(false);
               
                timer.start();

            }

        }
        for (int i = 0; i < Math.abs(yuzaklik); i++) {
            if (adim < 3) {
                adim++;
                int eksilecek = Integer.signum(yuzaklik);

                timer = new Timer(2000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setBackground(Color.WHITE);
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setText("");
                        oyuncuA.AktifKonumu[1] = oyuncuA.AktifKonumu[1] - eksilecek; // A için x in konumunu arttırdık veya azalttık.
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setBackground(Color.RED);
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setText("A");
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setBackground(Color.RED);
                        JButtonKare[oyuncuA.AktifKonumu[0]][oyuncuA.AktifKonumu[1]].setText("A");

                        repaint();

                    }
                });

                timer.setRepeats(false);
                
                timer.start();
               
            }
        }

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
        basla.setBounds(80, 510, 54, 28);

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

        DHedefMaaliyet.setText("D Oyuncusu hedef maaliyeti");
        kontrolAlani.add(DHedefMaaliyet);
        DHedefMaaliyet.setBounds(10, 440, 120, 35);

        DHamleMaaliyet.setText("D Oyuncusu hamle maaliyeti");
        kontrolAlani.add(DHamleMaaliyet);
        DHamleMaaliyet.setBounds(130, 440, 120, 35);

        BHedefMaaliyet.setText("B Oyuncusu hedef maaliyeti");
        kontrolAlani.add(BHedefMaaliyet);
        BHedefMaaliyet.setBounds(10, 350, 120, 35);

        CHedefMaaliyet.setText("C Oyuncusu hedef maaliyeti");
        kontrolAlani.add(CHedefMaaliyet);
        CHedefMaaliyet.setBounds(10, 400, 120, 35);

        AHedefMaaliyet1.setText("A Oyuncusu hedef maaliyeti");
        kontrolAlani.add(AHedefMaaliyet1);
        AHedefMaaliyet1.setBounds(10, 300, 120, 35);

        AHamleMaaliyet.setText("A Oyuncusu hamle maaliyeti");
        kontrolAlani.add(AHamleMaaliyet);
        AHamleMaaliyet.setBounds(130, 300, 120, 35);

        BHamleMaaliyet1.setText("B Oyuncusu hamle maaliyeti");
        kontrolAlani.add(BHamleMaaliyet1);
        BHamleMaaliyet1.setBounds(130, 350, 120, 35);

        CHamleMaaliyet1.setText("C Oyuncusu hamle maaliyeti");
        kontrolAlani.add(CHamleMaaliyet1);
        CHamleMaaliyet1.setBounds(130, 400, 120, 35);

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

        getContentPane().add(kontrolAlani);
        kontrolAlani.setBounds(800, 0, 250, 800);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void baslaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baslaActionPerformed
        // TODO add your handling code here:
        oyunOlustur();
    }//GEN-LAST:event_baslaActionPerformed

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
            java.util.logging.Logger.getLogger(Arayüz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Arayüz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Arayüz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Arayüz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton basla;
    private javax.swing.JLabel carpiText;
    private javax.swing.JLabel dikdötgenBoyutText;
    private javax.swing.JTextField gizliAltinOrani;
    private javax.swing.JLabel gizliAltinOraniText;
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
