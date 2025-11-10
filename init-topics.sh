#!/bin/sh
echo "🔹 Initializing Kafka topics..."

topics="charging.session billing.payment csms.transaction"

for topic in $topics; do
  /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 \
    --create --if-not-exists \
    --topic "$topic" \
    --partitions 3 \
    --replication-factor 1
done

echo "✅ Topics ready: $topics"