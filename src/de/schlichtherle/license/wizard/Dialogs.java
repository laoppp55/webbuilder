/*
 * Dialogs.java
 *
 * Created on 13. Dezember 2004, 19:33
 */
/*
 * Copyright 2005 Schlichtherle IT Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.schlichtherle.license.wizard;

import java.awt.*;

import javax.swing.*;

/**
 * A simple wrapper for JOptionPane dialogs to provide additional comfort.
 *
 * @author  Christian Schlichtherle
 */
public class Dialogs extends JOptionPane {
    
    public static void showMessageDialog(Component parentComponent,
                                         Object message,
                                         String title,
                                         int messageType) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }
    
    /** You cannot instantiate this class. */
    protected Dialogs() { }
}
