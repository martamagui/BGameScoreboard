// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.application) version Version.application apply false
    id(Plugins.kotlinAndroid) version Version.kotlinAndroid apply false
    id(Plugins.ksp) version Version.ksp apply false
    id(Plugins.hilt) version Version.hilt apply false
}