
provider "aws" {

  access_key                  = "test"
  secret_key                  = "test"
  region                      = "us-east-1"

  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    sns            = "http://localhost:4566"
    sqs            = "http://localhost:4566"
  }
}

resource "aws_sqs_queue" "terraform_queue" {
  name                      = "terraform-example-queue"
  delay_seconds             = 90
  max_message_size          = 2048
  message_retention_seconds = 86400
  receive_wait_time_seconds = 10

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.terraform_queue_deadletter.arn
    maxReceiveCount     = 4
  })


  tags = {
    Environment = "production"
  }
}

resource "aws_sqs_queue" "terraform_queue_deadletter" {
  name = "terraform-example-deadletter-queue"
}

resource "aws_sqs_queue_redrive_allow_policy" "terraform_queue_redrive_allow_policy" {
  queue_url = aws_sqs_queue.terraform_queue_deadletter.id

  redrive_allow_policy = jsonencode({
    redrivePermission = "byQueue",
    sourceQueueArns   = [aws_sqs_queue.terraform_queue.arn]
  })
}

resource "aws_sqs_queue_policy" "orders_to_process_subscription" {
  queue_url = aws_sqs_queue.terraform_queue.id
  policy    = <<EOF
  {
    "Version": "2012-10-17",
    "Statement": [
      {
        "Effect": "Allow",
        "Principal": {
          "Service": "sns.amazonaws.com"
        },
        "Action": [
          "sqs:SendMessage"
        ],
        "Resource": [
          "${aws_sqs_queue.terraform_queue.arn}"
        ],
        "Condition": {
          "ArnEquals": {
            "aws:SourceArn": "${aws_sns_topic.orders.arn}"
          }
        }
      }
    ]
  }
  EOF
}

resource "aws_sns_topic" "orders" {
  name = "orders-topic"
}

resource "aws_sns_topic_subscription" "orders_to_process_subscription" {
  protocol             = "sqs"
  raw_message_delivery = true
  topic_arn            = aws_sns_topic.orders.arn
  endpoint             = aws_sqs_queue.terraform_queue.arn
}