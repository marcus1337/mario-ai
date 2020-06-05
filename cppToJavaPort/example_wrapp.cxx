/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.1
 *
 * This file is not intended to be easily readable and contains a number of
 * coding conventions designed to improve portability and efficiency. Do not make
 * changes to this file unless you know what you are doing--modify the SWIG
 * interface file instead.
 * ----------------------------------------------------------------------------- */


#ifndef SWIGJAVA
#define SWIGJAVA
#endif



#ifdef __cplusplus
/* SwigValueWrapper is described in swig.swg */
template<typename T> class SwigValueWrapper {
  struct SwigMovePointer {
    T *ptr;
    SwigMovePointer(T *p) : ptr(p) { }
    ~SwigMovePointer() { delete ptr; }
    SwigMovePointer& operator=(SwigMovePointer& rhs) { T* oldptr = ptr; ptr = 0; delete oldptr; ptr = rhs.ptr; rhs.ptr = 0; return *this; }
  } pointer;
  SwigValueWrapper& operator=(const SwigValueWrapper<T>& rhs);
  SwigValueWrapper(const SwigValueWrapper<T>& rhs);
public:
  SwigValueWrapper() : pointer(0) { }
  SwigValueWrapper& operator=(const T& t) { SwigMovePointer tmp(new T(t)); pointer = tmp; return *this; }
  operator T&() const { return *pointer.ptr; }
  T *operator&() { return pointer.ptr; }
};

template <typename T> T SwigValueInit() {
  return T();
}
#endif

/* -----------------------------------------------------------------------------
 *  This section contains generic SWIG labels for method/variable
 *  declarations/attributes, and other compiler dependent labels.
 * ----------------------------------------------------------------------------- */

/* template workaround for compilers that cannot correctly implement the C++ standard */
#ifndef SWIGTEMPLATEDISAMBIGUATOR
# if defined(__SUNPRO_CC) && (__SUNPRO_CC <= 0x560)
#  define SWIGTEMPLATEDISAMBIGUATOR template
# elif defined(__HP_aCC)
/* Needed even with `aCC -AA' when `aCC -V' reports HP ANSI C++ B3910B A.03.55 */
/* If we find a maximum version that requires this, the test would be __HP_aCC <= 35500 for A.03.55 */
#  define SWIGTEMPLATEDISAMBIGUATOR template
# else
#  define SWIGTEMPLATEDISAMBIGUATOR
# endif
#endif

/* inline attribute */
#ifndef SWIGINLINE
# if defined(__cplusplus) || (defined(__GNUC__) && !defined(__STRICT_ANSI__))
#   define SWIGINLINE inline
# else
#   define SWIGINLINE
# endif
#endif

/* attribute recognised by some compilers to avoid 'unused' warnings */
#ifndef SWIGUNUSED
# if defined(__GNUC__)
#   if !(defined(__cplusplus)) || (__GNUC__ > 3 || (__GNUC__ == 3 && __GNUC_MINOR__ >= 4))
#     define SWIGUNUSED __attribute__ ((__unused__))
#   else
#     define SWIGUNUSED
#   endif
# elif defined(__ICC)
#   define SWIGUNUSED __attribute__ ((__unused__))
# else
#   define SWIGUNUSED
# endif
#endif

#ifndef SWIG_MSC_UNSUPPRESS_4505
# if defined(_MSC_VER)
#   pragma warning(disable : 4505) /* unreferenced local function has been removed */
# endif
#endif

#ifndef SWIGUNUSEDPARM
# ifdef __cplusplus
#   define SWIGUNUSEDPARM(p)
# else
#   define SWIGUNUSEDPARM(p) p SWIGUNUSED
# endif
#endif

/* internal SWIG method */
#ifndef SWIGINTERN
# define SWIGINTERN static SWIGUNUSED
#endif

/* internal inline SWIG method */
#ifndef SWIGINTERNINLINE
# define SWIGINTERNINLINE SWIGINTERN SWIGINLINE
#endif

/* exporting methods */
#if defined(__GNUC__)
#  if (__GNUC__ >= 4) || (__GNUC__ == 3 && __GNUC_MINOR__ >= 4)
#    ifndef GCC_HASCLASSVISIBILITY
#      define GCC_HASCLASSVISIBILITY
#    endif
#  endif
#endif

#ifndef SWIGEXPORT
# if defined(_WIN32) || defined(__WIN32__) || defined(__CYGWIN__)
#   if defined(STATIC_LINKED)
#     define SWIGEXPORT
#   else
#     define SWIGEXPORT __declspec(dllexport)
#   endif
# else
#   if defined(__GNUC__) && defined(GCC_HASCLASSVISIBILITY)
#     define SWIGEXPORT __attribute__ ((visibility("default")))
#   else
#     define SWIGEXPORT
#   endif
# endif
#endif

/* calling conventions for Windows */
#ifndef SWIGSTDCALL
# if defined(_WIN32) || defined(__WIN32__) || defined(__CYGWIN__)
#   define SWIGSTDCALL __stdcall
# else
#   define SWIGSTDCALL
# endif
#endif

/* Deal with Microsoft's attempt at deprecating C standard runtime functions */
#if !defined(SWIG_NO_CRT_SECURE_NO_DEPRECATE) && defined(_MSC_VER) && !defined(_CRT_SECURE_NO_DEPRECATE)
# define _CRT_SECURE_NO_DEPRECATE
#endif

/* Deal with Microsoft's attempt at deprecating methods in the standard C++ library */
#if !defined(SWIG_NO_SCL_SECURE_NO_DEPRECATE) && defined(_MSC_VER) && !defined(_SCL_SECURE_NO_DEPRECATE)
# define _SCL_SECURE_NO_DEPRECATE
#endif

/* Deal with Apple's deprecated 'AssertMacros.h' from Carbon-framework */
#if defined(__APPLE__) && !defined(__ASSERT_MACROS_DEFINE_VERSIONS_WITHOUT_UNDERSCORES)
# define __ASSERT_MACROS_DEFINE_VERSIONS_WITHOUT_UNDERSCORES 0
#endif

/* Intel's compiler complains if a variable which was never initialised is
 * cast to void, which is a common idiom which we use to indicate that we
 * are aware a variable isn't used.  So we just silence that warning.
 * See: https://github.com/swig/swig/issues/192 for more discussion.
 */
#ifdef __INTEL_COMPILER
# pragma warning disable 592
#endif


/* Fix for jlong on some versions of gcc on Windows */
#if defined(__GNUC__) && !defined(__INTEL_COMPILER)
  typedef long long __int64;
#endif

/* Fix for jlong on 64-bit x86 Solaris */
#if defined(__x86_64)
# ifdef _LP64
#   undef _LP64
# endif
#endif

#include <jni.h>
#include <stdlib.h>
#include <string.h>


/* Support for throwing Java exceptions */
typedef enum {
  SWIG_JavaOutOfMemoryError = 1,
  SWIG_JavaIOException,
  SWIG_JavaRuntimeException,
  SWIG_JavaIndexOutOfBoundsException,
  SWIG_JavaArithmeticException,
  SWIG_JavaIllegalArgumentException,
  SWIG_JavaNullPointerException,
  SWIG_JavaDirectorPureVirtual,
  SWIG_JavaUnknownError,
  SWIG_JavaIllegalStateException,
} SWIG_JavaExceptionCodes;

typedef struct {
  SWIG_JavaExceptionCodes code;
  const char *java_exception;
} SWIG_JavaExceptions_t;


static void SWIGUNUSED SWIG_JavaThrowException(JNIEnv *jenv, SWIG_JavaExceptionCodes code, const char *msg) {
  jclass excep;
  static const SWIG_JavaExceptions_t java_exceptions[] = {
    { SWIG_JavaOutOfMemoryError, "java/lang/OutOfMemoryError" },
    { SWIG_JavaIOException, "java/io/IOException" },
    { SWIG_JavaRuntimeException, "java/lang/RuntimeException" },
    { SWIG_JavaIndexOutOfBoundsException, "java/lang/IndexOutOfBoundsException" },
    { SWIG_JavaArithmeticException, "java/lang/ArithmeticException" },
    { SWIG_JavaIllegalArgumentException, "java/lang/IllegalArgumentException" },
    { SWIG_JavaNullPointerException, "java/lang/NullPointerException" },
    { SWIG_JavaDirectorPureVirtual, "java/lang/RuntimeException" },
    { SWIG_JavaUnknownError,  "java/lang/UnknownError" },
    { SWIG_JavaIllegalStateException, "java/lang/IllegalStateException" },
    { (SWIG_JavaExceptionCodes)0,  "java/lang/UnknownError" }
  };
  const SWIG_JavaExceptions_t *except_ptr = java_exceptions;

  while (except_ptr->code != code && except_ptr->code)
    except_ptr++;

  jenv->ExceptionClear();
  excep = jenv->FindClass(except_ptr->java_exception);
  if (excep)
    jenv->ThrowNew(excep, msg);
}


/* Contract support */

#define SWIG_contract_assert(nullreturn, expr, msg) if (!(expr)) {SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, msg); return nullreturn; } else


#include <string>


#include <typeinfo>
#include <stdexcept>


#include <vector>
#include <stdexcept>


/* Check for overflow converting to Java int (always signed 32-bit) from (unsigned variable-bit) size_t */
SWIGINTERN jint SWIG_JavaIntFromSize_t(size_t size) {
  static const jint JINT_MAX = 0x7FFFFFFF;
  return (size > (size_t)JINT_MAX) ? -1 : (jint)size;
}


SWIGINTERN jint SWIG_VectorSize(size_t size) {
  jint sz = SWIG_JavaIntFromSize_t(size);
  if (sz == -1)
    throw std::out_of_range("vector size is too large to fit into a Java int");
  return sz;
}

SWIGINTERN std::vector< int > *new_std_vector_Sl_int_Sg___SWIG_2(jint count,int const &value){
        if (count < 0)
          throw std::out_of_range("vector count must be positive");
        return new std::vector< int >(static_cast<std::vector< int >::size_type>(count), value);
      }
SWIGINTERN jint std_vector_Sl_int_Sg__doSize(std::vector< int > const *self){
        return SWIG_VectorSize(self->size());
      }
SWIGINTERN void std_vector_Sl_int_Sg__doAdd__SWIG_0(std::vector< int > *self,std::vector< int >::value_type const &x){
        self->push_back(x);
      }
SWIGINTERN void std_vector_Sl_int_Sg__doAdd__SWIG_1(std::vector< int > *self,jint index,std::vector< int >::value_type const &x){
        jint size = static_cast<jint>(self->size());
        if (0 <= index && index <= size) {
          self->insert(self->begin() + index, x);
        } else {
          throw std::out_of_range("vector index out of range");
        }
      }
SWIGINTERN std::vector< int >::value_type std_vector_Sl_int_Sg__doRemove(std::vector< int > *self,jint index){
        jint size = static_cast<jint>(self->size());
        if (0 <= index && index < size) {
          int const old_value = (*self)[index];
          self->erase(self->begin() + index);
          return old_value;
        } else {
          throw std::out_of_range("vector index out of range");
        }
      }
SWIGINTERN std::vector< int >::value_type const &std_vector_Sl_int_Sg__doGet(std::vector< int > *self,jint index){
        jint size = static_cast<jint>(self->size());
        if (index >= 0 && index < size)
          return (*self)[index];
        else
          throw std::out_of_range("vector index out of range");
      }
SWIGINTERN std::vector< int >::value_type std_vector_Sl_int_Sg__doSet(std::vector< int > *self,jint index,std::vector< int >::value_type const &val){
        jint size = static_cast<jint>(self->size());
        if (index >= 0 && index < size) {
          int const old_value = (*self)[index];
          (*self)[index] = val;
          return old_value;
        }
        else
          throw std::out_of_range("vector index out of range");
      }
SWIGINTERN void std_vector_Sl_int_Sg__doRemoveRange(std::vector< int > *self,jint fromIndex,jint toIndex){
        jint size = static_cast<jint>(self->size());
        if (0 <= fromIndex && fromIndex <= toIndex && toIndex <= size) {
          self->erase(self->begin() + fromIndex, self->begin() + toIndex);
        } else {
          throw std::out_of_range("vector index out of range");
        }
      }

#include "JavaPorts.h"


#ifdef __cplusplus
extern "C" {
#endif

SWIGEXPORT jlong JNICALL Java_exampleJNI_new_1IntVec_1_1SWIG_10(JNIEnv *jenv, jclass jcls) {
  jlong jresult = 0 ;
  std::vector< int > *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  result = (std::vector< int > *)new std::vector< int >();
  *(std::vector< int > **)&jresult = result; 
  return jresult;
}


SWIGEXPORT jlong JNICALL Java_exampleJNI_new_1IntVec_1_1SWIG_11(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  jlong jresult = 0 ;
  std::vector< int > *arg1 = 0 ;
  std::vector< int > *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1;
  if (!arg1) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "std::vector< int > const & reference is null");
    return 0;
  } 
  result = (std::vector< int > *)new std::vector< int >((std::vector< int > const &)*arg1);
  *(std::vector< int > **)&jresult = result; 
  return jresult;
}


SWIGEXPORT jlong JNICALL Java_exampleJNI_IntVec_1capacity(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  jlong jresult = 0 ;
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  std::vector< int >::size_type result;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  result = ((std::vector< int > const *)arg1)->capacity();
  jresult = (jlong)result; 
  return jresult;
}


SWIGEXPORT void JNICALL Java_exampleJNI_IntVec_1reserve(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jlong jarg2) {
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  std::vector< int >::size_type arg2 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  arg2 = (std::vector< int >::size_type)jarg2; 
  try {
    (arg1)->reserve(arg2);
  } catch(std::length_error &_e) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, (&_e)->what());
    return ;
  }
}


SWIGEXPORT jboolean JNICALL Java_exampleJNI_IntVec_1isEmpty(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  jboolean jresult = 0 ;
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  bool result;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  result = (bool)((std::vector< int > const *)arg1)->empty();
  jresult = (jboolean)result; 
  return jresult;
}


SWIGEXPORT void JNICALL Java_exampleJNI_IntVec_1clear(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  (arg1)->clear();
}


SWIGEXPORT jlong JNICALL Java_exampleJNI_new_1IntVec_1_1SWIG_12(JNIEnv *jenv, jclass jcls, jint jarg1, jint jarg2) {
  jlong jresult = 0 ;
  jint arg1 ;
  int *arg2 = 0 ;
  int temp2 ;
  std::vector< int > *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = jarg1; 
  temp2 = (int)jarg2; 
  arg2 = &temp2; 
  try {
    result = (std::vector< int > *)new_std_vector_Sl_int_Sg___SWIG_2(arg1,(int const &)*arg2);
  } catch(std::out_of_range &_e) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, (&_e)->what());
    return 0;
  }
  *(std::vector< int > **)&jresult = result; 
  return jresult;
}


SWIGEXPORT jint JNICALL Java_exampleJNI_IntVec_1doSize(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  jint jresult = 0 ;
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  jint result;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  try {
    result = std_vector_Sl_int_Sg__doSize((std::vector< int > const *)arg1);
  } catch(std::out_of_range &_e) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, (&_e)->what());
    return 0;
  }
  jresult = result; 
  return jresult;
}


SWIGEXPORT void JNICALL Java_exampleJNI_IntVec_1doAdd_1_1SWIG_10(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2) {
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  std::vector< int >::value_type *arg2 = 0 ;
  std::vector< int >::value_type temp2 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  temp2 = (std::vector< int >::value_type)jarg2; 
  arg2 = &temp2; 
  std_vector_Sl_int_Sg__doAdd__SWIG_0(arg1,(int const &)*arg2);
}


SWIGEXPORT void JNICALL Java_exampleJNI_IntVec_1doAdd_1_1SWIG_11(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2, jint jarg3) {
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  jint arg2 ;
  std::vector< int >::value_type *arg3 = 0 ;
  std::vector< int >::value_type temp3 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  arg2 = jarg2; 
  temp3 = (std::vector< int >::value_type)jarg3; 
  arg3 = &temp3; 
  try {
    std_vector_Sl_int_Sg__doAdd__SWIG_1(arg1,arg2,(int const &)*arg3);
  } catch(std::out_of_range &_e) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, (&_e)->what());
    return ;
  }
}


SWIGEXPORT jint JNICALL Java_exampleJNI_IntVec_1doRemove(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2) {
  jint jresult = 0 ;
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  jint arg2 ;
  std::vector< int >::value_type result;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  arg2 = jarg2; 
  try {
    result = (std::vector< int >::value_type)std_vector_Sl_int_Sg__doRemove(arg1,arg2);
  } catch(std::out_of_range &_e) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, (&_e)->what());
    return 0;
  }
  jresult = (jint)result; 
  return jresult;
}


SWIGEXPORT jint JNICALL Java_exampleJNI_IntVec_1doGet(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2) {
  jint jresult = 0 ;
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  jint arg2 ;
  std::vector< int >::value_type *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  arg2 = jarg2; 
  try {
    result = (std::vector< int >::value_type *) &std_vector_Sl_int_Sg__doGet(arg1,arg2);
  } catch(std::out_of_range &_e) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, (&_e)->what());
    return 0;
  }
  jresult = (jint)*result; 
  return jresult;
}


SWIGEXPORT jint JNICALL Java_exampleJNI_IntVec_1doSet(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2, jint jarg3) {
  jint jresult = 0 ;
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  jint arg2 ;
  std::vector< int >::value_type *arg3 = 0 ;
  std::vector< int >::value_type temp3 ;
  std::vector< int >::value_type result;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  arg2 = jarg2; 
  temp3 = (std::vector< int >::value_type)jarg3; 
  arg3 = &temp3; 
  try {
    result = (std::vector< int >::value_type)std_vector_Sl_int_Sg__doSet(arg1,arg2,(int const &)*arg3);
  } catch(std::out_of_range &_e) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, (&_e)->what());
    return 0;
  }
  jresult = (jint)result; 
  return jresult;
}


SWIGEXPORT void JNICALL Java_exampleJNI_IntVec_1doRemoveRange(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2, jint jarg3) {
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  jint arg2 ;
  jint arg3 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(std::vector< int > **)&jarg1; 
  arg2 = jarg2; 
  arg3 = jarg3; 
  try {
    std_vector_Sl_int_Sg__doRemoveRange(arg1,arg2,arg3);
  } catch(std::out_of_range &_e) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, (&_e)->what());
    return ;
  }
}


SWIGEXPORT void JNICALL Java_exampleJNI_delete_1IntVec(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  std::vector< int > *arg1 = (std::vector< int > *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(std::vector< int > **)&jarg1; 
  delete arg1;
}


SWIGEXPORT jlong JNICALL Java_exampleJNI_new_1JavaPorts(JNIEnv *jenv, jclass jcls, jint jarg1) {
  jlong jresult = 0 ;
  AIType arg1 ;
  JavaPorts *result = 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = (AIType)jarg1; 
  result = (JavaPorts *)new JavaPorts(arg1);
  *(JavaPorts **)&jresult = result; 
  return jresult;
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1init_1_1SWIG_10(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2, jint jarg3, jint jarg4, jint jarg5, jint jarg6, jint jarg7) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  int arg2 ;
  int arg3 ;
  int arg4 ;
  int arg5 ;
  int arg6 ;
  int arg7 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  arg2 = (int)jarg2; 
  arg3 = (int)jarg3; 
  arg4 = (int)jarg4; 
  arg5 = (int)jarg5; 
  arg6 = (int)jarg6; 
  arg7 = (int)jarg7; 
  (arg1)->init(arg2,arg3,arg4,arg5,arg6,arg7);
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1init_1_1SWIG_11(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2, jint jarg3, jint jarg4) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  int arg2 ;
  int arg3 ;
  int arg4 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  arg2 = (int)jarg2; 
  arg3 = (int)jarg3; 
  arg4 = (int)jarg4; 
  (arg1)->init(arg2,arg3,arg4);
}


SWIGEXPORT jint JNICALL Java_exampleJNI_JavaPorts_1getGeneration(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  jint jresult = 0 ;
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  int result;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  result = (int)(arg1)->getGeneration();
  jresult = (jint)result; 
  return jresult;
}


SWIGEXPORT jstring JNICALL Java_exampleJNI_JavaPorts_1getTreeString(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2) {
  jstring jresult = 0 ;
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  int arg2 ;
  std::string result;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  arg2 = (int)jarg2; 
  result = (arg1)->getTreeString(arg2);
  jresult = jenv->NewStringUTF((&result)->c_str()); 
  return jresult;
}


SWIGEXPORT jstring JNICALL Java_exampleJNI_JavaPorts_1getEliteTreeString(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2) {
  jstring jresult = 0 ;
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  int arg2 ;
  std::string result;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  arg2 = (int)jarg2; 
  result = (arg1)->getEliteTreeString(arg2);
  jresult = jenv->NewStringUTF((&result)->c_str()); 
  return jresult;
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1saveGeneration(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jstring jarg2) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  std::string arg2 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  if(!jarg2) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "null string");
    return ;
  } 
  const char *arg2_pstr = (const char *)jenv->GetStringUTFChars(jarg2, 0); 
  if (!arg2_pstr) return ;
  (&arg2)->assign(arg2_pstr);
  jenv->ReleaseStringUTFChars(jarg2, arg2_pstr); 
  (arg1)->saveGeneration(arg2);
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1loadGeneration(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jstring jarg2, jint jarg3) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  std::string arg2 ;
  int arg3 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  if(!jarg2) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "null string");
    return ;
  } 
  const char *arg2_pstr = (const char *)jenv->GetStringUTFChars(jarg2, 0); 
  if (!arg2_pstr) return ;
  (&arg2)->assign(arg2_pstr);
  jenv->ReleaseStringUTFChars(jarg2, arg2_pstr); 
  arg3 = (int)jarg3; 
  (arg1)->loadGeneration(arg2,arg3);
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1evolve(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  (arg1)->evolve();
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1setFitness(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2, jint jarg3) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  int arg2 ;
  int arg3 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  arg2 = (int)jarg2; 
  arg3 = (int)jarg3; 
  (arg1)->setFitness(arg2,arg3);
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1setBehavior(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2, jlong jarg3, jobject jarg3_) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  int arg2 ;
  std::vector< int > arg3 ;
  std::vector< int > *argp3 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  (void)jarg3_;
  arg1 = *(JavaPorts **)&jarg1; 
  arg2 = (int)jarg2; 
  argp3 = *(std::vector< int > **)&jarg3; 
  if (!argp3) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "Attempt to dereference null std::vector< int >");
    return ;
  }
  arg3 = *argp3; 
  (arg1)->setBehavior(arg2,arg3);
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1setTargetSpecies(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  int arg2 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  arg2 = (int)jarg2; 
  (arg1)->setTargetSpecies(arg2);
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1setSurpriseEffect(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jfloat jarg2) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  float arg2 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  arg2 = (float)jarg2; 
  (arg1)->setSurpriseEffect(arg2);
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1mapElites(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  (arg1)->mapElites();
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1randomizeBTPopulation(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jint jarg2, jint jarg3) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  int arg2 ;
  int arg3 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  arg2 = (int)jarg2; 
  arg3 = (int)jarg3; 
  (arg1)->randomizeBTPopulation(arg2,arg3);
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1randomizePopulationFromElites(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  (arg1)->randomizePopulationFromElites();
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1saveElites_1_1SWIG_10(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jstring jarg2) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  std::string arg2 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  if(!jarg2) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "null string");
    return ;
  } 
  const char *arg2_pstr = (const char *)jenv->GetStringUTFChars(jarg2, 0); 
  if (!arg2_pstr) return ;
  (&arg2)->assign(arg2_pstr);
  jenv->ReleaseStringUTFChars(jarg2, arg2_pstr); 
  (arg1)->saveElites(arg2);
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1saveElites_1_1SWIG_11(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  (arg1)->saveElites();
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1loadElites_1_1SWIG_10(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_, jstring jarg2) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  std::string arg2 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  if(!jarg2) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "null string");
    return ;
  } 
  const char *arg2_pstr = (const char *)jenv->GetStringUTFChars(jarg2, 0); 
  if (!arg2_pstr) return ;
  (&arg2)->assign(arg2_pstr);
  jenv->ReleaseStringUTFChars(jarg2, arg2_pstr); 
  (arg1)->loadElites(arg2);
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1loadElites_1_1SWIG_11(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  (arg1)->loadElites();
}


SWIGEXPORT void JNICALL Java_exampleJNI_JavaPorts_1storeElites(JNIEnv *jenv, jclass jcls, jlong jarg1, jobject jarg1_) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  
  (void)jenv;
  (void)jcls;
  (void)jarg1_;
  arg1 = *(JavaPorts **)&jarg1; 
  (arg1)->storeElites();
}


SWIGEXPORT void JNICALL Java_exampleJNI_delete_1JavaPorts(JNIEnv *jenv, jclass jcls, jlong jarg1) {
  JavaPorts *arg1 = (JavaPorts *) 0 ;
  
  (void)jenv;
  (void)jcls;
  arg1 = *(JavaPorts **)&jarg1; 
  delete arg1;
}


#ifdef __cplusplus
}
#endif

