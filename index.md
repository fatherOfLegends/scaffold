Scaffold
========

An Android library designed to help you quickly create prototypes and production applications that follow the latest material
design guidelines.

For more information see [the website][4] or [the wiki][1].

Gradle
------

Scaffold is available via jcenter. It depends on several of the [Android support libraries][2]:

```
dependencies {
    ...


    def androidSupportLibVersion = "25.0.1";

    compile "com.lamcreations.scaffold:scaffold:${androidSupportLibVersion}"
    compile "com.android.support:support-core-utils:${androidSupportLibVersion}";
    compile "com.android.support:support-core-ui:${androidSupportLibVersion}";
    compile "com.android.support:appcompat-v7:${androidSupportLibVersion}";
    compile "com.android.support:recyclerview-v7:${androidSupportLibVersion}";
    compile "com.android.support:preference-v7:${androidSupportLibVersion}";
    compile "com.android.support:design:${androidSupportLibVersion}";
    compile "com.android.support:support-annotations:${androidSupportLibVersion}";
}
```

Usage
-----
Scaffold is a collection of activities, fragments and views mostly designed to be extended. By using
these classes and following the conventions of this library you can very quickly create an Android app
that follows the [material design guidelines][3] without needing to worry about a lot of boiler plate code.

Activities that you can extend:
*  BottomNavigationActivity
*  ToolbarActivity
*  ToolbarBottomNavigationActivity
*  CoordinatorActivity
*  CoordinatorBottomNavigationActivity
*  DrawerActivity
*  TabActivity
*  TabDrawerActivity
*  CollapsingToolbarActivity
*  CollapsingToolbarDrawerActivity
*  CollapsingToolbarTabDrawerActivity
*  SearchableActivity
*  SettingsActivity
*  SplashScreenActivity
*  VideoSplashScreenActivity

Fragments that you can extend:
*  RecyclerViewFragment
*  SettingsFragment
*  NavigationViewFragment
*  NavigationRecyclerViewFragment

Views that you can use/extend:
*  NavigationViewHeader
*  AutoFitTextureView
*  RoundedImageView
*  CollapsingToolbarTabLayout

Other classes you might find helpful:
*  ImagePagerAdapter
*  BasicRecyclerViewAdapter
*  FabBehavior
*  BottomNavigationBehavior

For more information see [the wiki][1].

License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

Contributing
------------

If you would like to contribute code to Scaffold you can do so through GitHub by
forking the repository and sending a pull request.

When submitting code, please make every effort to follow existing conventions
and style in order to keep the code as readable as possible.


 [1]: https://github.com/lmckenzie/scaffold/wiki
 [2]: http://developer.android.com/tools/support-library/features.html
 [3]: https://www.google.com/design/spec/material-design/introduction.html
 [4]: http://lmckenzie.github.io/scaffold
