rm -r ../cloudfoundry/*
play war . -o ../cloudfoundry/exploded_war
jar -cvf ../cloudfoundry/remindcloud.war -C ../cloudfoundry/exploded_war .
vmc update remindcloud --path ../cloudfoundry

