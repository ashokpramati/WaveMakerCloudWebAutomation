package com.pramati.wavemaker.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pramati.wavemaker.page.BasePage;

public class ImportZipFile extends BasePage {

	private static final String STUDIOBAR = "div#studio_.wmcontainer.Studio-.studiodialog.wmdialog.nonmodaldialog.fadeInAnim";
	private static final String STUDIOTITLEBAR = "studio_titleBar";
	private static final String STUDIOTITLEBARLABEL = "studio_dialogTitleLabel";
	private static final String STUDIO_IMPORTFILE_LAYER = "studio__importFile_introLayer";
	
	private static final String STUDIO_FOOTER = "studio__importFile_footer1";
	private static final String CANCEL_BTN = "studio__importFile_cancelButton2";
	private static final String SELECT_ZIP_FILE = "input[id='studio__importFile_fileUploader_button']";
	
	private static final String DIALOG = "app_toastDialog_rightColumn";
	private static final String WMSIZENODE = "wmSizeNode";

	/**
	 * Get the webelement of Import Zip File Title bar
	 * 
	 * @return
	 */
	public WebElement importZipFile() {
		waitForElementLocatedByCSS(STUDIOBAR, getTimeOutInSeconds());
		return getElementByCSS(STUDIOBAR);
	}

	/**
	 * Get the Import Xip File Title text
	 * 
	 * @return
	 */
	public String getImportZipFileText() {
		return importZipFile().findElement(By.id(STUDIOTITLEBAR))
				.findElement(By.id(STUDIOTITLEBARLABEL)).getText();
	}

	/**
	 * Gets the Import File Parent Element.
	 * 
	 * To be used for getting below:
	 * 
	 * Use this dialog to import
	 * 
	 * Projects Project Templates Themes Custom Components
	 * 
	 * @return
	 */
	private WebElement getImportFileParentEle() {
		return getElementByID(STUDIO_IMPORTFILE_LAYER);
	}
	
	
	/**
	 * Get the footer parent element.
	 * 
	 * Usefull to get cancel and Select Zipfile button
	 * 
	 * @return
	 */
	private WebElement getFooterElement(){
		return getImportFileParentEle().findElement(By.id(STUDIO_FOOTER));
	}
	
	/**
	 * Click on Cancel button of Import Zip File 
	 * 
	 */
	public void clickFooterCancelBtn(){
		getFooterElement().findElement(By.id(CANCEL_BTN)).click();
	}
	
	/**
	 * Click and upload file in cloudjee
	 * 
	 * @param filePath
	 */
	public String clickSelectZipFileAndUploadFile(String filePath){
		getFooterElement().findElement(By.cssSelector(SELECT_ZIP_FILE)).sendKeys(filePath);
		return getDialogMsg();
	}
	
	/**
	 * Get's the alert message displayed if any while upload zip file.
	 * 
	 * Example : File failed to upload: String index out of range: -1
	 * 
	 * @return
	 */
	private String getDialogMsg(){
		waitForElementLocatedByID(DIALOG, getTimeOutInSeconds());
		return getElementByID(DIALOG).findElement(By.className(WMSIZENODE)).getText();
	}
	
	
}
