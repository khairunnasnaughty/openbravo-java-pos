//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007 Openbravo, S.L.
//    http://sourceforge.net/projects/openbravopos
//
//    This program is free software; you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation; either version 2 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program; if not, write to the Free Software
//    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package com.openbravo.pos.config;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.openbravo.basic.BasicException;

import com.openbravo.pos.forms.*;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.gui.JMessageDialog;
import java.io.File;

/**
 *
 * @author adrianromero
 */
public class JPanelConfiguration extends JPanel implements JPanelView {
        
    private List<PanelConfig> m_panelconfig;
    private File configfile;
    
    /** Creates new form JPanelConfiguration */
    public JPanelConfiguration(AppView oApp) {
        this(oApp.getProperties());  
    }
    
    public JPanelConfiguration(AppProperties props) {
        
        configfile = props.getConfigFile();        
        
        initComponents();
        
        // Inicio lista de paneles
        m_panelconfig = new ArrayList<PanelConfig>();
        m_panelconfig.add(new JPanelConfigDatabase());
        m_panelconfig.add(new JPanelConfigGeneral());
        m_panelconfig.add(new JPanelConfigLocale());
        m_panelconfig.add(new JPanelConfigPayment());
        
        // paneles auxiliares
        for (PanelConfig c: m_panelconfig) {
            m_jConfigOptions.add(c.getConfigComponent());
        }
    }
        
    private void restoreProperties() {
        
        AppConfig config = new AppConfig(configfile);
        if (config.delete()) {
            loadProperties();
        } else {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotdeleteconfig")));            
        }
    }
    
    private void loadProperties() {
        
        AppConfig config = new AppConfig(configfile);
        config.load();
        
        // paneles auxiliares
        for (PanelConfig c: m_panelconfig) {
            c.loadProperties(config);
        }
    }
    
    private void saveProperties() {
        
        AppConfig config = new AppConfig(configfile);
        
        // paneles auxiliares
        for (PanelConfig c: m_panelconfig) {
            c.saveProperties(config);
        }
        
        try {
            config.save();
            JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.restartchanges"), AppLocal.getIntString("message.title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotsaveconfig"), e));
        }
    }

    public JComponent getComponent() {
        return this;
    }
    
    public String getTitle() {
        return AppLocal.getIntString("Menu.Configuration");
    } 
    
    public void activate() throws BasicException {
        loadProperties();        
    }
    
    public boolean deactivate() {
        
        boolean haschanged = false;
        for (PanelConfig c: m_panelconfig) {
            if (c.hasChanged()) {
                haschanged = true;
            }
        }        
        
        
        if (haschanged) {
            int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannasave"), AppLocal.getIntString("title.editor"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                saveProperties();
                return true;
            } else {
                return res == JOptionPane.NO_OPTION;
            }
        } else {
            return true;
        }
    }      

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        m_jConfigOptions = new javax.swing.JPanel();
        jbtnCancel = new javax.swing.JButton();
        jbtnRestore = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();

        m_jConfigOptions.setLayout(new javax.swing.BoxLayout(m_jConfigOptions, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(m_jConfigOptions);

        jbtnCancel.setText(AppLocal.getIntString("Button.Restore")); // NOI18N
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        jbtnRestore.setText(AppLocal.getIntString("Button.Factory")); // NOI18N
        jbtnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRestoreActionPerformed(evt);
            }
        });

        jbtnSave.setText(AppLocal.getIntString("Button.Save")); // NOI18N
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnRestore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnCancel)
                    .addComponent(jbtnRestore)
                    .addComponent(jbtnSave))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelActionPerformed

        if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.configrestore"), AppLocal.getIntString("message.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {          
            loadProperties();
        }
        
    }//GEN-LAST:event_jbtnCancelActionPerformed

    private void jbtnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRestoreActionPerformed

        if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.configfactory"), AppLocal.getIntString("message.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {          
            restoreProperties();
        }
        
    }//GEN-LAST:event_jbtnRestoreActionPerformed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed

        saveProperties();
        
    }//GEN-LAST:event_jbtnSaveActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnRestore;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JPanel m_jConfigOptions;
    // End of variables declaration//GEN-END:variables
    
}
