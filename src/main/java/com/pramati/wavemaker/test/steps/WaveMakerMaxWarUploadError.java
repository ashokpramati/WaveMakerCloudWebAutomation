package com.pramati.wavemaker.test.steps;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pramati.wavemaker.base.BaseTest;
import com.pramati.wavemaker.pages.Deployment;
import com.pramati.wavemaker.pages.NewProjectDialog;
import com.pramati.wavemaker.pages.ProjectCreationPage;
import com.pramati.wavemaker.util.ConfigProperties;

/**
 * Test Case to Check max file upload error.
 * 
 * @author krishnakumarnellore
 *
 */

public class WaveMakerMaxWarUploadError extends BaseTest {

	Calendar calendar = new GregorianCalendar();
	public static String projectName = null;

	NewProjectDialog newProjectDialog = null;
	ProjectCreationPage projectCreationPage = null;
	Deployment deployment = null;
	


	@Test
	public void testNewProjectDeployment() throws InterruptedException {
		homePage.clickNewProject();
		newProjectDialog = new NewProjectDialog();

		projectName = "Project"+"B"+calendar.get(Calendar.MINUTE)+calendar.get(Calendar.SECOND);

		newProjectDialog.setProjectName(projectName);
		newProjectDialog.setDeskTopTemplate("Menu");
		newProjectDialog.clickButton("ok");
		projectCreationPage = new ProjectCreationPage();
		projectCreationPage.clickMenuBar("File","Deploy Project");
		projectCreationPage.clickFileSubSubMenu("New Deployment");
		projectCreationPage.selectDeployment("cloudjee");
		projectCreationPage.clickDeploymentBtn("Ok");
		deployment = new Deployment();
		Assert.assertEquals("https://apps.wavemaker.com", deployment.getWaveMakerCloudAccount_CloudTargetTxt(),"Target url didn't match");
		deployment.setUserPassword(ConfigProperties.USERNAME, ConfigProperties.PASSWORD);		
		deployment.clickDeployNowBtn();		
		List<String> deployDialogTxt = deployment.clickCloudAccountOkBtn(ConfigProperties.USERNAME, ConfigProperties.PASSWORD);
		Assert.assertTrue(deployDialogTxt.contains("Deploying..."),"Deploying message is not displayed");
		Assert.assertTrue(deployDialogTxt.contains("Exporting "+projectName+".war, this may take a few minutes"),WaveMakerTestCase.projectName+".war message is not displayed");
		Assert.assertTrue(deployDialogTxt.contains("Deploying "+projectName+".war to WaveMaker Cloud, this may take a few minutes"),WaveMakerTestCase.projectName+".war message is not displayed");
		Assert.assertEquals("Error deploying: Maximun of 5 applications already deployed.",deployment.alertErrorDeploying(),"Deployment is happening for more than 5 app in cloud");	
		


	}
}
