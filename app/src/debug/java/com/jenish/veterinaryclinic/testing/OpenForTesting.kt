package com.jenish.veterinaryclinic.testing

/**
 * Developed By JENISH KHANPARA on 24 June 2020.
 */

/**
 * This annotation allows us to open some classes for mocking purposes while they are final in
 * release builds.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass

/**
 * Annotate a class with [OpenForTesting] if you want it to be extendable in debug builds.
 */
@OpenClass
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting

// Forked from https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/debug/java/com/android/example/github/testing/OpenForTesting.kt