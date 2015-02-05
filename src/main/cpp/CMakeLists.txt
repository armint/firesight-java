
message("CMAKE_PREFIX_PATH:${CMAKE_PREFIX_PATH}")

link_directories( ${CMAKE_BINARY_DIR} )

set(FIRESIGHTJAVA_LIB_FILES
    org_firepick_firesight_FireSight.cpp
    org_firepick_firesight_HoleRecognizer.cpp
    org_firepick_firesight_HoughCircle.cpp
  )

add_library(firesight-java SHARED ${FIRESIGHTJAVA_LIB_FILES})
target_link_libraries(firesight-java ${JANSSON_LIB} ${OpenCV_LIBS} ${FireSight_LIBS})
set_target_properties(firesight-java PROPERTIES 
    VERSION ${PROJECT_VERSION_STRING} 
    SOVERSION ${PROJECT_VERSION_MAJOR}
    )

  
INSTALL(TARGETS firesight-java DESTINATION ${FIRESIGHT_INSTALL_LIB_DIR})
  