package org.knoldus

trait Response

case object Successful extends Response
case object Failed extends Response
