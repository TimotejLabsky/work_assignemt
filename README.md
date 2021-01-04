[![Tests](https://github.com/TimotejLabsky/work_assignemt/workflows/tests/badge.svg)](https://github.com/TimotejLabsky/work_assignemt/actions)
# Work assignment #

## Description ##
* write a simple program whose output is formatted similar to a receipt you would receive at a supermarket
* the input to the program is a list of products the shopper has in their basket e.g. "SIM card", "phone case"...

## Subjects to follow ##
* a sales tax of 12% is added to all purchases, but insurance products are exempt
* SIM cards are Buy One Get One Free (BOGOF)
* insurance is discounted by 20% if you buy any type of earphone
* the law prevents anyone buying more than 10 SIM cards in a single purchase

## Data warehouse ##
product data are parsed from file - [/resources/warehouse.txt](https://github.com/TimotejLabsky/work_assignemt/tree/main/src/main/resources)
### File format ###
* fields are separated by: `;`

`type`;`name`;`price`;`currency`;

#### Feature proposal ####
extend the file format by `sale_1_type`;`sale_1_arg` ... and remove hardcoded sales from Product Factory

## Requirements ##
Run:
* [java 14 +](https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html)

Tests:
* [Maven](https://maven.apache.org/)