/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io._29cu.usmserver.controllers.rest.resources;

import io._29cu.usmserver.core.model.entities.Installer;
import io._29cu.usmserver.core.model.enumerations.OperatingSystem;
import io._29cu.usmserver.core.model.enumerations.Platform;

public class InstallerResource extends EntityResourceBase<Installer> {

    private Long rid;
    private Platform platform;
    private OperatingSystem os;
    private String downloadUrl;
    private String expressInstallCommand;
    private String launchCommand;
	private String uninstallCommand;
   
    public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public OperatingSystem getOs() {
		return os;
	}

	public void setOs(OperatingSystem os) {
		this.os = os;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getExpressInstallCommand() {
		return expressInstallCommand;
	}

	public void setExpressInstallCommand(String expressInstallCommand) {
		this.expressInstallCommand = expressInstallCommand;
	}

	public String getLaunchCommand() {
		return launchCommand;
	}

	public void setLaunchCommand(String launchCommand) {
		this.launchCommand = launchCommand;
	}

	public String getUninstallCommand() {
		return uninstallCommand;
	}

	public void setUninstallCommand(String uninstallCommand) {
		this.uninstallCommand = uninstallCommand;
	}

	@Override
    public Installer toEntity() {
		Installer installer = new Installer();
		installer.setId(rid);
		installer.setPlatform(platform);
		installer.setOs(os);
		installer.setDownloadUrl(downloadUrl);
		installer.setExpressInstallCommand(expressInstallCommand);
		installer.setLaunchCommand(launchCommand);
		installer.setUninstallCommand(uninstallCommand);
        return installer;
    }
}
