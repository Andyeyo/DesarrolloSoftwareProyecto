/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinaldes;

import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;

/**
 *
 * @author Andres
 */


public class Editor_name extends DefaultCellEditor {
  public Editor_name(JCheckBox checkBox) {
   super(checkBox);
  }
  @Override
  public boolean isCellEditable(EventObject anEvent) {
    return false;
  }
}