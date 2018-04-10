# Simple Calculator 
<img alt="Logo" src="commons/src/main/res/mipmap-xxxhdpi/ic_launcher.png" width="80">

**Android app:** [![Build Status](https://travis-ci.org/jusleg/simple-calculator-FAMINGO.svg?branch=master)](https://travis-ci.org/jusleg/simple-calculator-FAMINGO) [![codebeat badge](https://codebeat.co/badges/9ce2c059-5bb7-46bd-b512-a746ce275690)](https://codebeat.co/projects/github-com-jusleg-simple-calculator-master)

**[seshat-web server](https://github.com/jusleg/seshat-web):** [![Build Status](https://travis-ci.org/jusleg/seshat-web.svg?branch=master)](https://travis-ci.org/jusleg/seshat-web) [![codebeat badge](https://codebeat.co/badges/89221ca6-ae12-4acf-a4c5-a967f2f8a77e)](https://codebeat.co/projects/github-com-jusleg-seshat-web-master) [![Dependency Status](https://gemnasium.com/badges/github.com/jusleg/seshat-web.svg)](https://gemnasium.com/github.com/jusleg/seshat-web) [![Maintainability](https://api.codeclimate.com/v1/badges/8a958baf7fd286770d40/maintainability)](https://codeclimate.com/github/jusleg/seshat-web/maintainability)

A calculator with the basic functions, money operations, unit conversions and handwritten equation analysis. It is fully opensource and provides customizable colors. The application is also built for Android Wear. This application is based on a fork of [Simple Calculator by Simple Tools](https://github.com/SimpleMobileTools/Simple-Calculator) and was built for our SOEN 390 class (Software Engineering Team Design Project).

Check out our project [wiki](https://github.com/jusleg/simple-calculator/wiki)!

## Screenshots

<img width="250" alt="Main Calculator" src="https://user-images.githubusercontent.com/4406751/38581965-4645bc46-3cdc-11e8-978f-ca166ca8bbda.png"> <img width="250" alt="Money view" src="https://user-images.githubusercontent.com/4406751/38581999-62d1e83a-3cdc-11e8-9269-db4a887b94cb.png"> <img width="250" alt="Draw Equations" src="https://user-images.githubusercontent.com/4406751/38582150-e0edc05e-3cdc-11e8-92c8-4b61fdf0aff9.png">

**Android Wear**

<img width="250" alt="Android Wear" src="https://camo.githubusercontent.com/692db6d0eb1172a33d81b02e93aeadc82ca05676/68747470733a2f2f6d656469612e67697068792e636f6d2f6d656469612f32354c757736496d446e47356530494c59702f67697068792e676966">

## Seshat-web
In order to analyse the handwritten equation, a [seshat](https://github.com/falvaro/seshat) server was created. The application sends the drawing as a sequence of strokes in a [`sgcink`](https://www.scg.uwaterloo.ca/mathbrush/publications/corpus.pdf) format. The server returns the result in LaTeX syntax. This is used to query wolfram alpha to show the analysis of the function. [Seshat-web](https://github.com/jusleg/seshat-web) is a sinatra server that interfaces with the C++ code of the seshat program.

## Contributing

To submit new changes to the codebase, a pull request must be opened. The pull request must have the following:
  1. Name that describes the modification and includes reference(s) to issue(s)
  2. Meaningful commit message that points to the issue number
  3. Follow the [pull request template](https://github.com/jusleg/simple-calculator-FAMINGO/blob/master/.github/PULL_REQUEST_TEMPLATE.md)
  4. At least 2 independent reviews that follow the [pull request review template](https://github.com/jusleg/simple-calculator-FAMINGO/blob/master/.github/PULL_REQUEST_REVIEW_TEMPLATE.md)
  5. Code that follows the conventions outlined by the [Kotlin Foundation](https://kotlinlang.org/docs/reference/coding-conventions.html)

## Submitting Issues

User facing features are represented as user stories which are further broken down into tasks. Very large user stories may be broken down into multiple user stories and put into an epic. All issues submitted must follow one of the following templates.

* **[User Story Template](https://github.com/jusleg/simple-calculator-FAMINGO/blob/master/.github/ISSUE_TEMPLATE.md)**
* **[Task Template](https://github.com/jusleg/simple-calculator-FAMINGO/blob/master/.github/TASK_TEMPLATE.md)**
* **[Epic Template](https://github.com/jusleg/simple-calculator-FAMINGO/blob/master/.github/EPIC_TEMPLATE.md)**

## Testing locally
### 1. Linting

To test if it passes the linting checks run `./gradlew ktlint`. Use `./gradlew ktlintFormat` to automatically format the code to follow the conventions

### 2. Unit tests

`./gradlew test`

### 3. Instrumentation tests

Start an Android emulator API 22. When the emulator is booted, run `./gradlew clean connectedAndroidTest`

**N.B.** The static analysis done by Codebeat cannot be run locally. It is triggered by new branches and PR.

## License

    Copyright 2017 SimpleMobileTools

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
