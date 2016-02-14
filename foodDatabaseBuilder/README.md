Food Database builder

This project consists of 4 parts - 

a.  Food ID Collector - This module will fetch a list of food Ids from USDA Database. These food Ids are stored under MYSQL DB, under table FoodId.

b.  Food Database Builder -  This module will iterate over the list of food Ids in MYSQL DB and fetch the food and nutrient information for each of the food items. The food and the nutrient information is stored in MongoDB.

c.  Food Info - NoSQL to SQL mapper for food information.

d . Food Nutrient Info - NoSQL to SQL mapper for food nutrient information.