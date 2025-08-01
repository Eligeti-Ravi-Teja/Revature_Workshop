#!/bin/bash

echo "Starting User Management Frontend..."
echo "=================================="

# Check if Node.js is available
if command -v node &> /dev/null; then
    echo "Using Node.js server..."
    node server.js
elif command -v python3 &> /dev/null; then
    echo "Using Python server..."
    python3 -m http.server 8082
else
    echo "Error: Neither Node.js nor Python3 found."
    echo "Please install Node.js or Python3 to run the frontend."
    exit 1
fi 