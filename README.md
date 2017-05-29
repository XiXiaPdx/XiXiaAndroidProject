# Appetizing
- Android capstone project for Epicodus coding school.

![Zoo of Mythical Creatures](./readMeRes/sandwich.jpg)

Have you ever been hungry but didn't know what you wanted to eat?

Have you ever been inspired by beautiful photography?

hungry + photographic inspiration =  Appetizing!

## What is Appetizing

Appetizing is a stream of category=food photography from the UnSplash API.  

The user is hungry and undecided. As they scroll through the beautiful hi-res photos, their visual senses will engage to help them make a decision on what to eat.

Once they have decided, they can click on a photo and it will generate a Yelp list of restaurants near them that serve that type of food.

## Planning

#### Phase 1

- [x] Test UnSplash API in browser. Return Json.
- [ ] Create UnSplashService class and return LogCat response.
- [ ] transform response into Picture object
- [ ] Create Recycler View for UnSplash on main
- [ ] Create Button on View Item To Search local
- [ ] Button intent moves to RestaurantActivity
- [ ] Style Content

### 4. Components

FilteredAnimals

FilterTool

Add New Animal

Edit Current Animal


### 5. Specs


| Behavior | Input Example| Output Example    |
| ---------| -----------------|-----------------|
|create a new Animal Object|new Animal(params)| Animal |

# Technologies

Angular 2, TypeScript, JavaScript, Jquery, HTML5, CSS
[Angular Material](https://material.angular.io/) and [Materialize](http://materializecss.com/) for front-end css frameworks.

## Prerequisites

You will need the following software properly installed on your computer.

* [Git](https://git-scm.com/)
* [Node.js](https://nodejs.org/) (with NPM)
* [Bower](https://bower.io/)


## Installation

Perform the following  steps at your terminal prompt

* #### Enter the following to download the app files.
```
git clone https://github.com/XiXiaPdx/zoo-angular.git
```
*  #### get into the app folder
```
cd ng-zoo
```
*  #### install node dependencies
```
npm install
```
* #### install bower dependencies
```
bower install
```
* #### gulp build
```
gulp build
```
* #### gulp serve
```
gulp serve
```

## Further Exploration

The code isn't efficient. The filtering mechanism runs extra loops that are not necessary.

## License

Copyright (c) 2017 Xi Xia. This software is licensed under the MIT license.
