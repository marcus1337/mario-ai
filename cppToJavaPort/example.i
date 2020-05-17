/* File : example.i */
%module example
%include <std_string.i>

%typemap(jni) const string & "jstring"
%typemap(jtype) const string & "String"
%typemap(jstype) const string & "String"
%typemap(javadirectorin) const string & "$jniinput"
%typemap(javadirectorout) const string & "$javacall"

%{
#include "JavaPorts.h"
%}

/* Let's just grab the original header file here */
%include "JavaPorts.h"
