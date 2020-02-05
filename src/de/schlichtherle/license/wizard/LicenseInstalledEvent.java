/*
 * LicenseInstalledEvent.java
 *
 * Created on 20. Februar 2005, 21:01
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
import de.schlichtherle.license.LicenseContent;

/**
 * Fired when a license certificate has been successfully installed.
 *
 * @author Christian Schlichtherle
 */
public class LicenseInstalledEvent extends java.util.EventObject {
    
    private final LicenseContent content;
    
    /**
     * Creates a new instance of LicenseInstalledEvent.
     *
     * @param content The license content that has been successfully installed.
     */
    public LicenseInstalledEvent(Object source, LicenseContent content) {
        super(source);
        this.content = content;
    }
    
    public LicenseContent getContent() {
        return content;
    }
}
