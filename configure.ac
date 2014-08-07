AC_INIT(
	[Mercury Components for All Platforms] ,
	[1.0] ,
	[mariano.pedro@gmail.com] ,
	[mc4ap-1.0] ,
	[https://github.com/plsm/mc4ap] )
AC_CONFIG_FILES(Makefile)

############################################################
# check for mercury
AC_PATH_PROG(MMC,mmc,notFound)
if test "$MMC" = notFound ; then
	AC_MSG_ERROR(["Mercury compiler is required!"])
else
	use_console=yes
############################################################
# check for java 
	AC_CHECK_PROG(JAVAC,javac,found)
	if test "$JAVAC" = found ; then
		# see if mercury has the java grade
		mercury_java_lib=$(AS_DIRNAME($MMC))
		mercury_java_lib=$(AS_DIRNAME($mercury_java_lib))/lib/mercury/lib/java
		if test -d $mercury_java_lib ; then
			echo "Mercury has the java grade."
			mercury_java=$mercury_java_lib/mer_std.jar:$mercury_java_lib/mer_rt.jar
			use_swing=yes
		else
			use_swing=no
		fi
	else
		use_swing=no
	fi
fi

AC_SUBST(use_swing)
AC_SUBST(mercury_java)

AC_OUTPUT
