/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package gui;

import model.MySQL;

/**
 *
 * @author NIDULA
 */
public class statusChangeAdmin extends javax.swing.JDialog {

    Users us;
    ViewProducts vp;
    int to_updRow;
    int sta;

    /**
     * Creates new form success
     */
    public statusChangeAdmin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

    }

    public statusChangeAdmin(java.awt.Frame parent, boolean modal, Users us, int to_upr, int st) {
        super(parent, modal);
        initComponents();
        this.us = us;
        this.to_updRow = to_upr;
        this.sta = st;
        if (sta == 1) {
            if (us.jTable1.getValueAt(to_updRow, 6).toString().equals("Active")) {
                jButton1.setText("Deactive");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/approved.png")));
                jLabel2.setText("Active");
            } else {
                jButton1.setText("Active");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/uncheck.png")));
                jLabel2.setText("Deactive");
            }
        } else if (sta == 2) {
            if (us.jTable2.getValueAt(to_updRow, 5).toString().equals("Active")) {
                jButton1.setText("Deactive");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/approved.png")));
                jLabel2.setText("Active");
            } else {
                jButton1.setText("Active");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/uncheck.png")));
                jLabel2.setText("Deactive");
            }
        }

    }

    public statusChangeAdmin(java.awt.Frame parent, boolean modal, ViewProducts vp, int to_upr, int st) {
        super(parent, modal);
        initComponents();
        this.vp = vp;
        this.to_updRow = to_upr;
        this.sta = st;
        if (sta == 3) {
            if (vp.jTable1.getValueAt(to_updRow, 5).toString().equals("Active")) {
                jButton1.setText("Deactive");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/approved.png")));
                jLabel2.setText("Active");
            } else {
                jButton1.setText("Active");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/uncheck.png")));
                jLabel2.setText("Deactive");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Status Change");
        setResizable(false);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/approved.png"))); // NOI18N

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("jLabel2");

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton2.setText("Done");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (sta == 1) {
            String uid = us.jTable1.getValueAt(to_updRow, 0).toString();
            String st = "0";

            if (jButton1.getText().equals("Active")) {
                st = "1";
            } else {
                st = "2";
            }

            MySQL.iud("UPDATE `user` SET `ststus_id` = '" + st + "' WHERE `user`.`id` = '" + uid + "';");
            us.searchAdmins();
            if (us.jTable1.getValueAt(to_updRow, 6).toString().equals("Active")) {
                jButton1.setText("Deactive");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/approved.png")));
                jLabel2.setText("Active");
            } else {
                jButton1.setText("Active");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/uncheck.png")));
                jLabel2.setText("Deactive");
            }
        } else if (sta == 2) {
            String uid = us.jTable2.getValueAt(to_updRow, 0).toString();
            String st = "0";

            if (jButton1.getText().equals("Active")) {
                st = "1";
            } else {
                st = "2";
            }

            MySQL.iud("UPDATE `user` SET `ststus_id` = '" + st + "' WHERE `user`.`id` = '" + uid + "';");
            us.searchCashiers();
            if (us.jTable2.getValueAt(to_updRow, 5).toString().equals("Active")) {
                jButton1.setText("Deactive");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/approved.png")));
                jLabel2.setText("Active");
            } else {
                jButton1.setText("Active");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/uncheck.png")));
                jLabel2.setText("Deactive");
            }
        } else if (sta == 3) {
            String uid = vp.jTable1.getValueAt(to_updRow, 0).toString();
            String st = "0";

            if (jButton1.getText().equals("Active")) {
                st = "1";
            } else {
                st = "2";
            }

            MySQL.iud("UPDATE `product` SET `ststus_id` = '" + st + "' WHERE `product`.`id` = '" + uid + "';");
            vp.loadProducts();
            if (vp.jTable1.getValueAt(to_updRow, 5).toString().equals("Active")) {
                jButton1.setText("Deactive");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/approved.png")));
                jLabel2.setText("Active");
            } else {
                jButton1.setText("Active");
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/uncheck.png")));
                jLabel2.setText("Deactive");
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(statusChangeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(statusChangeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(statusChangeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(statusChangeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                statusChangeAdmin dialog = new statusChangeAdmin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
