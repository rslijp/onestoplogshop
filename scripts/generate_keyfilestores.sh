#!/bin/bash
keytool -keypass changeit -storepass changeit -genkey -alias server -dname "CN=my-logging-server" -keyalg RSA -validity 365 -keystore server.keystore

keytool -import -alias server -file server.crt -keystore server.truststore

keytool -keypass changeit -storepass changeit -import -alias server -file server.crt -keystore server.truststore