package fr.amoya.ktaglib.common.parsers.id3.v2

import fr.amoya.ktaglib.common.parsers.TagParser
import fr.amoya.ktaglib.common.tags.id3v2.Id3ExtendedHeader
import fr.amoya.ktaglib.common.tags.id3v2.frame.Id3Frame
import fr.amoya.ktaglib.common.tags.id3v2.frame.Id3FrameHeader
import fr.amoya.ktaglib.common.tags.id3v2.frame.v22.Id3v22FrameHeader
import fr.amoya.ktaglib.common.tags.id3v2.frame.v22.Id3v22KnownFrames
import fr.amoya.ktaglib.common.tags.id3v2.frame.v23.Id3v23KnownFrames
import fr.amoya.ktaglib.common.utils.ByteHelper


/*
* fr.amoya.ktaglib.parsers.id3
* As a part of the Project k-taglib
* @Author Arnaud Moya : <contact@amoya.fr>
* Created on 30/04/2021
*/

@ExperimentalUnsignedTypes
class Id3v22TagParser : TagParser, AbstractId3v2TagParser()
{

  override fun parseExtendedHeader(rawData: ByteArray): Id3ExtendedHeader? = null

  override fun parseFrameHeader(rawFrameHeader: ByteArray): Id3FrameHeader
  {
    require(rawFrameHeader.size >= headerSize) { "Id3v2 Frame header must be $headerSize bytes" }
    val id = try
    {
      Id3v23KnownFrames.valueOf(rawFrameHeader.decodeToString(0, 3))
    }
    catch (_: Exception)
    {
      Id3v23KnownFrames.NONE
    }
    return Id3v22FrameHeader(
      id = id,
      size = ByteHelper.aggregateBytes(rawFrameHeader.copyOfRange(4, 8), 4, UInt::class).toInt()
    )
  }

  override fun parseFrame(header: Id3FrameHeader, rawFrameContent: ByteArray): Id3Frame =
    Id3Frame(header, (header.id as Id3v22KnownFrames).parser(rawFrameContent))
}
