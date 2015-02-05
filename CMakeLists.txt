cmake_minimum_required(VERSION 2.8)

project( firesight-java )


SET(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH}
    "${CMAKE_SOURCE_DIR}/cmake/Modules/")

find_package( OpenCV REQUIRED )
if (OpenCV_LIBS) 
  MESSAGE(STATUS "Found OpenCV version: ${OpenCV_VERSION}" )
  MESSAGE(STATUS "OpenCV_LIBS:${OpenCV_LIBS}")
else(OpenCV_LIBS)
  MESSAGE("find_package(OpenCV) failed => OpenCV_LIBS is not defined")
endif(OpenCV_LIBS)

find_package( JANSSON REQUIRED )
SET(JANSSON_LIB ${JANSSON_LIBS})

find_package( JNI REQUIRED )
if (JNI_INCLUDE_DIRS)
  MESSAGE(STATUS "JNI_INCLUDE_DIRS:${JNI_INCLUDE_DIRS}")
else (JNI_INCLUDE_DIRS)
  MESSAGE("find_package(JNI) failed => JNI_INCLUDE_DIRS is not defined")
endif (JNI_INCLUDE_DIRS)

find_package(FireSight REQUIRED)
if(FireSight_LIBS)
  MESSAGE(STATUS "FireSight_LIBS:${FireSight_LIBS}")
else(FireSight_LIBS)
  MESSAGE("find_package(FireSight) failed => FireSight_LIBS is not defined")
endif(FireSight_LIBS)

IF(WIN32)
  MESSAGE(STATUS "Detecting WINDOWS build")
  list(APPEND CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}")
  # By default, do not warn when built on machines using only VS Express:
  IF(NOT DEFINED CMAKE_INSTALL_SYSTEM_RUNTIME_LIBS_NO_WARNINGS)
    SET(CMAKE_INSTALL_SYSTEM_RUNTIME_LIBS_NO_WARNINGS ON)
  ENDIF()
ELSE(WIN32)
  MESSAGE(STATUS "Detecting LINUX build")
  SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fPIC -g -Wno-format-extra-args")
  SET(CMAKE_SHARED_LINKER_FLAGS_DEBUG "${CMAKE_SHARED_LINKER_FLAGS_DEBUG} -g")
ENDIF(WIN32)

SET(COMPILE_DEFINITIONS -Werror)
#SET(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -D_FILE_OFFSET_BITS=64")

include_directories( ${PROJECT_SOURCE_DIR} ${CMAKE_BINARY_DIR} ${JANSSON_INCLUDE_DIRS} ${OpenCV_INCLUDE_DIRS} ${JNI_INCLUDE_DIRS} ${FireSight_INCLUDE_DIRS})

include (InstallRequiredSystemLibraries)
SET(CPACK_RESOURCE_FILE_LICENSE  
     "${CMAKE_CURRENT_SOURCE_DIR}/LICENSE")
SET(CPACK_PACKAGE_GENERATED_WARNING "GENERATED FILE!!! DO NOT CHANGE!!!")
SET(CPACK_PACKAGE_VERSION_MAJOR "${PROJECT_VERSION_MAJOR}")
SET(CPACK_PACKAGE_VERSION_MINOR "${PROJECT_VERSION_MINOR}")
include (CPack)

get_property(dirs DIRECTORY . PROPERTY INCLUDE_DIRECTORIES)
message("INCLUDE_DIRECTORIES:")
foreach(dir ${dirs})
  message(STATUS "  dir='${dir}'")
endforeach()

#
# Installation preparation.
#

# Allow the user to override installation directories.
set(FIRESIGHT_INSTALL_LIB_DIR       lib CACHE PATH "Installation directory for libraries")


add_subdirectory(src/main/cpp)

