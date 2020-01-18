Feature: Sign In Page
  In order to test sign in page
  As a registered user
  I want to specify the sign in conditions
@sanity
  Scenario Outline: Sign in with valid credentials
    Given I am a "<user>" of Lolaflora.com
    When I sign in using valid  "<credential>"
    Then I should be logged in
    Examples: 
						|user				 								 |credential				|
						|testerselenium34@yopmail.com|test1234          |
 
@sanity
  Scenario Outline: Sign in with invalid credentials
    Given I am a not "<user>" of Lolaflora.com
    When I sign in using invalid  "<credential>"
    Then I should not be logged in and seen "<popupmessage>"
		Examples:
			  	 	|user								          |popupmessage					       																													|  credential				|	 		 
					  |testerselenium34@yopmail.com|E-mail address or password is incorrect. Please check your information and try again. |  tesr1230					| 



Scenario Outline: Try to sign in with invalid password
     Given I am a "<user>" of Lolaflora.com
     When I click forgot password button
     And     I fulfill valid "<user>" address
    Then    "<information>" should be seen correctly
	Examples:
					|user 								       |information																																			 | 		 
					|testerselenium34@yopmail.com|You will receive an e-mail from us with instructions for resetting your password.|     