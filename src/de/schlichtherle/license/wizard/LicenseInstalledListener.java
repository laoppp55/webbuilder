/*
 * LicenseInstalledListener.java
 *
 * Created on 20. Februar 2005, 20:41
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

/**
 * Instances of this class can be notified when a license certificate is
 * successfully installed.
 *
 * @author Christian Schlichtherle
 */
public interface LicenseInstalledListener extends java.util.EventListener {
    
    public abstract void licenseInstalled(LicenseInstalledEvent event);
}
