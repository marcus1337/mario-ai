/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */


public class exampleJNI {
  public final static native long new_JavaPorts(int jarg1);
  public final static native void JavaPorts_init__SWIG_0(long jarg1, JavaPorts jarg1_, int jarg2, int jarg3, int jarg4, int jarg5, int jarg6, int jarg7);
  public final static native void JavaPorts_init__SWIG_1(long jarg1, JavaPorts jarg1_, int jarg2, int jarg3, int jarg4);
  public final static native int JavaPorts_getGeneration(long jarg1, JavaPorts jarg1_);
  public final static native String JavaPorts_getTreeString(long jarg1, JavaPorts jarg1_, int jarg2);
  public final static native void JavaPorts_saveGeneration(long jarg1, JavaPorts jarg1_, String jarg2);
  public final static native void JavaPorts_loadGeneration(long jarg1, JavaPorts jarg1_, String jarg2, int jarg3);
  public final static native void JavaPorts_evolve(long jarg1, JavaPorts jarg1_);
  public final static native void JavaPorts_setFitness(long jarg1, JavaPorts jarg1_, int jarg2, int jarg3);
  public final static native void delete_JavaPorts(long jarg1);
}
