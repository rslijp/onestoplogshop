#!/bin/bash
keytool -keypass changeit -storepass changeit -genkey -alias server -dname "CN=my-logging-server" -keyalg RSA -validity 365 -keystore server.keystore
keytool -export -keystore server.keystore -storepass  changeit  -alias server -file server.crt
keytool -import -alias server -file server.crt -keystore server.truststore

keytool -keypass changeit -storepass changeit -import -alias server -file server.crt -keystore server.truststore