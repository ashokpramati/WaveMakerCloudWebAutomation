package com.pramati.wavemaker.test.steps;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pramati.wavemaker.base.BaseTest;
import com.pramati.wavemaker.pages.Deployment;
import com.pramati.wavemaker.pages.ImportZipFile;
import com.pramati.wavemaker.pages.NewProjectDialog;
import com.pramati.wavemaker.pages.ProjectCreationPage;

/**
 * Test Case for deployement of Application in Cloud
 * 
 * @author krishnakumarnellore
 *
 */
public class Example extends BaseTest {
	Calendar calendar = new GregorianCalendar();
	
	String url = null;
	public static String projectName = null;
	
	NewProjectDialog newProjectDialog = null;
	ProjectCreationPage projectCreationPage = null;
	Deployment deployment = null;
	ImportZipFile importZipFile = null;
	

	
	

	@Test
	public void testNewProjectDeployment() throws InterruptedException {		
		projectCreationPage = new ProjectCreationPage();
		projectCreationPage.clickMenuBar("File","Import");
		
		importZipFile = new ImportZipFile();
		System.out.println(importZipFile.clickSelectZipFileAndUploadFile("D:\\WMDemoApps\\GridMultiSelect.zip"));
		System.out.println(importZipFile.clickSelectZipFileAndUploadFile("D:\\WMDemoApps\\HrApp.zip"));
		System.out.println(importZipFile.clickSelectZipFileAndUploadFile("D:\\WMDemoApps\\IssueCloud61.zip"));
		System.out.println(importZipFile.clickSelectZipFileAndUploadFile("D:\\WMDemoApps\\StrikeIron_LiteWebServices.zip"));
		System.out.println(importZipFile.clickSelectZipFileAndUploadFile("D:\\WMDemoApps\\WM6APPCRMSimple.zip"));
		System.out.println(importZipFile.clickSelectZipFileAndUploadFile("D:\\WMDemoApps\\WMBlog.zip"));
		
		
				
	}


}
