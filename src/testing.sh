#!/bin/bash

USAGE="Usage: $0 dir1 dir2 dir3 ... dirN"
if [ "$#" == "0" ]; then
echo "$USAGE"
exit 1
fi
#Local and Server Paths
TESTSITE=amedvedev2013@andrew.cs.fit.edu
LOCALTPATH=Desktop/Spring2017/SoftwareTesting2/
TESTPATH=~kgallagher/public_html/
SAMPLEPROGPATH=sampleprogs/

#Pipes to test
GENERATORS=(
             onetoone
             onto
             reflex
             sym
             trans
             func
             ref.trans
             ref.sym
             sym.trans
             eq
            )

for ((II=0; II < ${#GENERATORS[@]}; ++II)) do
	
	echo ""
	#Test Sample progs output
	#echo $TESTSITE $TESTPATH$SAMPLEPROGPATH${GENERATORS[II]} $1 $2
	
	#Test Oracles access
	#echo $TESTSITE $TESTPATH$ORACLEPATH${GENERATORS[II]} $1 $2
	
	#Test java
	#java TestPipe
	
	#Test Arguments
	#echo ${GENERATORS[II]}
	
	#Main command
	ssh $TESTSITE $TESTPATH$SAMPLEPROGPATH${GENERATORS[II]} $1 $2 | tee >(java TestPipe ${GENERATORS[II]}) >/dev/null
	
done



