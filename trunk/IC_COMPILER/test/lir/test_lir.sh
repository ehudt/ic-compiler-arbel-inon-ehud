#!/bin/bash

for FILE in "$@"
do
	NAME=`echo "$FILE" | cut -d'.' -f1`
	rm -f $NAME.lir
	java -cp /home/ehud/IC_COMPILER/classes:$CLASSPATH IC.Compiler "$NAME.ic" -print-lir >/dev/null
	RESULT=`java -cp /home/ehud/microLir/build:$CLASSPATH microLIR.Main "$NAME.lir"`
	fromdos $NAME.result
	EXPECTED=`cat "$NAME.result"`
	if [ "$RESULT" != "$EXPECTED" ]
	then
		echo "Error: unexpected result while running $NAME"
		echo "Output: $RESULT"
		echo "Expected: $EXPECTED"
	fi
done
