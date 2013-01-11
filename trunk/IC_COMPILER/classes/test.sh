#!/bin/bash
for file in `find ../test/semantic/ | grep -v svn`
do
    java IC.Compiler $file -Llibic.sig
done
