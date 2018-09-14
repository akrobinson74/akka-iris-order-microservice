package com.olx.iris.model
import java.util.UUID

class TransactionId(val id: String) extends AnyVal

object TransactionId {
  def apply(): TransactionId = new TransactionId(id = UUID.randomUUID().toString)
  def apply(id: UUID): TransactionId = new TransactionId(id.toString)

//  implicit val transactionIdDecoder: Decoder[TransactionId] = Decoder.decodeUUID.map(TransactionId(_))
//  implicit val transactionIdEncoder: Encoder[TransactionId] = Encoder.encodeUUID.contramap(_.id)
}
