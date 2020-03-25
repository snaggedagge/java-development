#!/bin/bash

myfun() {
    echo "" | webportal-test:webportal:publishToS3
}

export -f myfun