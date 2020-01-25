# MatchScraper

This tool was designed to get data from eredmenyek.com (the hungarian version of flashscore).
I wrote it to get data for one of my other projects.

## Description
This program can get all the information about a soccer league. It works fully automatically,
you just have to provide a link 
_(example: https://www.eredmenyek.com/foci/spanyolorszag/laliga-2018-2019/eredmenyek/)_
to it and you will get a json file with all the match information. (date, result, teams, round, all of the statistics,
lineup, etc.)

## Getting started
This program was written in Java using Gradle and Selenium webdriver.

#### Prerequisites
You need the following to run this tool:
* Java
* Selenium
    * Webdriver (this was tested on chromedriver, but you can try other webdrivers too)


## Important note
This tool was written to be usable at the time of development. So if flashscore changes certain 
aspects of the site I can not guarantee the usability of the tool. But feel free to modify it 
to suit your needs.
