////////////////////////////////////////////////////////////////////////
//
//     Copyright (c) 2009-2013 Denim Group, Ltd.
//
//     The contents of this file are subject to the Mozilla Public License
//     Version 2.0 (the "License"); you may not use this file except in
//     compliance with the License. You may obtain a copy of the License at
//     http://www.mozilla.org/MPL/
//
//     Software distributed under the License is distributed on an "AS IS"
//     basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
//     License for the specific language governing rights and limitations
//     under the License.
//
//     The Original Code is ThreadFix.
//
//     The Initial Developer of the Original Code is Denim Group, Ltd.
//     Portions created by Denim Group, Ltd. are Copyright (C)
//     Denim Group, Ltd. All Rights Reserved.
//
//     Contributor(s): Denim Group, Ltd.
//
////////////////////////////////////////////////////////////////////////
package com.denimgroup.threadfix.selenium.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.denimgroup.threadfix.selenium.pages.ApplicationDetailPage;
import com.denimgroup.threadfix.selenium.pages.LoginPage;
import com.denimgroup.threadfix.selenium.pages.TeamDetailPage;
import com.denimgroup.threadfix.selenium.pages.TeamIndexPage;
import com.denimgroup.threadfix.selenium.pages.UploadScanPage;

public class ScanTests extends BaseTest {
	
	private WebDriver driver;
	private static LoginPage loginPage;
	public ApplicationDetailPage applicationDetailPage;
	public UploadScanPage uploadScanPage;
	public TeamIndexPage teamIndexPage;
	public TeamDetailPage teamDetailPage;
	
	public String appWasAlreadyUploadedErrorText = "Scan file has already been uploaded.";
	
	public final static Map<String, String> SCAN_FILE_MAP = new HashMap<String, String>();
	static {
		SCAN_FILE_MAP.put("Microsoft CAT.NET", getScanFilePath("Static","CAT.NET","catnet_RiskE.xml") );
		SCAN_FILE_MAP.put("FindBugs", getScanFilePath("Static","FindBugs","findbugs-normal.xml") );
		SCAN_FILE_MAP.put("IBM Rational AppScan", getScanFilePath("Dynamic","AppScan","appscan-php-demo.xml") );
		SCAN_FILE_MAP.put("Mavituna Security Netsparker", getScanFilePath("Dynamic","NetSparker","netsparker-demo-site.xml") );
		SCAN_FILE_MAP.put("Skipfish", getScanFilePath("Dynamic","Skipfish","skipfish-demo-site.zip") );
		SCAN_FILE_MAP.put("w3af", getScanFilePath("Dynamic","w3af","w3af-demo-site.xml") );
		SCAN_FILE_MAP.put("OWASP Zed Attack Proxy", getScanFilePath("Dynamic","ZAP","zaproxy-normal.xml") );
		SCAN_FILE_MAP.put("Nessus", getScanFilePath("Dynamic","Nessus","nessus_report_TFTarget.xml") );
		SCAN_FILE_MAP.put("Arachni", getScanFilePath("Dynamic","Arachni","php-demo.xml") );
		SCAN_FILE_MAP.put("WebInspect",getScanFilePath("Dynamic","WebInspect","webinspect-demo-site.xml"));
		SCAN_FILE_MAP.put("NTO Spider",getScanFilePath("Dynamic","NTOSpider","VulnerabilitiesSummary.xml"));
		SCAN_FILE_MAP.put("Brakeman", getScanFilePath("Static","Brakeman","brakeman.json")); 
		SCAN_FILE_MAP.put("Fortify 360", getScanFilePath("Static","Fortify","ZigguratUtility.fpr"));
		SCAN_FILE_MAP.put("Acunetix WVS", getScanFilePath("Dynamic","Acunetix","testaspnet.xml"));
		SCAN_FILE_MAP.put("Burp Suite", getScanFilePath("Dynamic","Burp","burp-demo-site.xml") );
		SCAN_FILE_MAP.put("IBM Rational AppScan Source Edition", null);
	}
	
	public final static String[][] catnetResults = {
			{ XSS, "Critical", "/ZigguratUtilityWeb/ContactUs.aspx", "email"},
			{ XSS, "Critical", "/ZigguratUtilityWeb/ContactUs.aspx", "txtMessage"},
			{ XSS, "Critical", "/ZigguratUtilityWeb/ContactUs.aspx", "txtSubject"},
			{ XSS, "Critical", "/ZigguratUtilityWeb/MakePayment.aspx", "txtAmount"},
			{ XSS, "Critical", "/ZigguratUtilityWeb/MakePayment.aspx", "txtAmount"},
			{ XSS, "Critical", "/ZigguratUtilityWeb/MakePayment.aspx", "txtCardNumber"},
			{ XSS, "Critical", "/ZigguratUtilityWeb/Message.aspx", "Msg"},
			{ SQLI, "Critical", "/ZigguratUtilityWeb/LoginPage.aspx", "txtPassword"},
			{ SQLI, "Critical", "/ZigguratUtilityWeb/LoginPage.aspx", "txtUsername"},
			{ SQLI, "Critical", "/ZigguratUtilityWeb/MakePayment.aspx", "txtAmount"},
			{ SQLI, "Critical", "/ZigguratUtilityWeb/ViewStatement.aspx", "StatementID"},
		};
	
	public final static String[][] findBugsResults = new String[][] {
			{ XSS, "Critical", "securibench/micro/aliasing/Aliasing1.java", "name"},
			{ XSS, "Critical", "securibench/micro/aliasing/Aliasing4.java", "name"},
			{ XSS, "Critical", "securibench/micro/basic/Basic1.java", "str"},
			{ XSS, "Critical", "securibench/micro/basic/Basic18.java", "s"},
			{ XSS, "Critical", "securibench/micro/basic/Basic2.java", "str"},
			{ XSS, "Critical", "securibench/micro/basic/Basic28.java", "name"},
			{ XSS, "Critical", "securibench/micro/basic/Basic4.java", "str"},
			{ XSS, "Critical", "securibench/micro/basic/Basic8.java", "str"},
			{ XSS, "Critical", "securibench/micro/basic/Basic9.java", "s1"},
			{ XSS, "Critical", "securibench/micro/pred/Pred4.java", "name"},
			{ XSS, "Critical", "securibench/micro/pred/Pred5.java", "name"},
			{ XSS, "Critical", "securibench/micro/pred/Pred6.java", "name"},
			{ XSS, "Critical", "securibench/micro/pred/Pred7.java", "name"},
			{ XSS, "Critical", "securibench/micro/pred/Pred8.java", "name"},
			{ XSS, "Critical", "securibench/micro/pred/Pred9.java", "name"},
			{ XSS, "Critical", "securibench/micro/session/Session1.java", "name"},
			{ XSS, "Critical", "securibench/micro/session/Session2.java", "name"},
			{ XSS, "High", "securibench/micro/basic/Basic10.java", "s5"},
			{ XSS, "High", "securibench/micro/basic/Basic27.java", ""},
			{ XSS, "High", "securibench/micro/basic/Basic29.java", ""},
			{ XSS, "High", "securibench/micro/basic/Basic30.java", ""},
			{ XSS, "High", "securibench/micro/basic/Basic32.java", "header"},
			{ XSS, "High", "securibench/micro/basic/Basic34.java", "headerValue"},
			{ XSS, "High", "securibench/micro/basic/Basic35.java", ""},
			{ XSS, "High", "securibench/micro/pred/Pred2.java", "name"},
			{ XSS, "High", "securibench/micro/pred/Pred3.java", "name"},
			{ XSS, "High", "securibench/micro/strong_updates/StrongUpdates3.java", ""},
			{ XSS, "High", "securibench/micro/strong_updates/StrongUpdates4.java", ""},
			{ XSS, "High", "securibench/micro/strong_updates/StrongUpdates5.java", ""},
			{ SQLI, "High", "securibench/micro/basic/Basic19.java", ""},
			{ SQLI, "High", "securibench/micro/basic/Basic20.java", ""},
			{ SQLI, "High", "securibench/micro/basic/Basic21.java", ""},
		};
	
	public final static String[][] ibmAppScanResults = new String[][] {
			{ PATH_TRAVERSAL, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
			{ XSS, "Critical", "/demo/EvalInjection2.php", "command"},
			{ XSS, "Critical", "/demo/XPathInjection2.php", ""},
			{ XSS, "Critical", "/demo/XPathInjection2.php", "password"},
			{ XSS, "Critical", "/demo/XPathInjection2.php", "username"},
			{ XSS, "Critical", "/demo/XSS-reflected2.php", "username"},
			{ COMMAND_INJECTION, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
			{ SQLI, "Critical", "/demo/XPathInjection2.php", "password"},
			{ SQLI, "Critical", "/demo/XPathInjection2.php", "username"},
			{ INFO_EXPOSURE_ERROR_MESSAGE, "Critical", "/demo/SQLI2.php", "username"},
			{ GENERIC_INJECTION, "Medium", "/demo/XPathInjection2.php", "password"},
			{ GENERIC_INJECTION, "Medium", "/demo/XPathInjection2.php", "username"},
			{ GENERIC_INJECTION, "Medium", "/demo/XSS-reflected2.php", "username"},
			{ DIRECTORY_LISTING, "Medium", "/demo/DIRECT~1/", ""},
			{ DIRECTORY_LISTING, "Medium", "/demo/DirectoryIndexing/", ""},
			{ REFLECTION_ATTACK, "Medium", "/demo/XPathInjection2.php", "password"},
			{ REFLECTION_ATTACK, "Medium", "/demo/XPathInjection2.php", "username"},
			{ REFLECTION_ATTACK, "Medium", "/demo/XSS-reflected2.php", "username"},
			{ FORCED_BROWSING, "Low", "/demo/DIRECT~1/", ""},
			{ FORCED_BROWSING, "Low", "/demo/DirectoryIndexing/", ""},
			{ IMPROPER_INPUT_VALIDATION, "Low", "/aux/", ""},
			{ IMPROPER_INPUT_VALIDATION, "Low", "/cgi-bin/", ""},
			{ IMPROPER_INPUT_VALIDATION, "Low", "/com1/", ""},
			{ IMPROPER_INPUT_VALIDATION, "Low", "/com2/", ""},
			{ IMPROPER_INPUT_VALIDATION, "Low", "/com3/", ""},
			{ IMPROPER_INPUT_VALIDATION, "Low", "/demo/", ""},
			{ IMPROPER_INPUT_VALIDATION, "Low", "/demo/aux/", ""},
			{ IMPROPER_INPUT_VALIDATION, "Low", "/demo/com1/", ""},
			{ IMPROPER_INPUT_VALIDATION, "Low", "/demo/com2/", ""},
			{ IMPROPER_INPUT_VALIDATION, "Low", "/demo/com3/", ""},
			{ INFORMATION_EXPOSURE, "Low", "/demo/PathTraversal.php", ""},
			{ INFORMATION_EXPOSURE, "Low", "/demo/PredictableResource.php", ""},
			{ INFORMATION_EXPOSURE, "Low", "/demo/XSS-cookie.php", ""},
			{ INFO_LEAK_COMMENTS, "Low", "/demo/", ""},
			{ INFO_LEAK_COMMENTS, "Low", "/demo/SQLI.php", ""},
			{ INFO_LEAK_COMMENTS, "Low", "/demo/XSS-reflected.php", ""},
			{ INFO_LEAK_COMMENTS, "Low", "/demo/XSS-reflected2.php", ""},
			{ INFO_LEAK_TEST_CODE, "Low", "/", ""},
			{ INFO_LEAK_TEST_CODE, "Low", "/demo/PredictableResource.php", ""},
			{ INFO_LEAK_SERVER_ERROR, "Info", "/demo/EvalInjection2.php", "command"},
			{ INFO_LEAK_SERVER_ERROR, "Info", "/demo/LDAPInjection2.php", "username"},
			{ INFO_LEAK_SERVER_ERROR, "Info", "/demo/SQLI2.php", "username"},
			{ INFO_LEAK_SERVER_ERROR, "Info", "/demo/XPathInjection2.php", "password"},
			{ INFO_LEAK_SERVER_ERROR, "Info", "/demo/XPathInjection2.php", "username"},
	};
	
	public final static String[][] netsparkerResults = new String[] [] {
			{CODE_INJECTION, "Critical", "/demo/EvalInjection2.php", "command"},
			{OS_INJECTION, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
			{RESOURCE_INJECTION, "High", "/demo/OSCommandInjection2.php", "fileName"},
			{XSS, "High", "/demo/EvalInjection2.php", "command"},
			{XSS, "High", "/demo/SQLI2.php", "username"},
			{XSS, "High", "/demo/XPathInjection2.php", "password"},
			{XSS, "High", "/demo/XPathInjection2.php", "username"},
			{XSS, "High", "/demo/XSS-reflected2.php", "username"},
			{SOURCE_CODE_INCLUDE, "Medium", "/demo/OSCommandInjection2.php", "fileName"},
			{CONFIGURATION, "Low", "/demo/", ""},
			{FORCED_BROWSING, "Low", "/demo/LDAPInjection.php", ""},
			{FORCED_BROWSING, "Low", "/demo/PredictableResource.php.bak", ""},
			{INFORMATION_EXPOSURE, "Low", "/demo/", ""},
			{INFORMATION_EXPOSURE, "Low", "/demo/PredictableResource.php", ""},
			{INFO_EXPOSURE_ERROR_MESSAGE, "Low", "/demo/SQLI2.php", "username"},
			{INFORMATION_EXPOSURE, "Info", "/demo/EvalInjection2.php", ""},
			{INFORMATION_EXPOSURE, "Info", "/demo/FormatString2.php", ""},
			{INFORMATION_EXPOSURE, "Info", "/demo/LDAPInjection2.php", ""},
			{INFORMATION_EXPOSURE, "Info", "/demo/OSCommandInjection2.php", ""},
			{INFORMATION_EXPOSURE, "Info", "/demo/PathTraversal.php", ""},
			{INFORMATION_EXPOSURE, "Info", "/demo/SQLI2.php", ""},
			{INFORMATION_EXPOSURE, "Info", "/demo/XPathInjection2.php", ""},
			{INFORMATION_EXPOSURE, "Info", "/demo/XSS-cookie.php", ""},
			{INFORMATION_EXPOSURE, "Info", "/demo/XSS-reflected2.php", ""},
			{"Information Exposure Through Directory Listing", "Info", "/demo/DirectoryIndexing/", ""},
	};
	
	public final static String[][] skipfishResults = new String [][] {
			{SQLI, "Critical", "/demo/EvalInjection2.php", "command"},
			{SQLI, "Critical", "/demo/LDAPInjection2.php", "username"},
			{SQLI, "Critical", "/demo/SQLI2.php", "username"},
			{IMPROPER_HANDLING_OF_MISSING_VALUES, "High", "/demo/EvalInjection2.php","command"},
			{IMPROPER_HANDLING_OF_MISSING_VALUES, "High", "/demo/FormatString2.php","name"},
			{IMPROPER_HANDLING_OF_MISSING_VALUES, "High", "/demo/PathTraversal.php","action"},
			{IMPROPER_HANDLING_OF_MISSING_VALUES, "High", "/demo/XSS-cookie.php","cookie"},
			{IMPROPER_HANDLING_OF_MISSING_VALUES, "High", "/demo/XSS-reflected2.php","username"},
			{PATH_TRAVERSAL, "High", "/demo/PathTraversal.php","action"},
			{XSS, "High", "/demo/XSS-cookie.php","cookie"},
			{XSS, "High", "/demo/XSS-reflected2.php","username"},
			{DIRECTORY_LISTING, "High", "/demo/DirectoryIndexing/",""},
			{INFO_LEAK_SERVER_ERROR, "High", "/demo/SQLI2.php","username"},
			{CSRF, "Medium", "/demo/EvalInjection2.php",""},
			{CSRF, "Medium", "/demo/FormatString2.php",""},
			{CSRF, "Medium", "/demo/LDAPInjection2.php",""},
			{CSRF, "Medium", "/demo/OSCommandInjection2.php",""},
			{CSRF, "Medium", "/demo/SQLI2.php",""},	
			{CSRF, "Medium", "/demo/XSS-cookie.php",""},
			{CSRF, "Medium", "/demo/XSS-reflected2.php",""},
		
	};
	
	public final static String[][] ntospiderResults = new String [][] {
			{"Improper Authentication", "Critical", "/bank/login.aspx", ""},
			{"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')", "Critical", "/bank/login.aspx", "passw"},
			{"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')", "Critical", "/bank/login.aspx", "uid"},
			{"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')", "Critical", "/subscribe.aspx", "txtEmail"},
			{"Improper Neutralization of Script-Related HTML Tags in a Web Page (Basic XSS)", "High", "/bank/login.aspx", "uid"},
			{"Improper Neutralization of Script-Related HTML Tags in a Web Page (Basic XSS)", "High", "/comment.aspx", "name"},
			{"Improper Neutralization of Script-Related HTML Tags in a Web Page (Basic XSS)", "High", "/notfound.aspx", "aspxerrorpath"},
			{"Improper Neutralization of Script-Related HTML Tags in a Web Page (Basic XSS)", "High", "/search.aspx", "txtSearch"},
			{"Information Exposure Through Directory Listing", "Medium", "/bank/", ""},
			{"Privacy Violation", "Medium", "/", ""},
			{"Privacy Violation", "Medium", "/bank/login.aspx", ""},
			{"Privacy Violation", "Medium", "/comment.aspx", ""},
			{"Privacy Violation", "Medium", "/default.aspx", ""},
			{"Privacy Violation", "Medium", "/disclaimer.htm", ""},
			{"Privacy Violation", "Medium", "/feedback.aspx", ""},
			{"Privacy Violation", "Medium", "/notfound.aspx", ""},
			{"Privacy Violation", "Medium", "/search.aspx", ""},
			{"Privacy Violation", "Medium", "/subscribe.aspx", ""},
			{"Privacy Violation", "Medium", "/survey_questions.aspx", ""},
			{"Information Exposure Through Environmental Variables", "Low", "/aaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbthbbbbbbbbbbbbb.bbbbbbb", ""},
		};
	
	public final static String[][] w3afResults = new String[] [] { 
		{EVAL_INJECTION,"High", "/demo/EvalInjection2.php","command"},
		{XSS, "High", "/demo/XSS-cookie.php", "cookie"},
		{LDAP_INJECTION,"High", "/demo/LDAPInjection2.php","username"},
		{OS_INJECTION, "High", "/demo/OSCommandInjection2.php", "fileName"},
		{SQLI,"High", "/demo/SQLI2.php","username"},
		{XPATH_INJECTION,"Medium", "/demo/XPathInjection2.php","password"},
		{XPATH_INJECTION,"Medium", "/demo/XPathInjection2.php","username"},
		{XSS,"Medium", "/demo/EvalInjection2.php","command"},
		{XSS,"Medium", "/demo/XSS-reflected2.php","username"},
		{FORMAT_STRING_INJECTION,"Medium", "/demo/FormatString2.php","name"},
		{FORCED_BROWSING,"Info", "/demo.zip",""},
		{FORCED_BROWSING,"Info", "/demo/PredictableResource.php.bak",""},
		
	};
	
	public final static String[][] zapProxyResults = new String [][] {
			{DIRECTORY_LISTING, "High", "/demo/DirectoryIndexing/", ""},
			{XSS, "Medium", "/demo/EvalInjection2.php", "command"},
			{XSS, "Medium", "/demo/XPathInjection2.php", "password"},
			{XSS, "Medium", "/demo/XPathInjection2.php", "username"},
			{XSS, "Medium", "/demo/XSS-reflected2.php", "username"},
			{SQLI, "Medium", "/demo/SQLI2.php", "username"},
		};
	
	public final static String[][] nessusResults = new String [][] {
			{OS_INJECTION, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
			{SQLI, "Critical", "/demo/SQLI2.php", "username"},
			{FORCED_BROWSING, "Medium", "/demo/PredictableResource.php.bak", ""},
			{EXTERNAL_FILEPATH_CONTROL, "Medium", "/demo/OSCommandInjection2.php", "fileName"},
			{XSS, "Medium", "/demo/EvalInjection2.php", "command"},
			{XSS, "Medium", "/demo/XPathInjection2.php", "password"},
			{XSS, "Medium", "/demo/XSS-cookie.php", "cookie"},
			{XSS, "Medium", "/demo/XSS-reflected2.php", "username"},
			{SESSION_FIXATION, "Medium", "/demo/XSS-reflected2.php", "username"},
			{DIRECTORY_LISTING, "Low", "/demo/DirectoryIndexing/", ""},
		};
	
	public final static String[][] arachniResults = new String [][] {
			{XSS, "Critical", "/demo/EvalInjection2.php", "command"},
			{XSS, "Critical", "/demo/XPathInjection2.php", "password"},
			{XSS, "Critical", "/demo/XPathInjection2.php", "username"},
			{XSS, "Critical", "/demo/XSS-reflected2.php", "username"},
			{LDAP_INJECTION, "Critical", "/demo/LDAPInjection2.php", "username"},
			{OS_INJECTION, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
			{SQLI, "Critical", "/demo/SQLI2.php", "username"},
			{XML_INJECTION, "Critical", "/demo/XPathInjection2.php", "password"},
			{XML_INJECTION, "Critical", "/demo/XPathInjection2.php", "username"},
			{INFO_LEAK_DIRECTORIES, "High", "/demo/", ""},
		};
	
	public final static String[][] webInspectResults = new String [][] {
			{XSS, "Critical", "/demo/EvalInjection2.php", "command"},
			{XSS, "Critical", "/demo/XSS-cookie.php", "cookie"},
			{XSS, "Critical", "/demo/XSS-reflected2.php", "username"},
			{OS_INJECTION, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
			{INFORMATION_EXPOSURE, "Critical", "/demo/SQLI2.php", "username"},
			{INFORMATION_EXPOSURE, "Critical", "/demo/password.txt", ""},
			{INFORMATION_EXPOSURE, "High", "/demo/OSCommandInjection2.php", "fileName"},
			{INFORMATION_EXPOSURE, "High", "/demo/PredictableResource.php.BAK", ""},
			{INFORMATION_EXPOSURE, "High", "/demo/PredictableResource.php.bak", ""},
			{FORCED_BROWSING, "Medium", "/test.php", ""},
			{ACCESS_CONTROL, "Medium", "/demo/XPathInjection2.php", ""},
			{LDAP_INJECTION, "Medium", "/demo/LDAPInjection2.php", ""},
			{INFORMATION_EXPOSURE, "Medium", "/demo/LDAPInjection2.php", ""},
			{INFORMATION_EXPOSURE, "Low", "/cgi-bin/test.php", ""},
			{INFORMATION_EXPOSURE, "Low", "/demo/EvalInjection2.php", ""},
			{INFORMATION_EXPOSURE, "Low", "/demo/FormatString2.php", ""},
			{INFORMATION_EXPOSURE, "Low", "/demo/OSCommandInjection2.php", ""},
			{INFORMATION_EXPOSURE, "Low", "/demo/PathTraversal.php", ""},
			{INFORMATION_EXPOSURE, "Low", "/demo/PathTraversal.php", "action"},
			{INFORMATION_EXPOSURE, "Low", "/demo/SQLI2.php", ""},
			{INFORMATION_EXPOSURE, "Low", "/demo/XPathInjection2.php", ""},
			{INFORMATION_EXPOSURE, "Low", "/demo/XSS-cookie.php", "cookie"},
			{INFORMATION_EXPOSURE, "Low", "/demo/XSS-reflected2.php", ""},
			{INFORMATION_EXPOSURE, "Low", "/test.php", ""},
			{DIRECTORY_LISTING, "Low", "/cgi-bin/", ""},
			{DIRECTORY_LISTING, "Low", "/demo/", ""},
			{INFORMATION_EXPOSURE, "Info", "/", ""},
	};
	
	public final static String[][] brakemanResults = new String [][] {
			{XSS, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/views/users/index.html", "User.new"},
			{XSS, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/views/users/results.html", "null"},
			{OS_INJECTION, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:user][:password]"},
			{OS_INJECTION, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:user][:password]"},
			{OS_INJECTION, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:user][:password]"},
			
			
			{SQLI, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:query]"},
			{OPEN_REDIRECT, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params"},
			{CSRF, "High", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/application_controller.rb", "null"},
			
			
			{EXTERNAL_CONTROL_OF_PARAM, "High", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/posts_controller.rb", "params[:post]"},
			{EXTERNAL_CONTROL_OF_PARAM, "High", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/posts_controller.rb", "params[:post]"},
			{EXTERNAL_CONTROL_OF_PARAM, "High", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:user]"},
			
			{EXTERNAL_CONTROL_OF_PARAM, "High", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:user]"},
			
			
			{ARGUMENT_INJECTION, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/models/user.rb", "null"},
			
			{ARGUMENT_INJECTION, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/models/user.rb", "null"},
			
			{FORCED_BROWSING, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/config/routes.rb", "null"},
			{EXTERNAL_CONTROL_OF_PARAM, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/post, user.rb", "null"},
			
			{OPEN_REDIRECT, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/posts_controller.rb", "Post.find(params[:id])"},
			{OPEN_REDIRECT, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "User.find(params[:id])"},
		};
	
	public final static String[][] fortify360Results = new String [][] {
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "Address"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "BillingDate"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "BillingDate"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CcfUsed"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CityServices"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CurrentElectricity"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CurrentNaturalGas"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CustomerNumber"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CustomerNumber"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "KiloWattHourUsed"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "Name"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "Payments"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "PreviousBill"},
			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "StateLocalTaxes"},
			{XSS, "High", "/ZigguratUtilityWeb/ContactUs.aspx", "email"},
			{XSS, "High", "/ZigguratUtilityWeb/ContactUs.aspx", "txtSubject"},
			{XSS, "High", "/ZigguratUtilityWeb/MakePayment.aspx", "txtCardNumber"},
			{XSS, "High", "/zigguratutilityweb/message.aspx", "Msg"},
			{SQLI, "High", "/ZigguratUtilityWeb/LoginPage.aspx", "txtUsername"},
			{SQLI, "High", "/ZigguratUtilityWeb/ViewStatement.aspx", "StatementID"},
			{ASP_NET_DEBUG, "Medium", "/ZigguratUtilityWeb/web.config", ""},
			{ASP_NET_CUSTOM_ERROR, "Medium", "/ZigguratUtilityWeb/web.config", ""},
			{ASP_NET_VALIDATION_MISSING, "Medium", "/zigguratutilityweb/message.aspx", ""},
			{IMPROPER_RESOURCE_SHUTDOWN, "Medium", "/ZigguratUtilityWeb/Home.aspx", ""},
			{IMPROPER_RESOURCE_SHUTDOWN, "Medium", "/ZigguratUtilityWeb/Home.aspx", ""},
			{IMPROPER_RESOURCE_SHUTDOWN, "Medium", "/ZigguratUtilityWeb/LoginPage.aspx", ""},
			{IMPROPER_RESOURCE_SHUTDOWN, "Medium", "/ZigguratUtilityWeb/ViewStatement.aspx", ""},
			{IMPROPER_RESOURCE_SHUTDOWN, "Medium", "/ZigguratUtilityWeb/ViewStatement.aspx", ""},
			{NON_SERIALIZABLE_OBJECT, "Medium", "/ZigguratUtilityWeb/LoginPage.aspx", ""},
			{TRUST_BOUNDARY_VIOLATION, "Medium", "/ZigguratUtilityWeb/LoginPage.aspx", ""},
			{NULL_POINTER, "Medium", "/ZigguratUtilityWeb/Home.aspx", ""},
			{NULL_POINTER, "Medium", "/ZigguratUtilityWeb/MakePayment.aspx", ""},
			{NULL_POINTER, "Medium", "/ZigguratUtilityWeb/MakePayment.aspx", ""},
			{SQLI, "Info", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", ""},
			{UNCHECKED_ERROR, "Info", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", ""}
		};
	
	public final static String[][] acunetixResults = new String [][] {
			{XSS, "Critical", "/comments.aspx", "tbComment"},
			{XSS, "Critical", "/readnews.aspx", "NewsAd"},
			{SQLI, "Critical", "/comments.aspx", "id"},
			{SQLI, "Critical", "/comments.aspx", "tbComment"},
			{SQLI, "Critical", "/login.aspx", "tbUsername"},
			{SQLI, "Critical", "/readnews.aspx", "id"},
			{CLEARTEXT_SENSITIVE_INFO, "Medium", "/login.aspx", ""},
			{CLEARTEXT_SENSITIVE_INFO, "Medium", "/signup.aspx", ""},
			{INFO_EXPOSURE_ERROR_MESSAGE, "Medium", "/default.aspx", "delete"},
			{INFO_EXPOSURE_ERROR_MESSAGE, "Medium", "/readnews.aspx", "id"},
			{INFO_EXPOSURE_ERROR_MESSAGE, "Medium", "/readnews.aspx", "NewsAd"},
			{INFO_EXPOSURE_ERROR_MESSAGE, "Medium", "Web Server", ""},
			{IMPROPER_RESTRICTION_AUTH, "Low", "/login.aspx", ""},
			{IMPROPER_RESTRICTION_AUTH, "Low", "/signup.aspx", ""},
			{INFORMATION_EXPOSURE, "Low", "Web Server", ""},
			{NON_SECURE_COOKIE, "Low", "/", ""},
			{FILES_ACCESSIBLE, "Info", "/_vti_cnf", ""},
			{FILES_ACCESSIBLE, "Info", "/_vti_cnf/acublog.csproj", ""},
			{FILES_ACCESSIBLE, "Info", "/_vti_cnf/acublog.csproj.webinfo", ""},
			{FILES_ACCESSIBLE, "Info", "/login.aspx", ""},
			{FILES_ACCESSIBLE, "Info", "/login.aspx.cs", ""},
			{FILES_ACCESSIBLE, "Info", "/login.aspx.resx", ""},
			{FILES_ACCESSIBLE, "Info", "/web.config", ""},
			{INFO_LEAK_BROWSER_CACHE, "Info", "/login.aspx", ""},
			{INFO_LEAK_BROWSER_CACHE, "Info", "/signup.aspx", ""},
		};
	
	public final static String[][] burpResults = new String [][] {
			{XSS, "High", "/demo/EvalInjection2.php", "command"},
			{XSS, "High", "/demo/XSS-reflected2.php", "username"},
			{OS_INJECTION, "High", "/demo/OSCommandInjection2.php", "fileName"},
			{SQLI, "High", "/demo/SQLI2.php", "username"},
			{IMPROPER_CROSS_BOUNDARY_REMOVAL_OF_DATA, "Info", "/demo/PredictableResource.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/DirectoryIndexing/admin.txt", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/EvalInjection.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/EvalInjection2.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/FormatString.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/FormatString2.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/LDAPInjection.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/LDAPInjection2.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/OSCommandInjection.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/OSCommandInjection2.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/PathTraversal.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/PredictableResource.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/SQLI.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/SQLI2.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XPathInjection.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XPathInjection2.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XSS-cookie.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XSS-reflected.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XSS-reflected2.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XSS-stored.php", ""},
			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XSS.php", ""},
			{INFORMATION_EXPOSURE, "Info", "/",""},
			{DIRECTORY_LISTING,"Info","/demo/DirectoryIndexing/",""},
		};
	
	@Before
	public void init() {
		super.init();
		driver = super.getDriver();
		loginPage = LoginPage.open(driver);
	}
	
	public static String getScanFilePath(String category, String scannerName, String fileName) {
		String string = "SupportingFiles/" + category  + "/" + scannerName + "/" + fileName;
		
		String urlFromCommandLine = System.getProperty("scanFileBaseLocation");
		if (urlFromCommandLine != null) {
			return urlFromCommandLine + string;
		}
		
		return ScanTests.class.getClassLoader().getResource(string).toString();
	}
	
//	// Mostly smoke test
	@Test
	public void testUploadScans() throws MalformedURLException {
		String teamName = "uploadScan" + getRandomString(5);
		String appName = "uploadScanApp" + getRandomString(5);
		int scanCnt  = 0;
		
		// log in
		teamIndexPage = loginPage.login("user", "password")
								.clickOrganizationHeaderLink()
								.clickOrganizationHeaderLink()
				 				.clickAddTeamButton()
								.setTeamName(teamName)
								.addNewTeam()
								.expandTeamRowByName(teamName)
								.addNewApplication(teamName, appName, "http://" + appName, "Low")
								.saveApplication(teamName);
		
			teamIndexPage.populateAppList(teamName);
			
			applicationDetailPage = teamIndexPage.clickViewAppLink(appName, teamName);
		
		// create an org and an app and upload the scan, then delete everything
		for (Entry<String, String> mapEntry : SCAN_FILE_MAP.entrySet()) {
			if (mapEntry.getValue() != null){
				File appScanFile = null;
				
				if (System.getProperty("scanFileBaseLocation") == null) {
					appScanFile = new File(new URL(mapEntry.getValue()).getFile());
				} else {
					appScanFile = new File(mapEntry.getValue());
				}
				assertTrue("The test file did not exist.", appScanFile.exists());
			} else {
				continue;
			}
			applicationDetailPage = applicationDetailPage.clickUploadScanLink()
														.setFileInput(mapEntry.getValue())
														.submitScan();
			scanCnt++;
			assertTrue("Scan count is incorrect after uploading "+mapEntry.getKey(), scanCnt == applicationDetailPage.scanCount());
		}
		
		assertTrue("Scan count is incorrect", scanCnt == applicationDetailPage.scanCount());
		
		applicationDetailPage.clickOrganizationHeaderLink()
							.clickViewTeamLink(teamName)
							.clickDeleteButton()
							.logout();
	}

	@Test
	public void microsoftCatNetScan() {
		String key = "Microsoft CAT.NET";
		String[][] expectedResults = catnetResults;
		
//		String[][] expectedResults = {
//				{ XSS, "Critical", "/ZigguratUtilityWeb/ContactUs.aspx", "email"},
//				{ XSS, "Critical", "/ZigguratUtilityWeb/ContactUs.aspx", "txtMessage"},
//				{ XSS, "Critical", "/ZigguratUtilityWeb/ContactUs.aspx", "txtSubject"},
//				{ XSS, "Critical", "/ZigguratUtilityWeb/MakePayment.aspx", "txtAmount"},
//				{ XSS, "Critical", "/ZigguratUtilityWeb/MakePayment.aspx", "txtAmount"},
//				{ XSS, "Critical", "/ZigguratUtilityWeb/MakePayment.aspx", "txtCardNumber"},
//				{ XSS, "Critical", "/ZigguratUtilityWeb/Message.aspx", "Msg"},
//				{ SQLI, "Critical", "/ZigguratUtilityWeb/LoginPage.aspx", "txtPassword"},
//				{ SQLI, "Critical", "/ZigguratUtilityWeb/LoginPage.aspx", "txtUsername"},
//				{ SQLI, "Critical", "/ZigguratUtilityWeb/MakePayment.aspx", "txtAmount"},
//				{ SQLI, "Critical", "/ZigguratUtilityWeb/ViewStatement.aspx", "StatementID"},
//			};
		
		runScanTest(key, expectedResults);
	}
	
	@Test
	public void findBugsScan() {
		
		String key = "FindBugs";
		String[][] expectedResults = findBugsResults;
//		String[][] expectedResults = new String[][] {
//			{ XSS, "Critical", "securibench/micro/aliasing/Aliasing1.java", "name"},
//			{ XSS, "Critical", "securibench/micro/aliasing/Aliasing4.java", "name"},
//			{ XSS, "Critical", "securibench/micro/basic/Basic1.java", "str"},
//			{ XSS, "Critical", "securibench/micro/basic/Basic18.java", "s"},
//			{ XSS, "Critical", "securibench/micro/basic/Basic2.java", "str"},
//			{ XSS, "Critical", "securibench/micro/basic/Basic28.java", "name"},
//			{ XSS, "Critical", "securibench/micro/basic/Basic4.java", "str"},
//			{ XSS, "Critical", "securibench/micro/basic/Basic8.java", "str"},
//			{ XSS, "Critical", "securibench/micro/basic/Basic9.java", "s1"},
//			{ XSS, "Critical", "securibench/micro/pred/Pred4.java", "name"},
//			{ XSS, "Critical", "securibench/micro/pred/Pred5.java", "name"},
//			{ XSS, "Critical", "securibench/micro/pred/Pred6.java", "name"},
//			{ XSS, "Critical", "securibench/micro/pred/Pred7.java", "name"},
//			{ XSS, "Critical", "securibench/micro/pred/Pred8.java", "name"},
//			{ XSS, "Critical", "securibench/micro/pred/Pred9.java", "name"},
//			{ XSS, "Critical", "securibench/micro/session/Session1.java", "name"},
//			{ XSS, "Critical", "securibench/micro/session/Session2.java", "name"},
//			{ XSS, "High", "securibench/micro/basic/Basic10.java", "s5"},
//			{ XSS, "High", "securibench/micro/basic/Basic27.java", ""},
//			{ XSS, "High", "securibench/micro/basic/Basic29.java", ""},
//			{ XSS, "High", "securibench/micro/basic/Basic30.java", ""},
//			{ XSS, "High", "securibench/micro/basic/Basic32.java", "header"},
//			{ XSS, "High", "securibench/micro/basic/Basic34.java", "headerValue"},
//			{ XSS, "High", "securibench/micro/basic/Basic35.java", ""},
//			{ XSS, "High", "securibench/micro/pred/Pred2.java", "name"},
//			{ XSS, "High", "securibench/micro/pred/Pred3.java", "name"},
//			{ XSS, "High", "securibench/micro/strong_updates/StrongUpdates3.java", ""},
//			{ XSS, "High", "securibench/micro/strong_updates/StrongUpdates4.java", ""},
//			{ XSS, "High", "securibench/micro/strong_updates/StrongUpdates5.java", ""},
//			{ SQLI, "High", "securibench/micro/basic/Basic19.java", ""},
//			{ SQLI, "High", "securibench/micro/basic/Basic20.java", ""},
//			{ SQLI, "High", "securibench/micro/basic/Basic21.java", ""},
//		};
		
		runScanTest(key, expectedResults);
	}
	
	@Test
	public void ibmAppscanScan() {
		String key = "IBM Rational AppScan";
		String[][] expectedResults = ibmAppScanResults;
//		String[][] expectedResults = new String[][] {
//					{ PATH_TRAVERSAL, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
//					{ XSS, "Critical", "/demo/EvalInjection2.php", "command"},
//					{ XSS, "Critical", "/demo/XPathInjection2.php", ""},
//					{ XSS, "Critical", "/demo/XPathInjection2.php", "password"},
//					{ XSS, "Critical", "/demo/XPathInjection2.php", "username"},
//					{ XSS, "Critical", "/demo/XSS-reflected2.php", "username"},
//					{ COMMAND_INJECTION, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
//					{ SQLI, "Critical", "/demo/XPathInjection2.php", "password"},
//					{ SQLI, "Critical", "/demo/XPathInjection2.php", "username"},
//					{ INFO_EXPOSURE_ERROR_MESSAGE, "Critical", "/demo/SQLI2.php", "username"},
//					{ GENERIC_INJECTION, "Medium", "/demo/XPathInjection2.php", "password"},
//					{ GENERIC_INJECTION, "Medium", "/demo/XPathInjection2.php", "username"},
//					{ GENERIC_INJECTION, "Medium", "/demo/XSS-reflected2.php", "username"},
//					{ DIRECTORY_LISTING, "Medium", "/demo/DIRECT~1/", ""},
//					{ DIRECTORY_LISTING, "Medium", "/demo/DirectoryIndexing/", ""},
//					{ REFLECTION_ATTACK, "Medium", "/demo/XPathInjection2.php", "password"},
//					{ REFLECTION_ATTACK, "Medium", "/demo/XPathInjection2.php", "username"},
//					{ REFLECTION_ATTACK, "Medium", "/demo/XSS-reflected2.php", "username"},
//					{ FORCED_BROWSING, "Low", "/demo/DIRECT~1/", ""},
//					{ FORCED_BROWSING, "Low", "/demo/DirectoryIndexing/", ""},
//					{ IMPROPER_INPUT_VALIDATION, "Low", "/aux/", ""},
//					{ IMPROPER_INPUT_VALIDATION, "Low", "/cgi-bin/", ""},
//					{ IMPROPER_INPUT_VALIDATION, "Low", "/com1/", ""},
//					{ IMPROPER_INPUT_VALIDATION, "Low", "/com2/", ""},
//					{ IMPROPER_INPUT_VALIDATION, "Low", "/com3/", ""},
//					{ IMPROPER_INPUT_VALIDATION, "Low", "/demo/", ""},
//					{ IMPROPER_INPUT_VALIDATION, "Low", "/demo/aux/", ""},
//					{ IMPROPER_INPUT_VALIDATION, "Low", "/demo/com1/", ""},
//					{ IMPROPER_INPUT_VALIDATION, "Low", "/demo/com2/", ""},
//					{ IMPROPER_INPUT_VALIDATION, "Low", "/demo/com3/", ""},
//					{ INFORMATION_EXPOSURE, "Low", "/demo/PathTraversal.php", ""},
//					{ INFORMATION_EXPOSURE, "Low", "/demo/PredictableResource.php", ""},
//					{ INFORMATION_EXPOSURE, "Low", "/demo/XSS-cookie.php", ""},
//					{ INFO_LEAK_COMMENTS, "Low", "/demo/", ""},
//					{ INFO_LEAK_COMMENTS, "Low", "/demo/SQLI.php", ""},
//					{ INFO_LEAK_COMMENTS, "Low", "/demo/XSS-reflected.php", ""},
//					{ INFO_LEAK_COMMENTS, "Low", "/demo/XSS-reflected2.php", ""},
//					{ INFO_LEAK_TEST_CODE, "Low", "/", ""},
//					{ INFO_LEAK_TEST_CODE, "Low", "/demo/PredictableResource.php", ""},
//					{ INFO_LEAK_SERVER_ERROR, "Info", "/demo/EvalInjection2.php", "command"},
//					{ INFO_LEAK_SERVER_ERROR, "Info", "/demo/LDAPInjection2.php", "username"},
//					{ INFO_LEAK_SERVER_ERROR, "Info", "/demo/SQLI2.php", "username"},
//					{ INFO_LEAK_SERVER_ERROR, "Info", "/demo/XPathInjection2.php", "password"},
//					{ INFO_LEAK_SERVER_ERROR, "Info", "/demo/XPathInjection2.php", "username"},
//			};
		
		runScanTest(key, expectedResults);
	}

	@Test
	public void netsparkerScan(){
		String key = "Mavituna Security Netsparker";
		String[][] expectedResults = netsparkerResults;
//		String[][] expectedResults = new String[] [] {
//				{CODE_INJECTION, "Critical", "/demo/EvalInjection2.php", "command"},
//				{OS_INJECTION, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
//				{RESOURCE_INJECTION, "High", "/demo/OSCommandInjection2.php", "fileName"},
//				{XSS, "High", "/demo/EvalInjection2.php", "command"},
//				{XSS, "High", "/demo/SQLI2.php", "username"},
//				{XSS, "High", "/demo/XPathInjection2.php", "password"},
//				{XSS, "High", "/demo/XPathInjection2.php", "username"},
//				{XSS, "High", "/demo/XSS-reflected2.php", "username"},
//				{SOURCE_CODE_INCLUDE, "Medium", "/demo/OSCommandInjection2.php", "fileName"},
//				{CONFIGURATION, "Low", "/demo/", ""},
//				{FORCED_BROWSING, "Low", "/demo/LDAPInjection.php", ""},
//				{FORCED_BROWSING, "Low", "/demo/PredictableResource.php.bak", ""},
//				{INFORMATION_EXPOSURE, "Low", "/demo/", ""},
//				{INFORMATION_EXPOSURE, "Low", "/demo/PredictableResource.php", ""},
//				{INFO_EXPOSURE_ERROR_MESSAGE, "Low", "/demo/SQLI2.php", "username"},
//				{INFORMATION_EXPOSURE, "Info", "/demo/EvalInjection2.php", ""},
//				{INFORMATION_EXPOSURE, "Info", "/demo/FormatString2.php", ""},
//				{INFORMATION_EXPOSURE, "Info", "/demo/LDAPInjection2.php", ""},
//				{INFORMATION_EXPOSURE, "Info", "/demo/OSCommandInjection2.php", ""},
//				{INFORMATION_EXPOSURE, "Info", "/demo/PathTraversal.php", ""},
//				{INFORMATION_EXPOSURE, "Info", "/demo/SQLI2.php", ""},
//				{INFORMATION_EXPOSURE, "Info", "/demo/XPathInjection2.php", ""},
//				{INFORMATION_EXPOSURE, "Info", "/demo/XSS-cookie.php", ""},
//				{INFORMATION_EXPOSURE, "Info", "/demo/XSS-reflected2.php", ""},
//				{"Information Exposure Through Directory Listing", "Info", "/demo/DirectoryIndexing/", ""},
//		};
		
		runScanTest(key, expectedResults);
	}
	
	
	@Test
	public void skipFishScan(){
		String key = "Skipfish";
		String[][] expectedResults = skipfishResults;
//		String[][] expectedResults = new String [][] {
//					{SQLI, "Critical", "/demo/EvalInjection2.php", "command"},
//					{SQLI, "Critical", "/demo/LDAPInjection2.php", "username"},
//					{SQLI, "Critical", "/demo/SQLI2.php", "username"},
//					{IMPROPER_HANDLING_OF_MISSING_VALUES, "High", "/demo/EvalInjection2.php","command"},
//					{IMPROPER_HANDLING_OF_MISSING_VALUES, "High", "/demo/FormatString2.php","name"},
//					{IMPROPER_HANDLING_OF_MISSING_VALUES, "High", "/demo/PathTraversal.php","action"},
//					{IMPROPER_HANDLING_OF_MISSING_VALUES, "High", "/demo/XSS-cookie.php","cookie"},
//					{IMPROPER_HANDLING_OF_MISSING_VALUES, "High", "/demo/XSS-reflected2.php","username"},
//					{PATH_TRAVERSAL, "High", "/demo/PathTraversal.php","action"},
//					{XSS, "High", "/demo/XSS-cookie.php","cookie"},
//					{XSS, "High", "/demo/XSS-reflected2.php","username"},
//					{DIRECTORY_LISTING, "High", "/demo/DirectoryIndexing/",""},
//					{INFO_LEAK_SERVER_ERROR, "High", "/demo/SQLI2.php","username"},
//					{CSRF, "Medium", "/demo/EvalInjection2.php",""},
//					{CSRF, "Medium", "/demo/FormatString2.php",""},
//					{CSRF, "Medium", "/demo/LDAPInjection2.php",""},
//					{CSRF, "Medium", "/demo/OSCommandInjection2.php",""},
//					{CSRF, "Medium", "/demo/SQLI2.php",""},	
//					{CSRF, "Medium", "/demo/XSS-cookie.php",""},
//					{CSRF, "Medium", "/demo/XSS-reflected2.php",""},
//				
//			};
		
		runScanTest(key, expectedResults);
	}
	
	@Test
	public void ntoSpiderScan() {
		String key = "NTO Spider";
		String[][] expectedResults = ntospiderResults;
//		String[][] expectedResults = new String [][] {
//			{"Improper Authentication", "Critical", "/bank/login.aspx", ""},
//			{"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')", "Critical", "/bank/login.aspx", "passw"},
//			{"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')", "Critical", "/bank/login.aspx", "uid"},
//			{"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')", "Critical", "/subscribe.aspx", "txtEmail"},
//			{"Improper Neutralization of Script-Related HTML Tags in a Web Page (Basic XSS)", "High", "/bank/login.aspx", "uid"},
//			{"Improper Neutralization of Script-Related HTML Tags in a Web Page (Basic XSS)", "High", "/comment.aspx", "name"},
//			{"Improper Neutralization of Script-Related HTML Tags in a Web Page (Basic XSS)", "High", "/notfound.aspx", "aspxerrorpath"},
//			{"Improper Neutralization of Script-Related HTML Tags in a Web Page (Basic XSS)", "High", "/search.aspx", "txtSearch"},
//			{"Information Exposure Through Directory Listing", "Medium", "/bank/", ""},
//			{"Privacy Violation", "Medium", "/", ""},
//			{"Privacy Violation", "Medium", "/bank/login.aspx", ""},
//			{"Privacy Violation", "Medium", "/comment.aspx", ""},
//			{"Privacy Violation", "Medium", "/default.aspx", ""},
//			{"Privacy Violation", "Medium", "/disclaimer.htm", ""},
//			{"Privacy Violation", "Medium", "/feedback.aspx", ""},
//			{"Privacy Violation", "Medium", "/notfound.aspx", ""},
//			{"Privacy Violation", "Medium", "/search.aspx", ""},
//			{"Privacy Violation", "Medium", "/subscribe.aspx", ""},
//			{"Privacy Violation", "Medium", "/survey_questions.aspx", ""},
//			{"Information Exposure Through Environmental Variables", "Low", "/aaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbthbbbbbbbbbbbbb.bbbbbbb", ""},
//		};
		
		runScanTest(key, expectedResults);
	}
	
	
	@Test
	public void w3afScan() {
		
		String key = "w3af";
		String[][] expectedResults = w3afResults;
//		String[][] expectedResults = new String[] [] { 
//			{EVAL_INJECTION,"High", "/demo/EvalInjection2.php","command"},
//			{XSS, "High", "/demo/XSS-cookie.php", "cookie"},
//			{LDAP_INJECTION,"High", "/demo/LDAPInjection2.php","username"},
//			{OS_INJECTION, "High", "/demo/OSCommandInjection2.php", "fileName"},
//			{SQLI,"High", "/demo/SQLI2.php","username"},
//			{XPATH_INJECTION,"Medium", "/demo/XPathInjection2.php","password"},
//			{XPATH_INJECTION,"Medium", "/demo/XPathInjection2.php","username"},
//			{XSS,"Medium", "/demo/EvalInjection2.php","command"},
//			{XSS,"Medium", "/demo/XSS-reflected2.php","username"},
//			{FORMAT_STRING_INJECTION,"Medium", "/demo/FormatString2.php","name"},
//			{FORCED_BROWSING,"Info", "/demo.zip",""},
//			{FORCED_BROWSING,"Info", "/demo/PredictableResource.php.bak",""},
//			
//		};
		
		runScanTest(key, expectedResults);		
	}
	
	@Test
	public void zaproxyScan() {
		String key = "OWASP Zed Attack Proxy";
		String[][] expectedResults = zapProxyResults;
//		String[][] expectedResults = new String [][] {
//			{DIRECTORY_LISTING, "High", "/demo/DirectoryIndexing/", ""},
//			{XSS, "Medium", "/demo/EvalInjection2.php", "command"},
//			{XSS, "Medium", "/demo/XPathInjection2.php", "password"},
//			{XSS, "Medium", "/demo/XPathInjection2.php", "username"},
//			{XSS, "Medium", "/demo/XSS-reflected2.php", "username"},
//			{SQLI, "Medium", "/demo/SQLI2.php", "username"},
//		};

		runScanTest(key, expectedResults);
	}

	@Test
	public void nessusScan() {
		String key = "Nessus";
		String[][] expectedResults = nessusResults;
//		String[][] expectedResults = new String [][] {
//			{OS_INJECTION, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
//			{SQLI, "Critical", "/demo/SQLI2.php", "username"},
//			{FORCED_BROWSING, "Medium", "/demo/PredictableResource.php.bak", ""},
//			{EXTERNAL_FILEPATH_CONTROL, "Medium", "/demo/OSCommandInjection2.php", "fileName"},
//			{XSS, "Medium", "/demo/EvalInjection2.php", "command"},
//			{XSS, "Medium", "/demo/XPathInjection2.php", "password"},
//			{XSS, "Medium", "/demo/XSS-cookie.php", "cookie"},
//			{XSS, "Medium", "/demo/XSS-reflected2.php", "username"},
//			{SESSION_FIXATION, "Medium", "/demo/XSS-reflected2.php", "username"},
//			{DIRECTORY_LISTING, "Low", "/demo/DirectoryIndexing/", ""},
//		};
		
		runScanTest(key, expectedResults);		
	}
	
	@Test
	public void arachniScan() {
		String key = "Arachni";
		String[][] expectedResults = arachniResults;
//		String[][] expectedResults = new String [][] {
//			{XSS, "Critical", "/demo/EvalInjection2.php", "command"},
//			{XSS, "Critical", "/demo/XPathInjection2.php", "password"},
//			{XSS, "Critical", "/demo/XPathInjection2.php", "username"},
//			{XSS, "Critical", "/demo/XSS-reflected2.php", "username"},
//			{LDAP_INJECTION, "Critical", "/demo/LDAPInjection2.php", "username"},
//			{OS_INJECTION, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
//			{SQLI, "Critical", "/demo/SQLI2.php", "username"},
//			{XML_INJECTION, "Critical", "/demo/XPathInjection2.php", "password"},
//			{XML_INJECTION, "Critical", "/demo/XPathInjection2.php", "username"},
//			{INFO_LEAK_DIRECTORIES, "High", "/demo/", ""},
//		};
		
		runScanTest(key, expectedResults);		
	}
	
	
	@Test
	public void webInspectScan() {
		String key = "WebInspect";
		String[][] expectedResults = webInspectResults;
//		String[][] expectedResults = new String [][] {
//				{XSS, "Critical", "/demo/EvalInjection2.php", "command"},
//				{XSS, "Critical", "/demo/XSS-cookie.php", "cookie"},
//				{XSS, "Critical", "/demo/XSS-reflected2.php", "username"},
//				{OS_INJECTION, "Critical", "/demo/OSCommandInjection2.php", "fileName"},
//				{INFORMATION_EXPOSURE, "Critical", "/demo/SQLI2.php", "username"},
//				{INFORMATION_EXPOSURE, "Critical", "/demo/password.txt", ""},
//				{INFORMATION_EXPOSURE, "High", "/demo/OSCommandInjection2.php", "fileName"},
//				{INFORMATION_EXPOSURE, "High", "/demo/PredictableResource.php.BAK", ""},
//				{INFORMATION_EXPOSURE, "High", "/demo/PredictableResource.php.bak", ""},
//				{FORCED_BROWSING, "Medium", "/test.php", ""},
//				{ACCESS_CONTROL, "Medium", "/demo/XPathInjection2.php", ""},
//				{LDAP_INJECTION, "Medium", "/demo/LDAPInjection2.php", ""},
//				{INFORMATION_EXPOSURE, "Medium", "/demo/LDAPInjection2.php", ""},
//				{INFORMATION_EXPOSURE, "Low", "/cgi-bin/test.php", ""},
//				{INFORMATION_EXPOSURE, "Low", "/demo/EvalInjection2.php", ""},
//				{INFORMATION_EXPOSURE, "Low", "/demo/FormatString2.php", ""},
//				{INFORMATION_EXPOSURE, "Low", "/demo/OSCommandInjection2.php", ""},
//				{INFORMATION_EXPOSURE, "Low", "/demo/PathTraversal.php", ""},
//				{INFORMATION_EXPOSURE, "Low", "/demo/PathTraversal.php", "action"},
//				{INFORMATION_EXPOSURE, "Low", "/demo/SQLI2.php", ""},
//				{INFORMATION_EXPOSURE, "Low", "/demo/XPathInjection2.php", ""},
//				{INFORMATION_EXPOSURE, "Low", "/demo/XSS-cookie.php", "cookie"},
//				{INFORMATION_EXPOSURE, "Low", "/demo/XSS-reflected2.php", ""},
//				{INFORMATION_EXPOSURE, "Low", "/test.php", ""},
//				{DIRECTORY_LISTING, "Low", "/cgi-bin/", ""},
//				{DIRECTORY_LISTING, "Low", "/demo/", ""},
//				{INFORMATION_EXPOSURE, "Info", "/", ""},
//		};
		runScanTest(key,expectedResults);
	}
	
	@Test
	public void brakeManScan() {
		String key = "Brakeman";
		String[][] expectedResults = brakemanResults;
//		String[][] expectedResults = new String [][] {
//			{XSS, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/views/users/index.html", "User.new"},
//			{XSS, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/views/users/results.html", "null"},
//			{OS_INJECTION, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:user][:password]"},
//			{OS_INJECTION, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:user][:password]"},
//			{OS_INJECTION, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:user][:password]"},
//			
//			
//			{SQLI, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:query]"},
//			{OPEN_REDIRECT, "Critical", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params"},
//			{CSRF, "High", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/application_controller.rb", "null"},
//			
//			
//			{EXTERNAL_CONTROL_OF_PARAM, "High", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/posts_controller.rb", "params[:post]"},
//			{EXTERNAL_CONTROL_OF_PARAM, "High", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/posts_controller.rb", "params[:post]"},
//			{EXTERNAL_CONTROL_OF_PARAM, "High", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:user]"},
//			
//			{EXTERNAL_CONTROL_OF_PARAM, "High", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "params[:user]"},
//			
//			
//			{ARGUMENT_INJECTION, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/models/user.rb", "null"},
//			
//			{ARGUMENT_INJECTION, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/models/user.rb", "null"},
//			
//			{FORCED_BROWSING, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/config/routes.rb", "null"},
//			{EXTERNAL_CONTROL_OF_PARAM, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/post, user.rb", "null"},
//			
//			{OPEN_REDIRECT, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/posts_controller.rb", "Post.find(params[:id])"},
//			{OPEN_REDIRECT, "Medium", "/presidentbeef-worst-forums-ever-8902d1b/presidentbeef-worst-forums-ever-8902d1b/app/controllers/users_controller.rb", "User.find(params[:id])"},
//		};
		
		runScanTest(key, expectedResults);		

	}
	
	@Test
	public void fortify360Scan() {
		String key = "Fortify 360";
		String[][] expectedResults = fortify360Results;
//		String[][] expectedResults = new String [][] {
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "Address"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "BillingDate"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "BillingDate"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CcfUsed"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CityServices"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CurrentElectricity"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CurrentNaturalGas"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CustomerNumber"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "CustomerNumber"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "KiloWattHourUsed"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "Name"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "Payments"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "PreviousBill"},
//			{XSS, "High", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", "StateLocalTaxes"},
//			{XSS, "High", "/ZigguratUtilityWeb/ContactUs.aspx", "email"},
//			{XSS, "High", "/ZigguratUtilityWeb/ContactUs.aspx", "txtSubject"},
//			{XSS, "High", "/ZigguratUtilityWeb/MakePayment.aspx", "txtCardNumber"},
//			{XSS, "High", "/zigguratutilityweb/message.aspx", "Msg"},
//			{SQLI, "High", "/ZigguratUtilityWeb/LoginPage.aspx", "txtUsername"},
//			{SQLI, "High", "/ZigguratUtilityWeb/ViewStatement.aspx", "StatementID"},
//			{ASP_NET_DEBUG, "Medium", "/ZigguratUtilityWeb/web.config", ""},
//			{ASP_NET_CUSTOM_ERROR, "Medium", "/ZigguratUtilityWeb/web.config", ""},
//			{ASP_NET_VALIDATION_MISSING, "Medium", "/zigguratutilityweb/message.aspx", ""},
//			{IMPROPER_RESOURCE_SHUTDOWN, "Medium", "/ZigguratUtilityWeb/Home.aspx", ""},
//			{IMPROPER_RESOURCE_SHUTDOWN, "Medium", "/ZigguratUtilityWeb/Home.aspx", ""},
//			{IMPROPER_RESOURCE_SHUTDOWN, "Medium", "/ZigguratUtilityWeb/LoginPage.aspx", ""},
//			{IMPROPER_RESOURCE_SHUTDOWN, "Medium", "/ZigguratUtilityWeb/ViewStatement.aspx", ""},
//			{IMPROPER_RESOURCE_SHUTDOWN, "Medium", "/ZigguratUtilityWeb/ViewStatement.aspx", ""},
//			{NON_SERIALIZABLE_OBJECT, "Medium", "/ZigguratUtilityWeb/LoginPage.aspx", ""},
//			{TRUST_BOUNDARY_VIOLATION, "Medium", "/ZigguratUtilityWeb/LoginPage.aspx", ""},
//			{NULL_POINTER, "Medium", "/ZigguratUtilityWeb/Home.aspx", ""},
//			{NULL_POINTER, "Medium", "/ZigguratUtilityWeb/MakePayment.aspx", ""},
//			{NULL_POINTER, "Medium", "/ZigguratUtilityWeb/MakePayment.aspx", ""},
//			{SQLI, "Info", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", ""},
//			{UNCHECKED_ERROR, "Info", "/ZigguratUtilityWeb/App_Code/DBUtil.cs", ""}
//		};
		
		runScanTest(key, expectedResults);
	}

	@Test
	public void acunetixScan() {
		String key = "Acunetix WVS";
		String[][] expectedResults = acunetixResults;
//		String[][] expectedResults = new String [][] {
//			{XSS, "Critical", "/comments.aspx", "tbComment"},
//			{XSS, "Critical", "/readnews.aspx", "NewsAd"},
//			{SQLI, "Critical", "/comments.aspx", "id"},
//			{SQLI, "Critical", "/comments.aspx", "tbComment"},
//			{SQLI, "Critical", "/login.aspx", "tbUsername"},
//			{SQLI, "Critical", "/readnews.aspx", "id"},
//			{CLEARTEXT_SENSITIVE_INFO, "Medium", "/login.aspx", ""},
//			{CLEARTEXT_SENSITIVE_INFO, "Medium", "/signup.aspx", ""},
//			{INFO_EXPOSURE_ERROR_MESSAGE, "Medium", "/default.aspx", "delete"},
//			{INFO_EXPOSURE_ERROR_MESSAGE, "Medium", "/readnews.aspx", "id"},
//			{INFO_EXPOSURE_ERROR_MESSAGE, "Medium", "/readnews.aspx", "NewsAd"},
//			{INFO_EXPOSURE_ERROR_MESSAGE, "Medium", "Web Server", ""},
//			{IMPROPER_RESTRICTION_AUTH, "Low", "/login.aspx", ""},
//			{IMPROPER_RESTRICTION_AUTH, "Low", "/signup.aspx", ""},
//			{INFORMATION_EXPOSURE, "Low", "Web Server", ""},
//			{NON_SECURE_COOKIE, "Low", "/", ""},
//			{FILES_ACCESSIBLE, "Info", "/_vti_cnf", ""},
//			{FILES_ACCESSIBLE, "Info", "/_vti_cnf/acublog.csproj", ""},
//			{FILES_ACCESSIBLE, "Info", "/_vti_cnf/acublog.csproj.webinfo", ""},
//			{FILES_ACCESSIBLE, "Info", "/login.aspx", ""},
//			{FILES_ACCESSIBLE, "Info", "/login.aspx.cs", ""},
//			{FILES_ACCESSIBLE, "Info", "/login.aspx.resx", ""},
//			{FILES_ACCESSIBLE, "Info", "/web.config", ""},
//			{INFO_LEAK_BROWSER_CACHE, "Info", "/login.aspx", ""},
//			{INFO_LEAK_BROWSER_CACHE, "Info", "/signup.aspx", ""},
//		};
		
		runScanTest(key, expectedResults);
	}
	
	@Test
	public void burpScan() {
		String key = "Burp Suite";
		String[][] expectedResults = burpResults;
//		String[][] expectedResults = new String [][] {
//			{XSS, "High", "/demo/EvalInjection2.php", "command"},
//			{XSS, "High", "/demo/XSS-reflected2.php", "username"},
//			{OS_INJECTION, "High", "/demo/OSCommandInjection2.php", "fileName"},
//			{SQLI, "High", "/demo/SQLI2.php", "username"},
//			{IMPROPER_CROSS_BOUNDARY_REMOVAL_OF_DATA, "Info", "/demo/PredictableResource.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/DirectoryIndexing/admin.txt", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/EvalInjection.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/EvalInjection2.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/FormatString.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/FormatString2.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/LDAPInjection.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/LDAPInjection2.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/OSCommandInjection.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/OSCommandInjection2.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/PathTraversal.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/PredictableResource.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/SQLI.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/SQLI2.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XPathInjection.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XPathInjection2.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XSS-cookie.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XSS-reflected.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XSS-reflected2.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XSS-stored.php", ""},
//			{FAILURE_TO_HANDLE_ENCODING, "Info", "/demo/XSS.php", ""},
//			{INFORMATION_EXPOSURE, "Info", "/",""},
//			{DIRECTORY_LISTING,"Info","/demo/DirectoryIndexing/",""},
//		};

		runScanTest(key, expectedResults);
	}
	
	
	public void runScanTest(String scannerName, String[][] expectedResults) {
		teamIndexPage = loginPage.login("user", "password").clickOrganizationHeaderLink();
		
		String orgName = scannerName + getRandomString(10);
		String appName = scannerName + getRandomString(10);
		
		//teamIndexPage.sleep(200);

		applicationDetailPage = teamIndexPage.clickOrganizationHeaderLink()
													 .clickAddTeamButton()
													 .setTeamName(orgName)
													 .addNewTeam()
													 .addNewApplication(orgName, appName, "http://" + scannerName, "Low")
													 .saveApplication(orgName)
													 .clickViewAppLink(appName, orgName)
													 .clickUploadScanLink()
													 .setFileInput(SCAN_FILE_MAP.get(scannerName))
													 .submitScan()
													 .clickExpandAllVulns();
		
		assertTrue("The vuln counts don't match.", applicationDetailPage.getNumRows(expectedResults.length));
		
		String[][] tableResults = new String[expectedResults.length][4];
		for (int i=1; i <= expectedResults.length; i++) {
			String[] thisVuln = new String[] {
					applicationDetailPage.getElementText("type" + i),
					applicationDetailPage.getElementText("severity" + i),
					applicationDetailPage.getElementText("path" + i),
					applicationDetailPage.getElementText("parameter" + i)
				};
			tableResults[i-1] = thisVuln;
		}
		
		outer: for (int i=0; i <= expectedResults.length - 1; i++) {
			for (int j=0; j <= expectedResults.length-1; j++) {
				if (expectedResults[i][0].equals(tableResults[j][0]) &&
						expectedResults[i][1].equals(tableResults[j][1]) &&
						expectedResults[i][2].equals(tableResults[j][2]) &&
						expectedResults[i][3].equals(tableResults[j][3])) {
					continue outer;
				}
			}
			assertTrue("Didn't find a vuln: " + expectedResults[i][0] 
					+ ", " + expectedResults[i][1]
					+ ", " + expectedResults[i][2]
					+ ", " + expectedResults[i][3], 
					false);
		}
		
		applicationDetailPage = applicationDetailPage.clickViewScansLink();
		assertTrue("Scan Count is incorrect.", applicationDetailPage.isScanCountCorrect(1));	
		assertTrue("Scan Tab is incorrect.", applicationDetailPage.isScanPresent(scannerName));
		
		int scanCount = applicationDetailPage.scanCount();
		//duplicate scan checking
		applicationDetailPage = applicationDetailPage.clickUploadScanLink()
		 											.setFileInput(SCAN_FILE_MAP.get(scannerName))
		 											.submitScanInvalid();
		
		assertTrue("Duplicate error not displayed",applicationDetailPage.isDuplicateScan());
		
		applicationDetailPage.clickCloseScanUploadModal()
							.clickVulnTab()
							.clickExpandAllVulns();
		
		assertTrue("Scan count is incorrect", scanCount == applicationDetailPage.scanCount());
		
		for (int i=1; i <= expectedResults.length; i++) {
			String[] thisVuln = new String[] {
					applicationDetailPage.getElementText("type" + i),
					applicationDetailPage.getElementText("severity" + i),
					applicationDetailPage.getElementText("path" + i),
					applicationDetailPage.getElementText("parameter" + i)
				};
			tableResults[i-1] = thisVuln;
		}
		
		outer: for (int i=0; i <= expectedResults.length - 1; i++) {
			for (int j=0; j <= expectedResults.length-1; j++) {
				if (expectedResults[i][0].equals(tableResults[j][0]) &&
						expectedResults[i][1].equals(tableResults[j][1]) &&
						expectedResults[i][2].equals(tableResults[j][2]) &&
						expectedResults[i][3].equals(tableResults[j][3])) {
					continue outer;
				}
			}
			assertTrue("Didn't find a vuln after duplicate scan upload: " + expectedResults[i][0] 
					+ ", " + expectedResults[i][1]
					+ ", " + expectedResults[i][2]
					+ ", " + expectedResults[i][3], 
					false);
		}
		
		assertTrue("Unexpected vulns were added", applicationDetailPage.getNumRows(expectedResults.length));
		
		
		applicationDetailPage.clickOrganizationHeaderLink()
							.clickViewTeamLink(orgName)
							.clickDeleteButton();
	}
}
