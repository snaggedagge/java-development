#!/bin/bash


if [ "$1" ==  "synth" ]
 then
   echo "Synthesizing cloudformation"
    cd webportal-test/webportal-aws/ || exit

    cdk synth
  elif [ "$1" ==  "destroy" ]
  then
    echo "Tearing down cloudformation"
    cd webportal-test/webportal-aws/ || exit

    cdk destroy '*'
 elif [ "$1" ==  "deploy" ]
 then
  echo "Will deploy new application"
  echo "Compiling and uploading webportal to S3"
  STR=$(gradle -q webportal-test:webportal:publishToS3)
  export -p S3_KEY="$STR"

  echo "Finished uploading. S3 key is $S3_KEY"

  cd webportal-test/webportal-aws/ || exit

  cdk synth && cdk deploy '*'
 else
    echo "Parameter didnt match synth or deploy"
fi

