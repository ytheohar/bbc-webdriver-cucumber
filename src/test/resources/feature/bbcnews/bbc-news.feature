Feature: Navigate to video page and share it on facebook
	The user should be able to navigate through news categories, select a video page and share it on facebook


	Scenario: Navigate to 'News' page by clicking on 'News headlines'
	    Given the user is landed at the home page
	    When the user clicks on 'News headlines'
	    Then the 'News' page loads

	Scenario: Navigate to 'News' page by clicking on 'News' on the navigation bar
	    Given the user is landed at the home page
	    When the user clicks 'News' on the navigation bar
	    Then the 'News' page loads

	Scenario Outline: Navigate to a Video Page and share it on Facebook
	    Given the user is landed at the home page
	    When the user clicks 'News' on the navigation bar
	    And the user clicks '<category>' on the inner news navigation bar
		And the user clicks on the first video on the right hand 'Watch/Listen' section
		And the user clicks on the facebook share icon
		Then the facebook page loads having a url that includes the video page url

    	Examples:
      	| category  			|
      	| UK					|
      	| Business				|
      	| Politics				|
      	| Tech					|
      	| Science				|
      	| Health				|
      	| Education				|
      	| Entertainment & Arts	|

