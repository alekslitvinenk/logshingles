#!/usr/bin/env bash

rm -R target

sbt ';build ;publishLocal'