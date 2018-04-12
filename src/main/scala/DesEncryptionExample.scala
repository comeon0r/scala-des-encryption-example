import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}
import javax.crypto.spec.DESKeySpec

import sun.misc.{BASE64Decoder, BASE64Encoder}

/**
  * Created by @author yuanjchen on 4/12/18
  */
object DesEncryptionExample extends App {
  val key = "i_am_a_testing_key" // key must be longer than 8 chars
  val secretKey: SecretKey = SecretKeyFactory.getInstance("des").generateSecret(new DESKeySpec(key.getBytes))

  val plainText = "Hello, World"

  val encoded = DESEncode(plainText)
  println(s"text:$plainText, encoded text:$encoded")
  assert(plainText != encoded)

  val decoded = DESDecode(encoded)
  assert(plainText == decoded)

  def DESEncode(content: String): String = {
    val cipher = Cipher.getInstance("des")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
    cipher.doFinal(content.getBytes)
    new BASE64Encoder().encode(cipher.doFinal(content.getBytes("utf-8")))
  }

  def DESDecode(content: String): String = {
    val cipher = Cipher.getInstance("des")
    cipher.init(Cipher.DECRYPT_MODE, secretKey)
    new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(content)),"utf-8")
  }

}
