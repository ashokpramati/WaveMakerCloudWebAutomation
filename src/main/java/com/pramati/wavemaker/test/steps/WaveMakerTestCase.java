package com.pramati.wavemaker.test.steps;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pramati.wavemaker.base.BaseTest;
import com.pramati.wavemaker.page.BasePage;
import com.pramati.wavemaker.pages.CloudJeeApplication;
import com.pramati.wavemaker.pages.Deployment;
import com.pramati.wavemaker.pages.NewProjectDialog;
import com.pramati.wavemaker.pages.ProjectCreationPage;
import com.pramati.wavemaker.util.ConfigProperties;

/**
 * Test Case for deployement of Application in Cloud
 * 
 * @author krishnakumarnellore
 *
 */
public class WaveMakerTestCase extends BaseTest {
	Calendar calendar = new GregorianCalendar();
	
	String url = null;
	public static String projectName = null;
	
	NewProjectDialog newProjectDialog = null;
	ProjectCreationPage projectCreationPage = null;
	Deployment deployment = null;
	CloudJeeApplication cloudJeeApplication = null;
	
	private static final String UNDEPLOY = "UNDEPLOY";
	
	private static final String START = "START";
	private static final String STOP = "STOP";
	
	

	@Test
	public void testNewProjectDeployment() throws InterruptedException {
		homePage.clickNewProject();
		newProjectDialog = new NewProjectDialog();
		
		projectName = "Project"+"A"+calendar.get(Calendar.MINUTE)+calendar.get(Calendar.SECOND);
		
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
		Assert.assertTrue(deployDialogTxt.contains("Exporting "+WaveMakerTestCase.projectName+".war, this may take a few minutes"),WaveMakerTestCase.projectName+".war message is not displayed");
		Assert.assertTrue(deployDialogTxt.contains("Deploying "+WaveMakerTestCase.projectName+".war to WaveMaker Cloud, this may take a few minutes"),WaveMakerTestCase.projectName+".war message is not displayed");
		url = deployment.alertGetLinkTextOfDeployment();
		Assert.assertTrue(url.startsWith("http"),url+" doesnot has link to depployment url");
	}
	
	@Test(dependsOnMethods="testNewProjectDeployment", description="Verifying whether application is successfully deployed by opening url")
	public void testSuccesfullDeploymentAPP(){
		WebDriver driver = BasePage.getDriver();
		driver.get(url);
		Assert.assertEquals(projectName,driver.getTitle(),"Title of the Application didn't match");
		Assert.assertEquals("Logout",driver.findElement(By.id("main_logoutButton")).getText(),"Logout button text didn't match");
		driver.quit();
	}


	@Test(enabled=true, dependsOnMethods="testSuccesfullDeploymentAPP")	
	public void verifyAppUndeploy(){
		
		homePage.openExistingProject(projectName);
		projectCreationPage = new ProjectCreationPage();
		projectCreationPage.clickMenuBar("File","Deploy Project");
		projectCreationPage.clickFileSubSubMenu("Manage Deployment");
		deployment = new Deployment();
		deployment.setUserPassword(ConfigProperties.USERNAME, ConfigProperties.PASSWORD);
		deployment.clickManageWMCloudApBtn();
		cloudJeeApplication = new CloudJeeApplication();
		deployment.setUserPassword(ConfigProperties.USERNAME, ConfigProperties.PASSWORD);
		
		cloudJeeApplication.changeAPPStatus(projectName, STOP);
		Assert.assertEquals(cloudJeeApplication.getAPPStatus(projectName),"STOPPED","App is not started");
		cloudJeeApplication.changeAPPStatus(projectName, START);		
		Assert.assertEquals(cloudJeeApplication.getAPPStatus(projectName),"STARTED","App is not stopped");
				
		cloudJeeApplication.changeAPPStatus(projectName, UNDEPLOY);		
		Assert.assertNull(cloudJeeApplication.getAPPStatus(projectName),"App not successfully undeployed");		
		
	}


}
