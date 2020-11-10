
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;

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
    private JButton[][] kare = new JButton[40][40];
    public static int tahtaXSayisi;
    public static int tahtaYSayisi;
    ButtonHandler buttonHandler = new ButtonHandler();

    public Arayüz() {
        initComponents();
        EkranX = (int) kit.getScreenSize().width; //Ekran boyutunun genişliğini alıyoruz...
        EkranY = (int) kit.getScreenSize().height;//Ekran boyutunun yüksekliğini alıyoruz...
        this.setLocation((EkranX - 1050) / 2, (EkranY - 800) / 2);  // Görünüm olarak açılan pencerenin ekranın tam ortasında çıkması için
        this.setSize(1050, 800);
    }

    public void oyunOlustur() {
        if (Integer.parseInt(tahtaX.getText()) < EkranX - 250 && Integer.parseInt(tahtaY.getText()) < EkranY && !kareKenar.getText().isEmpty()) {
            boyutOrantıla();
            oyunAlani.setSize(Integer.parseInt(tahtaX.getText()), Integer.parseInt(tahtaY.getText()));
            kontrolAlani.setSize(250, Integer.parseInt(tahtaY.getText()));
            this.setSize(250 + Integer.parseInt(tahtaX.getText()), Integer.parseInt(tahtaY.getText()));
            kontrolAlani.setLocation(Integer.parseInt(tahtaX.getText()), 0);
            this.setLocation((EkranX - Integer.parseInt(tahtaX.getText()) - 250) / 2, (EkranY - Integer.parseInt(tahtaY.getText())) / 2);
            
              System.out.println(tahtaXSayisi);
            System.out.println(tahtaYSayisi);
            for (int i = 0; i < 40; i++) {
                for (int j = 0; j < 40; j++) {
                    kare[i][j] = new JButton();
                    if((i+j)%2!=0){
                     kare[i][j].setBackground(Color.black);
                    }
                    oyunAlani.add(kare[i][j]);
                   
                    kare[i][j].addActionListener(buttonHandler);

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
        jLabel2 = new javax.swing.JLabel();
        kareText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(800, 800));
        getContentPane().setLayout(null);

        oyunAlani.setBackground(new java.awt.Color(255, 102, 102));
        oyunAlani.setLayout(new java.awt.GridLayout());
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
        tahtaX.setBounds(10, 0, 60, 22);

        tahtaY.setText("800");
        tahtaY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tahtaYActionPerformed(evt);
            }
        });
        kontrolAlani.add(tahtaY);
        tahtaY.setBounds(110, 0, 80, 22);

        basla.setText("BAŞLA");
        basla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baslaActionPerformed(evt);
            }
        });
        kontrolAlani.add(basla);
        basla.setBounds(80, 510, 69, 25);

        kareKenar.setText("20");
        kareKenar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                kareKenarKeyTyped(evt);
            }
        });
        kontrolAlani.add(kareKenar);
        kareKenar.setBounds(110, 40, 80, 30);

        jLabel2.setText("X");
        kontrolAlani.add(jLabel2);
        jLabel2.setBounds(90, 0, 41, 16);

        kareText.setText("Karenin Boyutu:");
        kontrolAlani.add(kareText);
        kareText.setBounds(10, 50, 180, 16);

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
            public void run() {
                new Arayüz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton basla;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField kareKenar;
    private javax.swing.JLabel kareText;
    private javax.swing.JPanel kontrolAlani;
    private javax.swing.JPanel oyunAlani;
    private javax.swing.JTextField tahtaX;
    private javax.swing.JTextField tahtaY;
    // End of variables declaration//GEN-END:variables
}
