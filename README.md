# Gradle for Android and Java Udacity Project

The project is part and 4th project of Udacity Android Developer Nanodegree. Project is a multiproject demonstrating various ways to use Gradle: Java and Android libraries, build variants and flavors enabling apps with and without mobile adds, and integration testing with development server. Gradle App Engine Plugin, Google cloud endpoints framework and App Engine development server are used.

## General
App loads jokes from Google Cloud Endpoints module and displays them using local Android Library. This is simulated with App Engine server running locally.
Project contains
- A Java library for supplying jokes.
- An Android library with an activity that displays jokes passed to it as intent extras.
- Connected tests to verify that the AsyncTask is indeed loading jokes.
- Paid/free flavors. The paid flavor has no ads and no unnecessary dependencies.
- Ads in the free version.
- Loading indicator while the joke is being fetched from the server.


License
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

