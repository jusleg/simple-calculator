# Simple Calculator
<img alt="Logo" src="commons/src/main/res/mipmap-xxxhdpi/ic_launcher.png" width="80">

[![Build Status](https://travis-ci.org/jusleg/simple-calculator-FAMINGO.svg?branch=master)](https://travis-ci.org/jusleg/simple-calculator-FAMINGO) [![codebeat badge](https://codebeat.co/badges/9ce2c059-5bb7-46bd-b512-a746ce275690)](https://codebeat.co/projects/github-com-jusleg-simple-calculator-master)

Check out our project [wiki](https://github.com/jusleg/simple-calculator/wiki)!

A calculator with the basic functions and money operations. A fork of [Simple Calculator by Simple Tools](https://github.com/SimpleMobileTools/Simple-Calculator) used for our SOEN 390 class.

You can copy the result or formula to clipboard by long pressing it.

Contains no ads or unnecessary permissions. It is fully opensource, provides customizable colors.

<img alt="App image" src="screenshots/app.png" width="250">
<img alt="App image" src="screenshots/app_2.png" width="250">

## Contributing

To submit new changes to the codebase, a pull request must be opened. The pull request must have the following:
  1. Name that describes the modification and includes reference(s) to issue(s)
  2. Meaningful commit message that points to the issue number
  3. Follow the [pull request template](https://github.com/jusleg/simple-calculator-FAMINGO/blob/master/.github/PULL_REQUEST_TEMPLATE.md)
  4. At least 2 independent reviews that follow the [pull request review template](https://github.com/jusleg/simple-calculator-FAMINGO/blob/master/.github/PULL_REQUEST_REVIEW_TEMPLATE.md)
  5. Code that follows the conventions outlined by the [Kotlin Foundation](https://kotlinlang.org/docs/reference/coding-conventions.html)

## Testing

This app contains UI and unit tests that can be ran with the following instructions.

<h3>Running Espresso UI tests</h3>
<p>1. Run -> Edit Configurations</p>
<p>2. Create a new "Android Instrumentation Tests" configuration, give it a name (i.e. "MainActivityEspressoTest")</p>
<p>3. Choose the "app" module</p>
<p>4. OK</p>
<p>5. Make sure MainActivityEspressoTest is selected near the Run button</p>
<p>6. Run</p>

<h3>Running Robolectric tests</h3>
<p>1. At the Project tab right click the folder containing the tests (i.e. "calculator.simplemobiletools.com.simple_calculator (test)")</p>
<p>2. select Run 'Tests in 'calculator.simplemob...' to run all the tests</p>
<p>3. if you are on Linux or Mac, go to Run -> Edit Configurations, select the new JUnit configuration and change the "Working Directory" item to "$MODULE_DIR$" (without quotes)</p>
<p>4. OK</p>
<p>5. Run</p>

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
