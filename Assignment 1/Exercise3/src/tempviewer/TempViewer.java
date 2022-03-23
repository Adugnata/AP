
package tempviewer;

import java.beans.PropertyVetoException;

public class TempViewer extends javax.swing.JFrame {

    float low;
    float high;

    public TempViewer() {
        initComponents();
        low = 0;
        high = 30;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tempSensorVetoable2 = new tempsensor.TempSensorVetoable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        tempSensorVetoable2.addVetoableChangeListener(evt -> tempSensorVetoable2VetoableChange(evt));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Sense Temp");
        jButton1.addActionListener(evt -> jButton1ActionPerformed());

        jToggleButton1.setText("Limit Temp");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jToggleButton1)
                                .addContainerGap(175, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed() {//GEN-FIRST:event_jButton1ActionPerformed
        tempSensorVetoable2.go();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tempSensorVetoable2VetoableChange(java.beans.PropertyChangeEvent evt) throws PropertyVetoException {//GEN-FIRST:event_tempSensorVetoable2VetoableChange
        if (jToggleButton1.isSelected()) {
            float newVal = (float) evt.getNewValue();
            if (newVal < low || newVal > high) {
                System.out.println("Out of range");
                throw new PropertyVetoException("Out of range", evt);
            } else {
                jLabel1.setText(Float.toString(tempSensorVetoable2.getCurrentTemp()));
            }
        } else
            jLabel1.setText(Float.toString(tempSensorVetoable2.getCurrentTemp()));
    }//GEN-LAST:event_tempSensorVetoable2VetoableChange

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TempViewer().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToggleButton jToggleButton1;
    private tempsensor.TempSensorVetoable tempSensorVetoable2;
    // End of variables declaration//GEN-END:variables
}
