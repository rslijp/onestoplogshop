#!/bin/bash
for((i=0; i<10; i++))
do
	echo "[1] Number $i" >> mock.log
done

echo "Sleep" >> mock.log
sleep 5

for((i=0; i<10; i++))
do
	echo "[2] Number $i" >> mock.log
done

echo "Sleep"
sleep 5

for((i=0; i<10; i++))
do
	echo "[3] Number $i" >> mock.log
done

echo "Sleep" >> mock.log
sleep 5

for((i=0; i<10; i++))
do
	echo "[4] Number $i" >> mock.log
done