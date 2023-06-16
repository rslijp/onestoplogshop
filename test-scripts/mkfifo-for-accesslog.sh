#!/bin/bash

echo "Installing net cat"
apt-get update && apt-get install -y netcat

echo "Creating net cat script"
cat <<EOT >> /tmp/send-log.sh
echo "Starting log script"
while true
do
    echo "Sending"
    touch /tmp/fifo-access.log.swap
    cat /tmp/fifo-access.log >> /tmp/fifo-access.log.swap && > /tmp/fifo-access.log
    cat /tmp/fifo-access.log.swap | nc 192.168.1.89 9000 -q 0 && > /tmp/fifo-access.log.swap
    sleep 5
done
EOT

chmod +x /tmp/send-log.sh

echo "Starting net cat script"
/tmp/send-log.sh&