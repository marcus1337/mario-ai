/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */


public class JavaPorts {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected JavaPorts(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(JavaPorts obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        exampleJNI.delete_JavaPorts(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public JavaPorts(AIType _aitype) {
    this(exampleJNI.new_JavaPorts(_aitype.swigValue()), true);
  }

  public void init(int numTrees, int maxOtherInteriors, int maxUnorderedInteriors, int maxDecorators, int maxActions, int maxConditions) {
    exampleJNI.JavaPorts_init__SWIG_0(swigCPtr, this, numTrees, maxOtherInteriors, maxUnorderedInteriors, maxDecorators, maxActions, maxConditions);
  }

  public void init(int numInputNodes, int numOutputNode, int numAIs) {
    exampleJNI.JavaPorts_init__SWIG_1(swigCPtr, this, numInputNodes, numOutputNode, numAIs);
  }

  public int getGeneration() {
    return exampleJNI.JavaPorts_getGeneration(swigCPtr, this);
  }

  public String getTreeString(int index) {
    return exampleJNI.JavaPorts_getTreeString(swigCPtr, this, index);
  }

  public void saveGeneration(String filename) {
    exampleJNI.JavaPorts_saveGeneration(swigCPtr, this, filename);
  }

  public void loadGeneration(String filename, int generation) {
    exampleJNI.JavaPorts_loadGeneration(swigCPtr, this, filename, generation);
  }

  public void evolve() {
    exampleJNI.JavaPorts_evolve(swigCPtr, this);
  }

  public void setFitness(int index, int fitness) {
    exampleJNI.JavaPorts_setFitness(swigCPtr, this, index, fitness);
  }

}
