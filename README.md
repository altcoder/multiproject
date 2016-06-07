# Project Name

[![Build Status](https://travis-ci.org/jafaeldon/multiproject.svg?branch=wip/0.1)](http://travis-ci.org/jafaeldon/multiproject)

## Overview

Project is a 

<http://project.org/>

## Online Documentation

You can find the latest Project documentation, including a programming
guide, on the [project web page](http://project.org/documentation.html)
and [project wiki](https://cwiki.project.org/confluence/display/PROJECT).
This README file only contains basic setup instructions.

## Building

Project is built using [SBT](http://www.scala-sbt.org/).
To build Project and its example programs, run:

    ./sbt clean compile

(You do not need to do this if you downloaded a pre-built package.)
More detailed documentation is available from the project site, at
["Building Project"](http://project.org/docs/latest/building-project.html).

## Running Tests

Setup the environment first using
    1. Install ["docker"](https://docs.docker.com/engine/quickstart/) and ["docker-compose"](https://docs.docker.com/compose/install/)
    2. Run services for testing
       docker-compose -f devops/docker-compose-test.yml up


Testing first requires [building Project](#building-project). Once Project is built, tests
can be run using:

    ./sbt test

Please see the guidance on how to
[run tests for a module, or individual tests](https://cwiki.project.org/confluence/display/PROJECT/Useful+Developer+Tools).

## Coding Style

Refer to [Scala Style Guide](http://docs.scala-lang.org/style/) for project coding conventions. 
To veriful code style, run:

    ./sbt scalastyle

## Configuration

Please refer to the [Configuration guide](http://project.org/docs/latest/configuration.html)
in the online documentation for an overview on how to configure Project.
