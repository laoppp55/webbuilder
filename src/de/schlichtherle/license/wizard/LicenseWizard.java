/*
 * LicenseWizard.java
 *
 * Created on 21. Februar 2005, 18:57
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

import com.nexes.wizard.*;

import de.schlichtherle.license.LicenseManager;

import java.awt.Dialog;
import java.awt.Frame;

/**
 * This is the internationalised license management wizard class.
 * It can be used to install and verify licenses visually for the
 * application's user.
 *
 * @author Christian Schlichtherle
 */
public class LicenseWizard extends Wizard {
    
    /** Creates a new instance of LicenseWizard */
    public LicenseWizard(LicenseManager manager) {
        init(manager);
    }

    /** Creates a new instance of LicenseWizard */
    public LicenseWizard(LicenseManager manager, Dialog owner) {
        super(owner);
        init(manager);
    }

    /** Creates a new instance of LicenseWizard */
    public LicenseWizard(LicenseManager manager, Frame owner) {
        super(owner);
        init(manager);
    }

    private void init(final LicenseManager manager) {
        WizardPanelDescriptor descriptor1
                = new WelcomePanel.Descriptor(manager);
        registerWizardPanel(
                WelcomePanel.Descriptor.IDENTIFIER, descriptor1);

        WizardPanelDescriptor descriptor2
                = new InstallPanel.Descriptor(manager);
        registerWizardPanel(
                InstallPanel.Descriptor.IDENTIFIER, descriptor2);

        WizardPanelDescriptor descriptor3
                = new LicensePanel.Descriptor(manager);
        registerWizardPanel(
                LicensePanel.Descriptor.IDENTIFIER, descriptor3);

        WizardPanelDescriptor descriptor4
                = new UninstallPanel.Descriptor(manager);
        registerWizardPanel(
                UninstallPanel.Descriptor.IDENTIFIER, descriptor4);

        setCurrentPanel(WelcomePanel.Descriptor.IDENTIFIER);
    }
}
