package tempviewer;

public class TempViewer extends javax.swing.JFrame {

    public TempViewer() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tempSensor1 = new tempsensor.TempSensor();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        tempSensor1.addPropertyChangeListener(evt -> tempSensor1PropertyChange());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Sense Temp");
        jButton1.addActionListener(evt -> jButton1ActionPerformed());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(97, 97, 97))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addContainerGap(209, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed() {//GEN-FIRST:event_jButton1ActionPerformed
        tempSensor1.go();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tempSensor1PropertyChange() {//GEN-FIRST:event_tempSensor1PropertyChange
        jLabel1.setText(Float.toString(tempSensor1.getCurrentTemp()));
    }//GEN-LAST:event_tempSensor1PropertyChange
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TempViewer().setVisible(true));
    }
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private tempsensor.TempSensor tempSensor1;
}
