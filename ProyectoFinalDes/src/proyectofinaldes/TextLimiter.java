/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinaldes;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Andres
 */

public class TextLimiter extends PlainDocument {
    @Override
    public void insertString(int offset,String str, AttributeSet atrr) throws BadLocationException{
        super.insertString(offset , str.toUpperCase(),atrr);
    }
}
