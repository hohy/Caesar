#/bin/bash
printf "\n\nGenerating java source from gramatic using javacc...\n"
#jjtree caesar.jjt
javacc caesar.jj

printf "\n\nCompiling java source using javac...\n"
javac javasrc/caesar/*.java javasrc/caesar/*/*.java -d build/
