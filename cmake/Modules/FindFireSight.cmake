# Written by Armin van der Togt
#  FireSight_FOUND - System has FireSight
#  FireSight_INCLUDE_DIRS - The FireSight include directories
#  FireSight_LIBS - The libraries needed to use FireSight


find_library(FireSight_LIBS NAMES _firesight PATHS ${FireSight_DIR} PATH_SUFFIXES "bin")

find_path(FireSight_INCLUDE_DIRS "FireSight.hpp" PATHS ${FireSight_DIR})

if (FireSight_LIBS AND FireSight_INCLUDE_DIRS)
	SET(FireSight_FOUND 1)
else  (FireSight_LIBS AND FireSight_INCLUDE_DIRS)
	SET(FireSight_FOUND 0)
endif  (FireSight_LIBS AND FireSight_INCLUDE_DIRS)

